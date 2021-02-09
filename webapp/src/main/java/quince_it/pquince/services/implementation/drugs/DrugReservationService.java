package quince_it.pquince.services.implementation.drugs;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.entities.drugs.DrugReservation;
import quince_it.pquince.entities.drugs.ReservationStatus;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Dermatologist;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Pharmacist;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.DrugPriceForPharmacyRepository;
import quince_it.pquince.repository.drugs.DrugReservationRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.PharmacistRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationRequestDTO;
import quince_it.pquince.services.contracts.dto.drugs.StaffDrugReservationDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugReservationService;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugStorageService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.services.implementation.util.drugs.DrugReservationMapper;

@Service
public class DrugReservationService implements IDrugReservationService{

	@Autowired
	private DrugReservationRepository drugReservationRepository;
	
	@Autowired
	private DrugPriceForPharmacyRepository drugPriceForPharmacyRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private DrugInstanceRepository drugInstanceRepository;
	
	@Autowired
	private IDrugStorageService drugStorageService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private IUserService userService;

	@Override
	public IdentifiableDTO<DrugReservationDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(DrugReservationRequestDTO entityDTO) {
		
		UUID patientId = userService.getLoggedUserId();
		Patient patient = patientRepository.getOne(patientId);
		DrugReservation drugReservation = new DrugReservation(pharmacyRepository.getOne(entityDTO.getPharmacyId()),
															  drugInstanceRepository.getOne(entityDTO.getDrugId()),
															  patient,
															  entityDTO.getDrugAmount(), entityDTO.getEndDate(), entityDTO.getDrugPrice());
		
		CanReserveDrug(drugReservation, patient);
		
		drugStorageService.reduceAmountOfReservedDrug(entityDTO.getDrugId(), entityDTO.getPharmacyId(), entityDTO.getDrugAmount());
		drugReservationRepository.save(drugReservation);
		
		try {
			emailService.sendDrugReservationNotificaitionAsync(drugReservation);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return drugReservation.getId();
	}
	
	private void CanReserveDrug(DrugReservation drugReservation,Patient patient) {
			
		if(!(drugReservation.getEndDate().compareTo(new Date()) > 0 && drugReservation.getEndDate().compareTo(drugReservation.getStartDate()) > 0))
			throw new IllegalArgumentException("Invalid arguments.");

		if(!(patient.getPenalty() < Integer.parseInt(env.getProperty("max_penalty_count"))))
			throw new AuthorizationServiceException("Too many penalty points.");
		
	}

	@Override
	public void update(DrugReservationDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cancelDrugReservation(UUID id) {
		
		DrugReservation drugReservation = drugReservationRepository.getOne(id);
		
		canReservationBeCanceled(drugReservation);
		
		drugReservation.setReservationStatus(ReservationStatus.CANCELED);
		drugStorageService.addAmountOfCanceledDrug(drugReservation.getDrugInstance().getId(), drugReservation.getPharmacy().getId(), drugReservation.getAmount());
		drugReservationRepository.save(drugReservation);
			
	}
	
	private void canReservationBeCanceled(DrugReservation drugReservation) {
		
		LocalDateTime ldt = LocalDateTime.ofInstant(drugReservation.getEndDate().toInstant(), ZoneId.systemDefault());
		ldt = ldt.minusDays(1);
		
		if(ldt.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("Invalid arguments.");
		
	}

	@Override
	public List<IdentifiableDTO<DrugReservationDTO>> findAllByPatientId() {
		UUID patientId = userService.getLoggedUserId();
		return DrugReservationMapper.MapDrugReservationPersistenceListToDrugReservationIdentifiableDTOList(drugReservationRepository.findAllByPatientId(patientId));
	}

	@Override
	public void givePenaltyForMissedDrugReservation() {

		
		List<DrugReservation> expiredReservations = drugReservationRepository.findExpiredDrugReservations();
		for (DrugReservation drugReservation : expiredReservations) {
			
			try {
				Patient patient = patientRepository.findById(drugReservation.getPatient().getId()).get();
				
				drugStorageService.addAmountOfCanceledDrug(drugReservation.getDrugInstance().getId(), drugReservation.getPharmacy().getId(), drugReservation.getAmount());
				
				patient.addPenalty(1);
				patientRepository.save(patient);
				
				drugReservation.setReservationStatus(ReservationStatus.EXPIRED);
				drugReservationRepository.save(drugReservation);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<IdentifiableDTO<DrugReservationDTO>> findAllFutureReservationsByPatientId() {
		UUID patientId = userService.getLoggedUserId();
		return DrugReservationMapper.MapDrugReservationPersistenceListToDrugReservationIdentifiableDTOList(drugReservationRepository.findAllFutureReservationsByPatientId(patientId));

	}

	@Override
	public List<IdentifiableDTO<DrugReservationDTO>> findProcessedDrugReservationsForPatient() {
		UUID patientId = userService.getLoggedUserId();
		return DrugReservationMapper.MapDrugReservationPersistenceListToDrugReservationIdentifiableDTOList(drugReservationRepository.findProcessedDrugReservationsForPatient(patientId));

	}

	@Override
	public UUID reserveDrugAsStaff(StaffDrugReservationDTO staffDrugReservationDTO) {
		//TODO: validation and exceptions
		UUID staffId = userService.getLoggedUserId();
		Patient patient = patientRepository.getOne(staffDrugReservationDTO.getPatientId());
		Staff staff = staffRepository.getOne(staffId);
		Pharmacy pharmacy;
		if(staff.getStaffType() == StaffType.DERMATOLOGIST)
			pharmacy = userService.getPharmacyForLoggedDermatologist();
		else
			pharmacy = pharmacistRepository.getOne(staffId).getPharmacy();
		DrugInstance drugInstance = drugInstanceRepository.getOne(staffDrugReservationDTO.getDrugInstanceId());
		int amount = staffDrugReservationDTO.getAmount();
		Integer price = drugPriceForPharmacyRepository.findCurrentDrugPrice(drugInstance.getId(), pharmacy.getId());
		long drugReservationDuration = Integer.parseInt(env.getProperty("drug_reservation_duration"));
		long currentTime = new Date().getTime();
		Date endDate = new Date(currentTime + (1000 * 60 * 60 * 24 * drugReservationDuration));
		DrugReservation drugReservation = new DrugReservation(pharmacy, drugInstance, patient, amount, endDate, price);
		CanReserveDrug(drugReservation, patient);
		drugStorageService.reduceAmountOfReservedDrug(drugInstance.getId(), pharmacy.getId(), amount);
		drugReservationRepository.save(drugReservation);
		
		try {
			emailService.sendDrugReservationNotificaitionAsync(drugReservation);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return drugReservation.getId();
	}

	@Override
	public IdentifiableDTO<DrugReservationDTO> getDrugReservation(UUID reservationId) {
		UUID pharmacistId = userService.getLoggedUserId();
		Pharmacist pharmacist = pharmacistRepository.getOne(pharmacistId);
		UUID pharmacyId = pharmacist.getPharmacy().getId();
		List<DrugReservation> drugReservations = drugReservationRepository.findByStatusAndIdAndPharmacy(reservationId, pharmacyId);
		if (drugReservations.isEmpty())
			throw new EntityNotFoundException();
		DrugReservation drugReservation = drugReservations.get(0);
		Date currentDateTime = new Date();
		if (drugReservation.getEndDate().getTime() - currentDateTime.getTime() < 24 * 60 * 60 * 1000)
			throw new EntityNotFoundException();
		return DrugReservationMapper.MapDrugReservationPersistenceToDrugReservationIdentifiableDTO(drugReservation);
	}

	@Override
	public void processReservation(UUID drugReservationId) throws MailException, InterruptedException, MessagingException {
		DrugReservation drugReservation = drugReservationRepository.getOne(drugReservationId);
		drugReservation.setReservationStatus(ReservationStatus.PROCESSED);
		drugReservationRepository.save(drugReservation);
		emailService.sendDrugReservationProcessedNotificaitionAsync(drugReservation);
	}

}
