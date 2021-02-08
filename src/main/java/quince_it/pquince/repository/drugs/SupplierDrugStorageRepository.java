package quince_it.pquince.repository.drugs;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.SupplierDrugStorage;
import quince_it.pquince.entities.drugs.SupplierDrugStorageId;

public interface SupplierDrugStorageRepository extends JpaRepository<SupplierDrugStorage, SupplierDrugStorageId>{
	
	@Query(value = "SELECT d.count FROM DrugStorage d WHERE d.drugStorageId.drugInstance.id = ?1")
	int getDrugCountForDrug(UUID drugId);
	
}
