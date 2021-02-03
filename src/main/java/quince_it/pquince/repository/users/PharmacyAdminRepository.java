package quince_it.pquince.repository.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.users.PharmacyAdmin;


public interface PharmacyAdminRepository extends JpaRepository<PharmacyAdmin, UUID>{

}
