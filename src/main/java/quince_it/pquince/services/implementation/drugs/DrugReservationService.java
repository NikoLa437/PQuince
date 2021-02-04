package quince_it.pquince.services.implementation.drugs;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugReservation;
import quince_it.pquince.entities.drugs.ReservationStatus;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.DrugReservationRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationRequestDTO;
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
	private PatientRepository patientRepository;
	
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
				patient.addPenalty(1);
				patientRepository.save(patient);
				
				drugReservation.setReservationStatus(ReservationStatus.EXPIRED);
				drugReservationRepository.save(drugReservation);
			} catch (ObjectOptimisticLockingFailureException e) {
				e.printStackTrace();
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
	


}
