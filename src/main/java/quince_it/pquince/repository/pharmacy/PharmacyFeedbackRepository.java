package quince_it.pquince.repository.pharmacy;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.pharmacy.PharmacyFeedback;
import quince_it.pquince.entities.pharmacy.PharmacyFeedbackId;

public interface PharmacyFeedbackRepository extends JpaRepository<PharmacyFeedback, PharmacyFeedbackId>{
	
	@Query(value = "SELECT AVG(p.grade) FROM PharmacyFeedback p WHERE p.pharmacy.id = ?1")
	double findAvgGradeForPharmacy(UUID pharmacyId);

}
