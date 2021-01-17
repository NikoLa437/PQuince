package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.repository.drugs.AllergenRepository;
import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.dto.users.PatientDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IAllergenService;
import quince_it.pquince.services.implementation.users.UserService;
import quince_it.pquince.services.implementation.util.drugs.AllergenMapper;

@Service
public class AllergenService implements IAllergenService {

	@Autowired
	private UserService userService;

	@Autowired
	private AllergenRepository allergenRepository;
	
	@Override
	public List<IdentifiableDTO<AllergenDTO>> getPatientAllergens(UUID patientId) {
		
		IdentifiableDTO<PatientDTO> patient = userService.getPatientById(patientId);
				
		return patient.EntityDTO.getAllergens();
	}

	@Override
	public List<IdentifiableDTO<AllergenDTO>> findAll() {
		
		List<IdentifiableDTO<AllergenDTO>> allergens = new ArrayList<IdentifiableDTO<AllergenDTO>>();
		allergenRepository.findAll().forEach((a) -> allergens.add(AllergenMapper.MapAllergenPersistenceToAllergenIdentifiableDTO(a)));

		return allergens;
	}

	@Override
	public IdentifiableDTO<AllergenDTO> findById(UUID id) {
		return AllergenMapper.MapAllergenPersistenceToAllergenIdentifiableDTO(allergenRepository.getOne(id));
	}
}
