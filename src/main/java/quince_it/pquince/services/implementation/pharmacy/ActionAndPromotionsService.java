package quince_it.pquince.services.implementation.pharmacy;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.pharmacy.ActionAndPromotion;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.repository.pharmacy.ActionAndPromotionsRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.services.contracts.dto.pharmacy.ActionAndPromotionsDTO;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IActionAndPromotionsService;
import quince_it.pquince.services.implementation.users.UserService;

@Service
public class ActionAndPromotionsService implements IActionAndPromotionsService{
	@Autowired 
	private ActionAndPromotionsRepository actionAndPromotionsRepository;

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void create(ActionAndPromotionsDTO actionAndPromotions) {
		
		UUID pharmacyId= this.userService.getPharmacyIdForPharmacyAdmin();
		Pharmacy pharmacy = pharmacyRepository.getOne(pharmacyId);
		
		
		ActionAndPromotion newActionAndPromotion= new ActionAndPromotion(actionAndPromotions.getDateFrom(),actionAndPromotions.getDateTo(),pharmacy,actionAndPromotions.getPercentOfDiscount(),actionAndPromotions.getActionAndPromotionType());
		
		actionAndPromotionsRepository.save(newActionAndPromotion);
	}
	
	
}
