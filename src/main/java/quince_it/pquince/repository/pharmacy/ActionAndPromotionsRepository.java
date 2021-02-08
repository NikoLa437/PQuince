package quince_it.pquince.repository.pharmacy;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.pharmacy.ActionAndPromotion;
import quince_it.pquince.entities.pharmacy.ActionAndPromotionType;

public interface ActionAndPromotionsRepository extends JpaRepository<ActionAndPromotion, UUID>{

	@Query(value = "SELECT a from ActionAndPromotion a WHERE a.pharmacy.id = ?1 AND"
			+ " a.dateFrom <= CURRENT_TIMESTAMP AND a.dateTo >= CURRENT_TIMESTAMP AND a.actionAndPromotionType = ?2")
	ActionAndPromotion findCurrentActionAndPromotionForPharmacyForActionType(UUID pharmacyId, ActionAndPromotionType actionAndPromotionType);
	
	@Query(value = "SELECT a from ActionAndPromotion a WHERE a.pharmacy.id = ?1")
	List<ActionAndPromotion> findAllActionAndPromotionForPharmacy(UUID pharmacyId);

	@Query(value = "SELECT a from ActionAndPromotion a WHERE a.pharmacy.id = ?1 "
			 + "AND DATE(a.dateFrom) <= DATE(?4) AND DATE(?3) <= DATE(a.dateTo) AND a.actionAndPromotionType = ?2")
	List<ActionAndPromotion> findByPharmacyAndTypeInDateRange(UUID pharmacyId,
			ActionAndPromotionType actionAndPromotionType, Date dateFrom, Date dateTo);
}
