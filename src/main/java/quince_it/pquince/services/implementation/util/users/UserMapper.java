package quince_it.pquince.services.implementation.util.users;

import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.services.contracts.dto.users.PatientDTO;
import quince_it.pquince.services.contracts.dto.users.UserDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.util.drugs.AllergenMapper;

public class UserMapper {
	
	public static IdentifiableDTO<UserDTO> MapUserPersistenceToUserIdentifiableDTO(User user){
		if(user == null) throw new IllegalArgumentException();

		
		return new IdentifiableDTO<UserDTO>(user.getId(), new UserDTO(user.getEmail(), user.getName(), user.getSurname(), user.getAddress(),
											user.getPhoneNumber(), user.isActive(), user.getUserAuthorities()));

	}
	
	public static IdentifiableDTO<PatientDTO> MapPatientPersistenceToPatientIdentifiableDTO(Patient patient){
		if(patient == null) throw new IllegalArgumentException();

		
		return new IdentifiableDTO<PatientDTO>(patient.getId(), new PatientDTO(patient.getEmail(), patient.getName(), patient.getSurname(), patient.getAddress(),
												patient.getPhoneNumber(), patient.isActive(), patient.getUserAuthorities(), patient.getPenalty(),
												AllergenMapper.MapAllergenPersistenceListToAllergenIdentifiableDTOList(patient.getAllergens()),
												patient.getPoints(),
												patient.getLoyalityCategory()));

	}
}
