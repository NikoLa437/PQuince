package quince_it.pquince.repository.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.DrugRequest;

public interface DrugRequestRepository extends JpaRepository<DrugRequest, UUID> {

	@Query(value = "SELECT d FROM DrugRequest d WHERE d.pharmacy.id = ?1")
	List<DrugRequest> findAllForPharmacy(UUID pharmacyId);

}
