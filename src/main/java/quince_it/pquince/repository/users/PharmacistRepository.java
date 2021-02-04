package quince_it.pquince.repository.users;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Pharmacist;

public interface PharmacistRepository extends JpaRepository<Pharmacist, UUID>{

	@Query(value = "SELECT p.pharmacy FROM Pharmacist p WHERE p.id = ?1")
	Pharmacy findPharmacyByPharmacistId(UUID pharmacistId);
	
	@Query(value = "SELECT p FROM Pharmacist p WHERE p.pharmacy.id = ?1")
	List<Pharmacist> findAllPharmacistsForPharmacy(UUID pharmacyId);
	
	@Query(value = "SELECT p FROM Pharmacist p WHERE p.pharmacy.id is null")
	List<Pharmacist> findAllPharmacistForEmployment();
}
