package quince_it.pquince.repository.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.users.Dermatologist;

public interface DermatologistRepository extends JpaRepository<Dermatologist, UUID>{

}
