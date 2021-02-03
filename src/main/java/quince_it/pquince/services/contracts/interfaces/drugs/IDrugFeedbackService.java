package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.DrugFeedbackDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugsWithGradesDTO;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IDrugFeedbackService {

	void create(DrugFeedbackDTO entityDTO) throws FeedbackNotAllowedException;
	
	void update(DrugFeedbackDTO entityDTO);
	
	DrugFeedbackDTO findByPatientAndDrug(UUID drugId);

	double findAvgGradeForDrug(UUID drugId);

	List<IdentifiableDTO<DrugsWithGradesDTO>> findDrugsWithGrades();

	List<IdentifiableDTO<DrugsWithGradesDTO>> searchDrugs(String name, double gradeFrom, double gradeTo,
			String drugKind);

}
