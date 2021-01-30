package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.DrugFeedbackDTO;

public interface IDrugFeedbackService {

	void create(DrugFeedbackDTO entityDTO);
	
	void update(DrugFeedbackDTO entityDTO);
	
	DrugFeedbackDTO findByPatientAndDrug(UUID drugId);

}
