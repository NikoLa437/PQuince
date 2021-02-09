package quince_it.pquince.repository.users;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.users.Authority;

public interface AuthorityRepository  extends JpaRepository<Authority, UUID>{
	Authority findByName ( String name );
}
