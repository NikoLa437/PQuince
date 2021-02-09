package quince_it.pquince.repository.pharmacy;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.pharmacy.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, UUID>{

	@Query(value = "SELECT p from Pharmacy p WHERE LOWER(p.name) LIKE %?1% AND LOWER(p.address.city) LIKE %?2%")
	List<Pharmacy> findByNameAndCity(String name, String city);
}
