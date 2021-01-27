package quince_it.pquince.repository.pharmacy;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.pharmacy.PharmacyFeedback;
import quince_it.pquince.entities.pharmacy.PharmacyFeedbackId;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationRepositoryDTO;

public interface PharmacyFeedbackRepository extends JpaRepository<PharmacyFeedback, PharmacyFeedbackId>{
	
	@Query(value = "SELECT AVG(p.grade) FROM PharmacyFeedback p WHERE p.pharmacyId.pharmacy.id = ?1")
	double findAvgGradeForPharmacy(UUID pharmacyId);
	
	@Query(value = "SELECT new quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationRepositoryDTO(p.pharmacyId.pharmacy.id, p.pharmacyId.pharmacy.name,"
				 + " p.pharmacyId.pharmacy.address,  p.pharmacyId.pharmacy.description, AVG(p.grade)) "
				 + "FROM PharmacyFeedback p WHERE LOWER(p.pharmacyId.pharmacy.name) LIKE %?1% AND LOWER(p.pharmacyId.pharmacy.address.city) LIKE %?4% GROUP BY p.pharmacyId.pharmacy.id,"
				 + " p.pharmacyId.pharmacy.name, p.pharmacyId.pharmacy.address, p.pharmacyId.pharmacy.description "
				 + "HAVING AVG(p.grade) >= ?2 AND AVG(p.grade) <= ?3 ")
	List<PharmacyFiltrationRepositoryDTO> findByNameCityAndGrade(String name, double gradeFrom, double gradeTo, String city);
	
	
	@Query(value = "SELECT p FROM PharmacyFeedback p WHERE p.pharmacyId.pharmacy.id = ?2 AND p.pharmacyId.patient.id = ?1")
	PharmacyFeedback findByPatientAndPharmacy(UUID patientId, UUID pharmacyId);
}
