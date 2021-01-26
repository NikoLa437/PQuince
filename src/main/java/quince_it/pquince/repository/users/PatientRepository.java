package quince_it.pquince.repository.users;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.User;

public interface PatientRepository extends JpaRepository<Patient, UUID>{
	@Query(value = "SELECT p from Patient p WHERE LOWER(p.name) LIKE %?1% AND LOWER(p.surname) LIKE %?2%")
    List<User> findByNameAndSurname(String name, String surname);
}
