package quince_it.pquince.repository.drugs;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.drugs.DrugKindId;
import quince_it.pquince.entities.users.User;

public interface DrugKindRepository  extends JpaRepository<DrugKindId, UUID>{
	 User findByType ( String type );
}
