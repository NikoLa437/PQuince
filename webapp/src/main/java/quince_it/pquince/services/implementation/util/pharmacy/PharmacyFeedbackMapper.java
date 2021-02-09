package quince_it.pquince.services.implementation.util.pharmacy;

import quince_it.pquince.entities.pharmacy.PharmacyFeedback;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFeedbackDTO;

public class PharmacyFeedbackMapper {

	public static PharmacyFeedbackDTO MapPharmacyFeedbackPersistenceToPharmacyFeedbackDTO(PharmacyFeedback pharmacyFeedback) {
		if(pharmacyFeedback == null) throw new IllegalArgumentException();
		
		return new PharmacyFeedbackDTO(pharmacyFeedback.getPharmacy().getId(), pharmacyFeedback.getDate(), pharmacyFeedback.getGrade());
	}
}
