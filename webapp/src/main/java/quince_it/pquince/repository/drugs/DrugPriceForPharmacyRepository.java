package quince_it.pquince.repository.drugs;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.DrugPriceForPharmacy;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO;

public interface DrugPriceForPharmacyRepository extends JpaRepository<DrugPriceForPharmacy, UUID>{

	@Query(value = "SELECT new quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO"
				 + "(d.pharmacy.id, d.pharmacy.name, d.pharmacy.address, d.pharmacy.description, d.price, 0, 0.0) "
			 	 + "FROM DrugPriceForPharmacy d WHERE d.drugInstance.id = ?1 and DATE(d.dateFrom) <= CURRENT_DATE and DATE(d.dateTo) >= CURRENT_DATE")
	List<IdentifiablePharmacyDrugPriceAmountDTO> findByDrugId(UUID id);
	
	@Query(value = "SELECT d.price FROM DrugPriceForPharmacy d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2 "
				 + "AND DATE(d.dateFrom) <= CURRENT_DATE AND DATE(d.dateTo) >= CURRENT_DATE")
	Integer findCurrentDrugPrice(UUID drugInstanceId, UUID pharmacyId);
	
	@Query(value = "SELECT d FROM DrugPriceForPharmacy d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2 "
			 + "AND DATE(d.dateFrom) <= CURRENT_DATE AND DATE(d.dateTo) >= CURRENT_DATE")
	DrugPriceForPharmacy findDrugPriceForPharmacy(UUID drugInstanceId, UUID pharmacyId);
	
	@Query(value = "SELECT d FROM DrugPriceForPharmacy d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2 "
			 + "AND DATE(d.dateFrom) < DATE(?4) AND DATE(?3) < DATE(d.dateTo)")
	DrugPriceForPharmacy findDrugPriceForPharmacyByDateRange(UUID drugInstanceId, UUID pharmacyId, Date dateFrom, Date dateTo);
	
	@Query(value = "SELECT d FROM DrugPriceForPharmacy d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2")
	List<DrugPriceForPharmacy> findDrugPriceForPharmacyByPharmacyAndDugId(UUID drugInstanceId, UUID pharmacyId);

	
}
