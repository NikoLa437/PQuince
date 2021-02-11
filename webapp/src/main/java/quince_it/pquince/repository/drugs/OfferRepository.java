package quince_it.pquince.repository.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.Offers;

public interface OfferRepository extends JpaRepository<Offers, UUID>{

	@Query(value = "SELECT o from Offers o WHERE o.supplier.id = ?1")
	List<Offers> findBySupplier(UUID id);
}
