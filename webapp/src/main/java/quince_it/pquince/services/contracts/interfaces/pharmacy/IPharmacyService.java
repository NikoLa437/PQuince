package quince_it.pquince.services.contracts.interfaces.pharmacy;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import quince_it.pquince.entities.pharmacy.IncomeStatistics;
import quince_it.pquince.entities.pharmacy.PharmacyIncomeStatistics;
import quince_it.pquince.services.contracts.dto.drugs.PharmacyERecipeDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.DrugsStatisticsDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.EditPharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.ExaminationsStatisticsDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IncomeStatisticsDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDrugPriceDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradePriceDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyIncomeStatisticsDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IPharmacyService extends IService<PharmacyDTO, IdentifiableDTO<PharmacyDTO>>{

	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGrades();
	
	List<IdentifiableDTO<PharmacyGradeDTO>> findAllSubscribedPharmaciesWithGrades();
	
	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(PharmacyFiltrationDTO pharmacyFiltrationDTO);
	
	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameDescending(PharmacyFiltrationDTO pharmacyFiltrationDTO);

	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameAscending(PharmacyFiltrationDTO pharmacyFiltrationDTO);

	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameDescending(PharmacyFiltrationDTO pharmacyFiltrationDTO);

	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeAscending(PharmacyFiltrationDTO pharmacyFiltrationDTO);

	List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeDescending(PharmacyFiltrationDTO pharmacyFiltrationDTO);
	
	List<IdentifiableDTO<PharmacyGradeDTO>> findByNameGradeAndDistance(PharmacyFiltrationDTO pharmacyFiltrationDTO);

	IdentifiableDTO<PharmacyGradeDTO> findByIdWithGrade(UUID id);
	
	List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPrice(Date startDateTime);
	
	IdentifiableDTO<PharmacyGradePriceDTO> findPharmacyByPharmacyId(UUID pharmacyId);

	List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByGradeAscending(Date startDateTime);
	
	List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByGradeDescending(Date startDateTime);
	
	List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByPriceAscending(Date startDateTime);
	
	List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByPriceDescending(Date startDateTime);

	void updatePharmacy(EditPharmacyDTO editPharmacyDTO);

	List<IdentifiableDTO<PharmacyDrugPriceDTO>> findWithQR(UUID id);

	Object buyWithQR(PharmacyERecipeDTO pharmacyERecipeDTO);

	List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(
			UUID id);

	List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByNameGradeAndDistanceSortByNameDescending(
			UUID id);

	List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByCityAscending(UUID id);

	List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByCityDescending(UUID id);

	List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByGradeDescending(UUID id);

	List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByGradeAscending(UUID id);

	List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByPriceDescending(UUID id);

	List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByPriceAscending(UUID id);

	ExaminationsStatisticsDTO findStatisticsForExaminationsAndColsutations();

	DrugsStatisticsDTO findStatisticsForDrugs();

	boolean findIfPharmacyHasQRCode(UUID pharamcyId, UUID qrID);

	PharmacyIncomeStatistics findIncomeStatisticsForPharmacy(Date dateFrom, Date dateTo) throws Exception;

	boolean canPatientUseQR(UUID id);
}
