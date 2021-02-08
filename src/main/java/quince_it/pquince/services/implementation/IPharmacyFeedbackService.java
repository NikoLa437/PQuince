package quince_it.pquince.services.implementation;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFeedbackDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IPharmacyFeedbackService {
	
	double findAvgGradeForPharmacy(UUID pharmacyId);
	
	List<IdentifiableDTO<PharmacyGradeDTO>> findByNameCityAndGrade(PharmacyFiltrationDTO pharmacyFiltrationDTO);
	
	void create(PharmacyFeedbackDTO entityDTO) throws FeedbackNotAllowedException;
	
	void update(PharmacyFeedbackDTO entityDTO);
	
	PharmacyFeedbackDTO findByPatientAndPharmacy(UUID pharmacyId);

}
