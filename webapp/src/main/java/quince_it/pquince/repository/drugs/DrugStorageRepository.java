package quince_it.pquince.repository.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.DrugStorage;
import quince_it.pquince.entities.drugs.DrugStorageId;

public interface DrugStorageRepository extends JpaRepository<DrugStorage, DrugStorageId>{
	
	@Query(value = "SELECT d.count FROM DrugStorage d WHERE d.drugStorageId.drugInstance.id = ?1 AND d.drugStorageId.pharmacy.id = ?2")
	int getDrugCountForDrug(UUID drugId, UUID pharmacyId);
	
	@Query(value = "SELECT d FROM DrugStorage d WHERE d.drugStorageId.drugInstance.id = ?1 AND d.drugStorageId.pharmacy.id = ?2")
	DrugStorage findByDrugIdAndPharmacyId(UUID drugId, UUID pharmacyId);
	
	@Query(value = "SELECT d FROM DrugStorage d WHERE d.drugStorageId.pharmacy.id = ?1")
	List<DrugStorage> findAllBPharmacyId(UUID pharmacyId);
	
}
