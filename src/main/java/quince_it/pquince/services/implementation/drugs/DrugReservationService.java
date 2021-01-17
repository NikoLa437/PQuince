package quince_it.pquince.services.implementation.drugs;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugReservation;
import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.DrugReservationRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationRequestDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugReservationService;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugStorageService;
import quince_it.pquince.services.implementation.users.mail.EmailService;

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

	@Override
	public List<IdentifiableDTO<DrugReservationDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

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
		DrugReservation drugReservation = new DrugReservation(pharmacyRepository.getOne(entityDTO.getPharmacyId()),
															  drugInstanceRepository.getOne(entityDTO.getDrugId()),
															  patientRepository.getOne(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")),
															  entityDTO.getDrugAmount(), entityDTO.getEndDate(), entityDTO.getDrugPrice());
		
		drugReservationRepository.save(drugReservation);
		drugStorageService.reduceAmountOfReservedDrug(entityDTO.getDrugId(), entityDTO.getPharmacyId(), entityDTO.getDrugAmount());
		
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

	@Override
	public void update(DrugReservationDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}
	


}
