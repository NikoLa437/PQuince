package quince_it.pquince.repository.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.entities.users.Dermatologist;

public interface DrugInstanceRepository extends JpaRepository<DrugInstance, UUID>{
	
	@Query(value = "SELECT d from DrugInstance d WHERE LOWER(d.drugInstanceName) LIKE %?1%")
	List<DrugInstance> findByName(String name);
}
