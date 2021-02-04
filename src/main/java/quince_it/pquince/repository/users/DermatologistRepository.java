package quince_it.pquince.repository.users;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Dermatologist;

public interface DermatologistRepository extends JpaRepository<Dermatologist, UUID>{
	@Query(value = "SELECT d from Dermatologist d WHERE LOWER(d.name) LIKE %?1% AND LOWER(d.surname) LIKE %?2%")
	List<Dermatologist> findByNameAndSurname(String name, String surname);

}
