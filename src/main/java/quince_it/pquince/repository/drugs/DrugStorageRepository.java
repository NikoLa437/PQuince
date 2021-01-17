package quince_it.pquince.repository.drugs;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.DrugStorage;
import quince_it.pquince.entities.drugs.DrugStorageId;

public interface DrugStorageRepository extends JpaRepository<DrugStorage, DrugStorageId>{
	
	@Query(value = "SELECT d.count FROM DrugStorage d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2")
	int getDrugCountForDrug(UUID drugId, UUID pharmacyId);
}
