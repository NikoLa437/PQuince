package quince_it.pquince.services.implementation.appointment;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.appointment.AppointmentStatus;
import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.DateRange;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.entities.users.WorkTime;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.PharmacistRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.repository.users.WorkTimeRepository;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentCreateDTO;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentPeriodResponseDTO;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentRequestDTO;
import quince_it.pquince.services.contracts.dto.appointment.ConsultationRequestDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentWithPharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.StaffGradeDTO;
import quince_it.pquince.services.contracts.exceptions.AlreadyBeenScheduledConsultationException;
import quince_it.pquince.services.contracts.exceptions.AppointmentNotScheduledException;
import quince_it.pquince.services.contracts.exceptions.AppointmentTimeOverlappingWithOtherAppointmentException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.appointment.IAppointmentService;
import quince_it.pquince.services.contracts.interfaces.users.ILoyaltyProgramService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.services.implementation.util.appointment.AppointmentMapper;

@Service
public class AppointmentService implements IAppointmentService{

	@Autowired
	private IUserService userService;
	
	@Autowired
	private PatientRepository patientRepository;
			
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private WorkTimeRepository workTimeRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private ILoyaltyProgramService loyalityProgramService;
	
	@Override
	public UUID create(DermatologistAppointmentDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public UUID createTerminForDermatologist(AppointmentCreateDTO appointmentDTO) {
		// TODO NIKOLA : srediti try catch blok
		try {
			Pharmacy pharmacy = pharmacyRepository.getOne(appointmentDTO.getPharmacy());
			Staff dermatologist = staffRepository.getOne(appointmentDTO.getStaff());
			
			Appointment newAppointment = new Appointment(pharmacy,dermatologist,null, appointmentDTO.getStartDateTime(),appointmentDTO.getEndDateTime(),appointmentDTO.getPrice(),AppointmentType.EXAMINATION,AppointmentStatus.CREATED);
			appointmentRepository.save(newAppointment);
			return newAppointment.getId();
		}catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAll() {
		return null;
	}

	@Override
	public IdentifiableDTO<DermatologistAppointmentDTO> findById(UUID id) {
		return null;
	}

	@Override
	public void update(DermatologistAppointmentDTO entityDTO, UUID id) {
		
	}

	@Override
	public boolean delete(UUID id) {
		return false;
	}

	@Override
	public List<AppointmentPeriodResponseDTO> getFreePeriods(AppointmentRequestDTO appointmentRequestDTO) {
		WorkTime workTime = workTimeRepository.getWorkTimeForDermatologistForDateForPharmacy(appointmentRequestDTO.getDermatologistId(),appointmentRequestDTO.getDate(),appointmentRequestDTO.getPharmacyId());
		List<Appointment> scheduledAppointments = appointmentRepository.getCreatedAppoitntmentsByDermatologistByDate(appointmentRequestDTO.getDermatologistId(),appointmentRequestDTO.getDate(),appointmentRequestDTO.getPharmacyId());
	
		if(workTime==null) {
			return new ArrayList<AppointmentPeriodResponseDTO>();
		}
		else {
			AppointmentScheduler scheduler = new AppointmentScheduler(createDateRangeForWorkTimeForDay(workTime,appointmentRequestDTO.getDate()),scheduledAppointments,appointmentRequestDTO.getDuration());
			return scheduler.GetFreeAppointment();
		}
	}

	private DateRange createDateRangeForWorkTimeForDay(WorkTime workTime, Date date) {
		@SuppressWarnings("deprecation")
		Date startDate = new Date(date.getYear(),date.getMonth(),date.getDay(),workTime.getStartTime(),0,0);
		@SuppressWarnings("deprecation")
		Date endDate = new Date(date.getYear(),date.getMonth(),date.getDay(),workTime.getEndTime(),0,0);

		return new DateRange(startDate,endDate);
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentType(UUID pharmacyId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllFreeAppointmentsByPharmacyAndAppointmentType(pharmacyId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(StaffType.DERMATOLOGIST);
		
		int discountPercentage = loyalityProgramService.getDiscountPercentageForAppointmentForPatient(AppointmentType.EXAMINATION);
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades, discountPercentage);
		
		return returnAppointments;
	}

	@Override
	public boolean reserveAppointment(UUID appointmentId) {
		
		try {
			UUID patientId = userService.getLoggedUserId();
			
			Appointment appointment = appointmentRepository.findById(appointmentId).get();
			Patient patient = patientRepository.findById(patientId).get();

			CanReserveAppointment(appointment, patient);
			
			appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);
			appointment.setPatient(patient);
			appointment.setPriceToPay(loyalityProgramService.getDiscountAppointmentPriceForPatient(appointment.getPrice(), AppointmentType.EXAMINATION));
			
			appointmentRepository.save(appointment);
			emailService.sendAppointmentReservationNotificationAsync(appointment, "dr.");
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void CanReserveAppointment(Appointment appointment,Patient patient) throws AppointmentTimeOverlappingWithOtherAppointmentException {
		
		if(doesPatientHaveAppointmentInDesiredTime(appointment, patient))
			throw new AppointmentTimeOverlappingWithOtherAppointmentException("Cannot reserve appointment at same time as other appointment");
		
		if(patient.getPenalty() >= Integer.parseInt(env.getProperty("max_penalty_count")))
			throw new AuthorizationServiceException("Too many penalty points");
		
		if (!(appointment.getStartDateTime().after(new Date()) &&
				(appointment.getAppointmentStatus().equals(AppointmentStatus.CREATED) || appointment.getAppointmentStatus().equals(AppointmentStatus.CANCELED))))
			throw new IllegalArgumentException();
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(UUID pharmacyId, AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(pharmacyId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(StaffType.DERMATOLOGIST);
		
		int discountPercentage = loyalityProgramService.getDiscountPercentageForAppointmentForPatient(AppointmentType.EXAMINATION);
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades, discountPercentage);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(
			UUID pharmacyId, AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(pharmacyId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(StaffType.DERMATOLOGIST);
		
		int discountPercentage = loyalityProgramService.getDiscountPercentageForAppointmentForPatient(AppointmentType.EXAMINATION);
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades, discountPercentage);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeAscending(
			UUID pharmacyId, AppointmentType appointmentType) {
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> staffWithGrades = findAllFreeAppointmentsByPharmacyAndAppointmentType(pharmacyId, appointmentType);
		Collections.sort(staffWithGrades, (s1, s2) -> Double.compare(s1.EntityDTO.getStaff().EntityDTO.getGrade(), s2.EntityDTO.getStaff().EntityDTO.getGrade()));
		
		return staffWithGrades;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeDescending(
			UUID pharmacyId, AppointmentType appointmentType) {
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> staffWithGrades = findAllFreeAppointmentsByPharmacyAndAppointmentType(pharmacyId, appointmentType);
		Collections.sort(staffWithGrades, (s1, s2) -> Double.compare(s1.EntityDTO.getStaff().EntityDTO.getGrade(), s2.EntityDTO.getStaff().EntityDTO.getGrade()));
		Collections.reverse(staffWithGrades);

		return staffWithGrades;
	}

	@Override
	public boolean cancelAppointment(UUID appointmentId) {
		
		try {
			Appointment appointment = appointmentRepository.findById(appointmentId).get();
			if(!canAppointmentBeCanceled(appointment.getStartDateTime())) return false;
			
			appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
			appointment.setPriceToPay(appointment.getPrice());
			appointmentRepository.save(appointment);
	
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean canAppointmentBeCanceled(Date appointmentStartDateTime) {
		
		LocalDateTime ldt = LocalDateTime.ofInstant(appointmentStartDateTime.toInstant(), ZoneId.systemDefault());
		ldt = ldt.minusDays(1);
		
		if(ldt.isBefore(LocalDateTime.now())) return false;
		
		return true;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>> findAllFutureAppointmentsForPatient(AppointmentType appointmentType) {
		
		UUID patientId = userService.getLoggedUserId();
		List<Appointment> appointments = appointmentRepository.findAllFutureAppointmentsForPatient(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) 
																											  ? StaffType.DERMATOLOGIST :
																												StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>> returnAppointments = AppointmentMapper
																									.MapAppointmentPersistenceListToAppointmentWithPharmacyIdentifiableDTOList
																									(appointments, staffWithGrades, 0);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByDateAscending(AppointmentType appointmentType) {
		
		UUID patientId = userService.getLoggedUserId();
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatientSortByDateAscending(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) 
																											  ? StaffType.DERMATOLOGIST :
																												StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades, 0);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByDateDescending(AppointmentType appointmentType) {
		
		UUID patientId = userService.getLoggedUserId();
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatientSortByDateDescending(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) ? 
																																		StaffType.DERMATOLOGIST :
																																		StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades, 0);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByPriceAscending(AppointmentType appointmentType) {
		
		UUID patientId = userService.getLoggedUserId();
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatientSortByPriceAscending(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) ? 
																																		StaffType.DERMATOLOGIST :
																																		StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades, 0);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByPriceDescending(AppointmentType appointmentType) {
		
		UUID patientId = userService.getLoggedUserId();
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatientSortByPriceDescending(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) ? 
																													StaffType.DERMATOLOGIST :
																													StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades, 0);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByTimeAscending(AppointmentType appointmentType) {
		
		UUID patientId = userService.getLoggedUserId();
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatientSortByTimeAscending(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) ? 
																																		StaffType.DERMATOLOGIST :
																																		StaffType.PHARMACIST);
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades, 0);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByTimeDescending(AppointmentType appointmentType) {
		
		UUID patientId = userService.getLoggedUserId();
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatientSortByTimeDescending(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) ? 
																												StaffType.DERMATOLOGIST :
																												StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades, 0);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatient(AppointmentType appointmentType) {
		
		UUID patientId = userService.getLoggedUserId();
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatient(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) ? 
																													StaffType.DERMATOLOGIST :
																													StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades, 0);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<AppointmentDTO>> getDermatologistAppointmentsByPatient(UUID patientId) {
		List<Appointment> appointments = appointmentRepository.getDermatologistAppointmentsByPatient(patientId);
		
		List<IdentifiableDTO<AppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments);
		
		return returnAppointments;
	}
	@Override
	public List<IdentifiableDTO<AppointmentDTO>> getCreatedAppointmentsByDermatologist(UUID dermatologistId) {
		List<Appointment> appointments = appointmentRepository.getCreatedAppointmentsByDermatologist(dermatologistId);
		
		List<IdentifiableDTO<AppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments);
		
		return returnAppointments;
	}

	@Override
	@SuppressWarnings("deprecation")
	public List<Pharmacy> findAllDistinctPharmaciesForAppointmentTime(Date startDateTime, Date endDateTime) {

		List<WorkTime> workTimeInRange = workTimeRepository.findWorkTimesByDesiredConsultationTime(new Date(endDateTime.getYear(), endDateTime.getMonth(), endDateTime.getDate(),0,0,0),
				endDateTime.getMinutes() == 0 ? endDateTime.getHours() : endDateTime.getHours() + 1);
		
		List<Appointment> overlappingAppointmentsWithRange = appointmentRepository.findAllConsultationsByAppointmentTime(startDateTime, endDateTime);
		List<Pharmacy> distinctPharmacies = findDistinctPharmaciesInRange(workTimeInRange, overlappingAppointmentsWithRange);

		return distinctPharmacies;
	}

	private List<Pharmacy> findDistinctPharmaciesInRange(List<WorkTime> workTimeInRange,List<Appointment> overlappingAppointmentsWithRange) {
		List<Pharmacy> pharmacies = new ArrayList<Pharmacy>();
		
		for(WorkTime workTime : workTimeInRange) {
			boolean busy = false;
			for(Appointment appointment : overlappingAppointmentsWithRange) {
				if(workTime.getStaff().getId().equals(appointment.getStaff().getId())) {
					busy = true;
					break;
				}
			}
			
			if(!busy) pharmacies.add(workTime.getPharmacy());
		}
		
		List<Pharmacy> returnPharmacies = getDistinctPharmacies(pharmacies);
		
		return returnPharmacies;
	}

	private List<Pharmacy> getDistinctPharmacies(List<Pharmacy> pharmacies) {
		List<Pharmacy> returnPharmacies = new ArrayList<Pharmacy>();
		
		for(Pharmacy pharmacy : pharmacies) {
			boolean added = false;
			for(Pharmacy rePharmacy : returnPharmacies) {
				if(pharmacy.getId().equals(rePharmacy.getId())) {
					added = true;
					break;
				}
			}
			
			if(!added) returnPharmacies.add(pharmacy);
		}
		return returnPharmacies;
	}

	@Override
	@SuppressWarnings("deprecation")
	public List<Staff> findAllDistinctPharmacistsForAppointmentTimeForPharmacy(Date startDateTime, Date endDateTime, UUID pharmacyId) {
		
		List<WorkTime> workTimeInRange = workTimeRepository.findWorkTimesByDesiredConsultationTimeAndPharmacyId(new Date(endDateTime.getYear(), endDateTime.getMonth(), endDateTime.getDate(),0,0,0),
				endDateTime.getMinutes() == 0 ? endDateTime.getHours() : endDateTime.getHours() + 1, pharmacyId);
		
		List<Appointment> overlappingAppointmentsWithRange = appointmentRepository.findAllConsultationsByAppointmentTimeAndPharmacy(startDateTime, endDateTime, pharmacyId);
		List<Staff> distinctStaff = findDistinctPharmacistInRange(workTimeInRange, overlappingAppointmentsWithRange);
		
		return distinctStaff;
	}

	private List<Staff> findDistinctPharmacistInRange(List<WorkTime> workTimeInRange, List<Appointment> overlappingAppointmentsWithRange) {
		List<Staff> pharmacists = new ArrayList<Staff>();
		
		for(WorkTime workTime : workTimeInRange) {
			boolean busy = false;
			for(Appointment appointment : overlappingAppointmentsWithRange) {
				if(workTime.getStaff().getId().equals(appointment.getStaff().getId())) {
					busy = true;
					break;
				}
			}
			
			if(!busy) pharmacists.add(workTime.getStaff());
		}
		
		List<Staff> returnPharmacists = getDistinctPharmacists(pharmacists);
		
		return returnPharmacists;
	}

	private List<Staff> getDistinctPharmacists(List<Staff> pharmacists) {
		List<Staff> returnPharmacists = new ArrayList<Staff>();
		
		for(Staff pharmacist : pharmacists) {
			boolean added = false;
			for(Staff rePharmacist : returnPharmacists) {
				if(pharmacist.getId().equals(rePharmacist.getId())) {
					added = true;
					break;
				}
			}
			
			if(!added) returnPharmacists.add(pharmacist);
		}
		return returnPharmacists;
	}

	@Override
	public UUID createConsultation(ConsultationRequestDTO requestDTO) throws AppointmentNotScheduledException, AlreadyBeenScheduledConsultationException, AppointmentTimeOverlappingWithOtherAppointmentException {
		
		long time = requestDTO.getStartDateTime().getTime();
		Date endDateTime= new Date(time + (Integer.parseInt(env.getProperty("consultation_time")) * 60000));
		checkIfConsultationTimeIsValid(requestDTO, endDateTime);
		
		Appointment appointment = createConsultationAppointmentFromDTO(requestDTO, endDateTime);

		CanCreateConsultation(appointment);
		
		appointmentRepository.save(appointment);
		try {
			emailService.sendAppointmentReservationNotificationAsync(appointment,"pharmacist");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return appointment.getId();
	}
	
	private void CanCreateConsultation(Appointment appointment) throws AlreadyBeenScheduledConsultationException, AppointmentTimeOverlappingWithOtherAppointmentException {
		
		if(doesPatientHaveAppointmentInDesiredTime(appointment, appointment.getPatient()))
			throw new AppointmentTimeOverlappingWithOtherAppointmentException("Cannot reserve appointment at same time as other appointment");
		
		if(appointment.getPatient().getPenalty() >= Integer.parseInt(env.getProperty("max_penalty_count")))
			throw new AuthorizationServiceException("Too many penalty points");

		if(appointmentRepository.findConsultationsByAppointmentTimePharmacistAndPatient(appointment.getStartDateTime(),
																						appointment.getStaff().getId(),
																						appointment.getPatient().getId()) != null)
			
			throw new AlreadyBeenScheduledConsultationException("Cannot schedule appointment at same pharmacist at same time more than once");
	}

	private boolean doesPatientHaveAppointmentInDesiredTime(Appointment appointment, Patient patient) {
		return appointmentRepository.findAllAppointmentsByAppointmentTimeAndPatient(appointment.getStartDateTime(), appointment.getEndDateTime(), patient.getId()).size() > 0;
	}

	private Appointment createConsultationAppointmentFromDTO(ConsultationRequestDTO requestDTO, Date endDateTime) {
		
		UUID patientId = userService.getLoggedUserId();
		
		Staff staff = staffRepository.findById(requestDTO.getPharmacistId()).get();
		Pharmacy pharmacy = pharmacistRepository.findPharmacyByPharmacistId(requestDTO.getPharmacistId());
		Patient patient = patientRepository.findById(patientId).get();
		double discountPrice = loyalityProgramService.getDiscountAppointmentPriceForPatient(pharmacy.getConsultationPrice(), AppointmentType.CONSULTATION);
		
		return new Appointment(pharmacy, staff, patient, requestDTO.getStartDateTime(), endDateTime, discountPrice, AppointmentType.CONSULTATION, AppointmentStatus.SCHEDULED);
	}

	@SuppressWarnings("deprecation")
	private void checkIfConsultationTimeIsValid(ConsultationRequestDTO requestDTO, Date endDateTime) throws AppointmentNotScheduledException {
		
		
		List<Appointment> overlappingAppointment = appointmentRepository.findAllConsultationsByAppointmentTimeAndPharmacist(requestDTO.getStartDateTime(), endDateTime, requestDTO.getPharmacistId());
		
		if(overlappingAppointment.size() > 0) throw new AppointmentNotScheduledException("Invalid appointment time");
		
		WorkTime pharmacistWorkTime = workTimeRepository.findWorkTimeByDesiredConsultationTimeAndPharmacistId(
						new Date(endDateTime.getYear(), endDateTime.getMonth(), endDateTime.getDate(),0,0,0),
						endDateTime.getMinutes() == 0 ? endDateTime.getHours() : endDateTime.getHours() + 1, requestDTO.getPharmacistId());
		
		if(pharmacistWorkTime == null) throw new AppointmentNotScheduledException("Invalid appointment time");
	}

}
