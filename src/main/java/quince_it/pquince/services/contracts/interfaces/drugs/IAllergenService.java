package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IAllergenService {
	
	List<IdentifiableDTO<AllergenDTO>> getPatientAllergens(UUID patientId);
	List<IdentifiableDTO<AllergenDTO>> findAll();
	IdentifiableDTO<AllergenDTO> findById(UUID id);
}
