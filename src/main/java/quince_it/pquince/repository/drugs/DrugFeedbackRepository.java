package quince_it.pquince.repository.drugs;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.DrugFeedback;
import quince_it.pquince.entities.drugs.DrugFeedbackId;

public interface DrugFeedbackRepository extends JpaRepository<DrugFeedback, DrugFeedbackId>{

	@Query(value = "SELECT d FROM DrugFeedback d WHERE d.drugFeedbackId.patient.id = ?1 AND d.drugFeedbackId.drug.id = ?2")
	DrugFeedback findByPatientAndDrug(UUID patientId, UUID drugId);
}
