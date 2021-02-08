package quince_it.pquince.repository.drugs;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.drugs.Order;


public interface OrderRepository extends JpaRepository<Order, UUID>{

}
