package quince_it.pquince.services.contracts.interfaces.pharmacy;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.pharmacy.EditPharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradePriceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IPharmacyService extends IService<PharmacyDTO, IdentifiableDTO<PharmacyDTO>>{

	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGrades();
	
	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(PharmacyFiltrationDTO pharmacyFiltrationDTO);
	
	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameDescending(PharmacyFiltrationDTO pharmacyFiltrationDTO);

	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameAscending(PharmacyFiltrationDTO pharmacyFiltrationDTO);

	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameDescending(PharmacyFiltrationDTO pharmacyFiltrationDTO);

	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeAscending(PharmacyFiltrationDTO pharmacyFiltrationDTO);

	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeDescending(PharmacyFiltrationDTO pharmacyFiltrationDTO);
	
	List<IdentifiableDTO<PharmacyGradeDTO>> findByNameGradeAndDistance(PharmacyFiltrationDTO pharmacyFiltrationDTO);

	IdentifiableDTO<PharmacyGradeDTO> findByIdWithGrade(UUID id);
	
	List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPrice(Date startDateTime);
	
	List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByGradeAscending(Date startDateTime);
	
	List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByGradeDescending(Date startDateTime);
	
	List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByPriceAscending(Date startDateTime);
	
	List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByPriceDescending(Date startDateTime);

	void updatePharmacy(EditPharmacyDTO editPharmacyDTO);
}
