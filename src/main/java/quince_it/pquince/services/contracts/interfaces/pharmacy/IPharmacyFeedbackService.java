package quince_it.pquince.services.contracts.interfaces.pharmacy;

import java.util.UUID;

public interface IPharmacyFeedbackService {
	
	double findAvgGradeForPharmacy(UUID pharmacyId);
}
