package quince_it.pquince.repository.drugs;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.drugs.DrugRequest;

public interface DrugRequestRepository extends JpaRepository<DrugRequest, UUID> {

}
