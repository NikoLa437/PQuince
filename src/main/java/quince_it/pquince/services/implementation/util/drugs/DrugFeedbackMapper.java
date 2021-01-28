package quince_it.pquince.services.implementation.util.drugs;

import quince_it.pquince.entities.drugs.DrugFeedback;
import quince_it.pquince.services.contracts.dto.drugs.DrugFeedbackDTO;

public class DrugFeedbackMapper {

	public static DrugFeedbackDTO MapDrugFeedbackPersistenceToPDrugFeedbackDTO(DrugFeedback drugFeedback) {
		if(drugFeedback == null) throw new IllegalArgumentException();
		
		return new DrugFeedbackDTO(drugFeedback.getDrug().getId(), drugFeedback.getDate(), drugFeedback.getGrade());
	}
}
