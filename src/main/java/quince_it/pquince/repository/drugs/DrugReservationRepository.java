package quince_it.pquince.repository.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.DrugReservation;

public interface DrugReservationRepository extends JpaRepository<DrugReservation, UUID> {

	@Query(value = "SELECT d FROM DrugReservation d WHERE d.patient.id = ?1 ")
	List<DrugReservation> findAllByPatientId(UUID patientId);
	
	@Query(value = "SELECT d FROM DrugReservation d WHERE d.endDate < CURRENT_TIMESTAMP AND d.reservationStatus = 'ACTIVE'")
	List<DrugReservation> findExpiredDrugReservations();
}
