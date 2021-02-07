package quince_it.pquince.repository.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.DrugReservation;

public interface DrugReservationRepository extends JpaRepository<DrugReservation, UUID> {

	@Query(value = "SELECT d FROM DrugReservation d WHERE d.patient.id = ?1 ")
	List<DrugReservation> findAllByPatientId(UUID patientId);
	
	@Query(value = "SELECT d FROM DrugReservation d WHERE d.patient.id = ?1 AND d.reservationStatus = 'ACTIVE' AND d.endDate > CURRENT_TIMESTAMP")
	List<DrugReservation> findAllFutureReservationsByPatientId(UUID patientId);
	
	@Query(value = "SELECT d FROM DrugReservation d WHERE d.endDate < CURRENT_TIMESTAMP AND d.reservationStatus = 'ACTIVE'")
	List<DrugReservation> findExpiredDrugReservations();
	
	@Query(value = "SELECT d FROM DrugReservation d WHERE d.patient.id = ?1 AND d.reservationStatus = 'PROCESSED'")
	List<DrugReservation> findProcessedDrugReservationsForPatient(UUID patientId);
	
	@Query(value = "SELECT d FROM DrugReservation d WHERE d.patient.id = ?1 AND d.reservationStatus = 'PROCESSED' AND d.drugInstance.id = ?2")
	List<DrugReservation> findProcessedDrugReservationsForPatientForDrug(UUID patientId, UUID drugId);

	@Query(value = "SELECT d FROM DrugReservation d WHERE d.pharmacy.id = ?2 AND d.patient.id = ?1 AND d.reservationStatus = 'PROCESSED'")
	List<DrugReservation> findProcessedDrugReservationsForPatientForPharmacy(UUID patientId, UUID pharmacyId);

	@Query(value = "SELECT d FROM DrugReservation d WHERE d.id = ?1 AND d.pharmacy.id = ?2 AND d.reservationStatus = 'ACTIVE'")
	List<DrugReservation> findByStatusAndIdAndPharmacy(UUID reservationId, UUID pharmacyId);
}
