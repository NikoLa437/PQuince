package quince_it.pquince.repository.drugs;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.drugs.DrugStorage;
import quince_it.pquince.entities.drugs.DrugStorageId;

public interface DrugStorageRepository extends JpaRepository<DrugStorage, DrugStorageId>{

}
