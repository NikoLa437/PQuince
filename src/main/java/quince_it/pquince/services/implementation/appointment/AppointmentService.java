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
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.appointment.AppointmentStatus;
import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.entities.users.WorkTime;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.PharmacistRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.repository.users.WorkTimeRepository;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.ConsultationRequestDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentWithPharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.StaffGradeDTO;
import quince_it.pquince.services.contracts.exceptions.AppointmentNotScheduledException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.appointment.IAppointmentService;
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
	private Environment env;
	
	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdentifiableDTO<DermatologistAppointmentDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(DermatologistAppointmentDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(DermatologistAppointmentDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentType(UUID pharmacyId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllFreeAppointmentsByPharmacyAndAppointmentType(pharmacyId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(StaffType.DERMATOLOGIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades);
		
		return returnAppointments;
	}

	@Override
	public boolean reserveAppointment(UUID appointmentId, UUID patientId) {
		
		try {
			Appointment appointment = appointmentRepository.findById(appointmentId).get();
			Patient patient = patientRepository.findById(patientId).get();

			if(!CanReserveAppointment(appointment, patient)) return false;
			
			appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);
			appointment.setPatient(patient);
			
			appointmentRepository.save(appointment);
			emailService.sendAppointmentReservationNotificationAsync(appointment, "dr.");
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean CanReserveAppointment(Appointment appointment,Patient patient) {
		
		if(appointment.getStartDateTime().after(new Date()) && patient.getPenalty() < Integer.parseInt(env.getProperty("max_penalty_count")) &&
				(appointment.getAppointmentStatus().equals(AppointmentStatus.CREATED) || appointment.getAppointmentStatus().equals(AppointmentStatus.CANCELED)))
			return true;
		
		return false;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(UUID pharmacyId, AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(pharmacyId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(StaffType.DERMATOLOGIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(
			UUID pharmacyId, AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(pharmacyId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(StaffType.DERMATOLOGIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades);
		
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
			appointment.setPatient(null);
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
	public List<IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>> findAllFutureAppointmentsForPatient(UUID patientId, AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllFutureAppointmentsForPatient(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) 
																											  ? StaffType.DERMATOLOGIST :
																												StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>> returnAppointments = AppointmentMapper
																									.MapAppointmentPersistenceListToAppointmentWithPharmacyIdentifiableDTOList
																									(appointments, staffWithGrades);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByDateAscending(UUID patientId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatientSortByDateAscending(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) 
																											  ? StaffType.DERMATOLOGIST :
																												StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByDateDescending(UUID patientId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatientSortByDateDescending(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) ? 
																																		StaffType.DERMATOLOGIST :
																																		StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByPriceAscending(UUID patientId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatientSortByPriceAscending(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) ? 
																																		StaffType.DERMATOLOGIST :
																																		StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByPriceDescending(UUID patientId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatientSortByPriceDescending(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) ? 
																													StaffType.DERMATOLOGIST :
																													StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByTimeAscending(UUID patientId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatientSortByTimeAscending(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) ? 
																																		StaffType.DERMATOLOGIST :
																																		StaffType.PHARMACIST);
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByTimeDescending(UUID patientId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatientSortByTimeDescending(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) ? 
																												StaffType.DERMATOLOGIST :
																												StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades);
		
		return returnAppointments;
	}

	@Override
	public List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatient(UUID patientId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllPreviousAppointmentsForPatient(patientId, appointmentType);
		List<IdentifiableDTO<StaffGradeDTO>> staffWithGrades = userService.findAllStaffWithAvgGradeByStaffType(appointmentType.equals(AppointmentType.EXAMINATION) ? 
																													StaffType.DERMATOLOGIST :
																													StaffType.PHARMACIST);
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades);
		
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
	public UUID createConsultation(ConsultationRequestDTO requestDTO) throws AppointmentNotScheduledException {
		
		long time = requestDTO.getStartDateTime().getTime();
		Date endDateTime= new Date(time + (Integer.parseInt(env.getProperty("consultation_time")) * 60000));
		checkIfConsultationTimeIsValid(requestDTO, endDateTime);
		
		
		Appointment appointment = createConsultationAppointmentFromDTO(requestDTO, endDateTime);
		appointmentRepository.save(appointment);
		try {
			emailService.sendAppointmentReservationNotificationAsync(appointment,"pharmacist");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return appointment.getId();
	}
	
	private Appointment createConsultationAppointmentFromDTO(ConsultationRequestDTO requestDTO, Date endDateTime) {
		//TODO DULE: get Logged patient
		
		Staff staff = staffRepository.findById(requestDTO.getPharmacistId()).get();
		Pharmacy pharmacy = pharmacistRepository.findPharmacyByPharmacistId(requestDTO.getPharmacistId());
		Patient patient = patientRepository.findById(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")).get();

		return new Appointment(pharmacy, staff, patient, requestDTO.getStartDateTime(), endDateTime, pharmacy.getConsultationPrice(), AppointmentType.CONSULTATION, AppointmentStatus.SCHEDULED);
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
