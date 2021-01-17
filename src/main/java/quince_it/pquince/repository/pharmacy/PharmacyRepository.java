package quince_it.pquince.repository.pharmacy;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.pharmacy.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, UUID>{

}
