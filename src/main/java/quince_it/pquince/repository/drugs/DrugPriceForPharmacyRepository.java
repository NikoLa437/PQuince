package quince_it.pquince.repository.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.DrugPriceForPharmacy;
import quince_it.pquince.entities.drugs.DrugPriceForPharmacyId;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO;

public interface DrugPriceForPharmacyRepository extends JpaRepository<DrugPriceForPharmacy, DrugPriceForPharmacyId>{

	@Query(value = "SELECT new quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO"
				 + "(d.drugPriceForPharmacyId.pharmacy.id, d.drugPriceForPharmacyId.pharmacy.name, d.drugPriceForPharmacyId.pharmacy.address, d.drugPriceForPharmacyId.pharmacy.description, d.price, 0) "
			 	 + "FROM DrugPriceForPharmacy d WHERE d.drugPriceForPharmacyId.drugInstance.id = ?1 and d.dateFrom <= CURRENT_DATE and d.dateTo >= CURRENT_DATE")
	List<IdentifiablePharmacyDrugPriceAmountDTO> findByDrugId(UUID id);
}
