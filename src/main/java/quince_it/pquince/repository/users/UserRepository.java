package quince_it.pquince.repository.users;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail ( String email );
	@Query(value = "SELECT u from User u WHERE LOWER(u.name) LIKE %?1% AND LOWER(u.surname) LIKE %?2%")
    List<User> findByNameAndSurname(String name, String surname);
}
