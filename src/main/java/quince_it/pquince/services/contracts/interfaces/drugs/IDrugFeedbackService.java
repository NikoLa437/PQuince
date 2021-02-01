package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.DrugFeedbackDTO;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;

public interface IDrugFeedbackService {

	void create(DrugFeedbackDTO entityDTO) throws FeedbackNotAllowedException;
	
	void update(DrugFeedbackDTO entityDTO);
	
	DrugFeedbackDTO findByPatientAndDrug(UUID drugId);

}
