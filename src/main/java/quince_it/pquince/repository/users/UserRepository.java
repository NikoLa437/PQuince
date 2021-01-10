package quince_it.pquince.repository.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.users.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail ( String email );

}
