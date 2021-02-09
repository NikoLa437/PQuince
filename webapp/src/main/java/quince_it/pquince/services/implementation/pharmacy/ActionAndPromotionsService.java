package quince_it.pquince.services.implementation.pharmacy;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.pharmacy.ActionAndPromotion;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.repository.pharmacy.ActionAndPromotionsRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.services.contracts.dto.pharmacy.ActionAndPromotionsDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IActionAndPromotionsService;
import quince_it.pquince.services.implementation.users.UserService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.services.implementation.util.pharmacy.ActionAndPromotionMapper;

@Service
public class ActionAndPromotionsService implements IActionAndPromotionsService{
	@Autowired 
	private ActionAndPromotionsRepository actionAndPromotionsRepository;

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService mailService;
	
	@Override
	public boolean create(ActionAndPromotionsDTO actionAndPromotions) throws MailException, InterruptedException, MessagingException {
		
		UUID pharmacyId= this.userService.getPharmacyIdForPharmacyAdmin();
		Pharmacy pharmacy = pharmacyRepository.getOne(pharmacyId);
		
		if(!hasActionAndPromotionInDateRange(actionAndPromotions,pharmacyId)) {
			ActionAndPromotion newActionAndPromotion= new ActionAndPromotion(actionAndPromotions.getDateFrom(),actionAndPromotions.getDateTo(),pharmacy,actionAndPromotions.getPercentOfDiscount(),actionAndPromotions.getActionAndPromotionType());
			actionAndPromotionsRepository.save(newActionAndPromotion);
			sendMailForPromotion(newActionAndPromotion,pharmacyId);
			return true;
		}
		
		return false;
	}

	private void sendMailForPromotion(ActionAndPromotion actionAndPromotion, UUID pharmacyId) throws MailException, InterruptedException, MessagingException {
		List<Patient> listOfPatient = patientRepository.findAll();
		
		for(Patient patient : listOfPatient) {
			if(isPatientSubscribed(patient,pharmacyId))
				mailService.sendActionAndPromotionNotificaitionAsync(patient, actionAndPromotion);
		}
	}

	private boolean isPatientSubscribed(Patient patient, UUID pharmacyId) {
		if(patient.getPharmacies()==null)
			return false;
		
		for(Pharmacy pharmacy : patient.getPharmacies()) {
			if(pharmacy.getId().equals(pharmacyId))
				return true;
		}
		
		return false;
	}

	private boolean hasActionAndPromotionInDateRange(ActionAndPromotionsDTO actionAndPromotions, UUID pharmacyId) {
		List<ActionAndPromotion> listOfAction = actionAndPromotionsRepository.findByPharmacyAndTypeInDateRange(pharmacyId,actionAndPromotions.getActionAndPromotionType(),actionAndPromotions.getDateFrom(),actionAndPromotions.getDateTo());
	
		if(listOfAction.size()>0)
			return true;
		
		return false;
	}

	@Override
	public List<IdentifiableDTO<ActionAndPromotionsDTO>> findActionAndPromotionsInPharmacy() {
		UUID pharmacyId= this.userService.getPharmacyIdForPharmacyAdmin();
		
		return ActionAndPromotionMapper.MapListOfActionAndPromotionPersistenceToListOfActionAndPromotionDTO(actionAndPromotionsRepository.findAllActionAndPromotionForPharmacy(pharmacyId));
	}
	
	
}
