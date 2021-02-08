package quince_it.pquince.services.implementation.util.pharmacy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import quince_it.pquince.entities.pharmacy.ActionAndPromotion;
import quince_it.pquince.entities.pharmacy.ActionAndPromotionType;
import quince_it.pquince.entities.pharmacy.PharmacyFeedback;
import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.ActionAndPromotionsDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFeedbackDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class ActionAndPromotionMapper {
	
	
	public static IdentifiableDTO<ActionAndPromotionsDTO> MapActionAndPromotionPersistenceToActionAndPromotionDTO(ActionAndPromotion actionAndPromotion) {
		if(actionAndPromotion == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<ActionAndPromotionsDTO>(actionAndPromotion.getId(), new ActionAndPromotionsDTO(actionAndPromotion.getDateFrom(),actionAndPromotion.getDateTo(),actionAndPromotion.getPercentOfDiscount(),actionAndPromotion.getActionAndPromotionType()));
	}
	
	public static List<IdentifiableDTO<ActionAndPromotionsDTO>> MapListOfActionAndPromotionPersistenceToListOfActionAndPromotionDTO(List<ActionAndPromotion> actionAndPromotions){
		List<IdentifiableDTO<ActionAndPromotionsDTO>> retVal = new ArrayList<IdentifiableDTO<ActionAndPromotionsDTO>>();
		actionAndPromotions.forEach((a) -> retVal.add(MapActionAndPromotionPersistenceToActionAndPromotionDTO(a)));
		return retVal;
	}
}
