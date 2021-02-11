package quince_it.pquince.repository.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.Order;


public interface OrderRepository extends JpaRepository<Order, UUID>{
	
	@Query(value = "SELECT o from Order o WHERE o.pharmacy.id = ?1")
	List<Order> findDrugOrderForPharmacy(UUID pharmacyId);

	@Query(value = "SELECT o from Order o WHERE o.pharmacy.id = ?1 AND o.orderStatus='CREATED'")
	List<Order> findFilteredCreatedOrderForPharmacy(UUID pharmacyId);

	@Query(value = "SELECT o from Order o WHERE o.pharmacy.id = ?1 AND o.orderStatus='PROCESSED'")
	List<Order> findFilteredProcessedOrderForPharmacy(UUID pharmacyId);
}
