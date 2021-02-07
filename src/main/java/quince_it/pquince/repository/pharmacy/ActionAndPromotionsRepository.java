package quince_it.pquince.repository.pharmacy;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.pharmacy.ActionAndPromotion;

public interface ActionAndPromotionsRepository extends JpaRepository<ActionAndPromotion, UUID>{

}
