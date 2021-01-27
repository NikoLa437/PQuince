package quince_it.pquince.services.contracts.interfaces.pharmacy;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IPharmacyService extends IService<PharmacyDTO, IdentifiableDTO<PharmacyDTO>>{

	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGrades();
	
	List<IdentifiableDTO<PharmacyGradeDTO>> findByNameGradeAndDistance(PharmacyFiltrationDTO pharmacyFiltrationDTO);

	IdentifiableDTO<PharmacyGradeDTO> findByIdWithGrade(UUID id);

}
