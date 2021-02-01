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

	/*
	 * Pharmacy pharmacy, DrugInstance drugInstance, Patient patient, int amount,
	 * Date endDate,double drugPeacePrice
	 */
	@Override
	public UUID create(DrugReservationRequestDTO entityDTO) {
		
		UUID patientId = userService.getLoggedUserId();
		Patient patient = patientRepository.getOne(patientId);
		DrugReservation drugReservation = new DrugReservation(pharmacyRepository.getOne(entityDTO.getPharmacyId()),
															  drugInstanceRepository.getOne(entityDTO.getDrugId()),
															  patient,
															  entityDTO.getDrugAmount(), entityDTO.getEndDate(), entityDTO.getDrugPrice());
		
		if(!CanReserveDrug(drugReservation, patient))
			throw new IllegalArgumentException();
		else {
			drugReservationRepository.save(drugReservation);
			drugStorageService.reduceAmountOfReservedDrug(entityDTO.getDrugId(), entityDTO.getPharmacyId(), entityDTO.getDrugAmount());
		}
		try {
			emailService.sendDrugReservationNotificaitionAsync(drugReservation);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return drugReservation.getId();
	}
	
	private boolean CanReserveDrug(DrugReservation drugReservation,Patient patient) {
			
		if(drugReservation.getEndDate().compareTo(new Date()) > 0 && drugReservation.getEndDate().compareTo(drugReservation.getStartDate()) > 0
				&& patient.getPenalty() < Integer.parseInt(env.getProperty("max_penalty_count")))
			return true;
		
		return false;
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
	public boolean cancelDrugReservation(UUID id) {
		
		try {
			DrugReservation drugReservation = drugReservationRepository.getOne(id);
						
			if(!canReservationBeCanceled(drugReservation)) return false;
			
			drugReservation.setReservationStatus(ReservationStatus.CANCELED);
			drugReservationRepository.save(drugReservation);
			drugStorageService.addAmountOfCanceledDrug(drugReservation.getDrugInstance().getId(), drugReservation.getPharmacy().getId(), drugReservation.getAmount());
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean canReservationBeCanceled(DrugReservation drugReservation) {
		
		LocalDateTime ldt = LocalDateTime.ofInstant(drugReservation.getEndDate().toInstant(), ZoneId.systemDefault());
		ldt = ldt.minusDays(1);
		
		if(ldt.isBefore(LocalDateTime.now())) return false;
		
		return true;
	}

	@Override
	public List<IdentifiableDTO<DrugReservationDTO>> findAllByPatientId() {
		UUID patientId = userService.getLoggedUserId();
		return DrugReservationMapper.MapDrugReservationPersistenceListToDrugReservationIdentifiableDTOList(drugReservationRepository.findAllByPatientId(patientId));
	}

	@Override
	public void givePenaltyForMissedDrugReservation() {

		try {
			List<DrugReservation> expiredReservations = drugReservationRepository.findExpiredDrugReservations();
			for (DrugReservation drugReservation : expiredReservations) {
				
				drugReservation.setReservationStatus(ReservationStatus.EXPIRED);
				drugReservationRepository.save(drugReservation);
				
				Patient patient = patientRepository.findById(drugReservation.getPatient().getId()).get();
				patient.addPenalty(1);
				patientRepository.save(patient);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
