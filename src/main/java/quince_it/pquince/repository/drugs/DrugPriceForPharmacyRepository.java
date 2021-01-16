package quince_it.pquince.repository.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.DrugPriceForPharmacy;
import quince_it.pquince.entities.drugs.DrugPriceForPharmacyId;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceDTO;

public interface DrugPriceForPharmacyRepository extends JpaRepository<DrugPriceForPharmacy, DrugPriceForPharmacyId>{

	@Query(value = "SELECT new quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceDTO"
				 + "(d.pharmacy.id, d.pharmacy.name, d.pharmacy.address, d.pharmacy.description, d.price) "
			 	 + "FROM DrugPriceForPharmacy d WHERE d.drugInstance.id = ?1 and d.dateFrom <= CURRENT_DATE and d.dateTo >= CURRENT_DATE")
	List<IdentifiablePharmacyDrugPriceDTO> findByDrugId(UUID id);
}
