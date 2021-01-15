package quince_it.pquince.services.contracts.interfaces.pharmacy;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IPharmacyFeedbackService {
	
	double findAvgGradeForPharmacy(UUID pharmacyId);
	
	List<IdentifiableDTO<PharmacyGradeDTO>> findByNameGradeAndDistance(PharmacyFiltrationDTO pharmacyFiltrationDTO);
}
