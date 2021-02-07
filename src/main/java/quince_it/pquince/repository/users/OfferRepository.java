package quince_it.pquince.repository.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.users.Absence;
import quince_it.pquince.entities.users.Offer;

public interface OfferRepository extends JpaRepository<Offer, UUID>{

}
