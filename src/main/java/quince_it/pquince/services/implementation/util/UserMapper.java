package quince_it.pquince.services.implementation.util;

import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.services.contracts.dto.users.CityDTO;
import quince_it.pquince.services.contracts.dto.users.PatientDTO;
import quince_it.pquince.services.contracts.dto.users.UserDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class UserMapper {
	
	public static IdentifiableDTO<UserDTO> MapUserPersistenceToUserIdentifiableDTO(User user){
		if(user == null) throw new IllegalArgumentException();

		
		return new IdentifiableDTO<UserDTO>(user.getId(), new UserDTO(user.getEmail(), user.getName(), user.getSurname(), user.getAddress(),
											new IdentifiableDTO<CityDTO>(user.getCity().getId(), new CityDTO(user.getCity().getName(), user.getCity().getCountry().getId())),
											user.getPhoneNumber(), user.isActive(), user.getUserAuthorities()));

	}
	
	public static IdentifiableDTO<PatientDTO> MapPatientPersistenceToPatientIdentifiableDTO(Patient patient){
		if(patient == null) throw new IllegalArgumentException();

		
		return new IdentifiableDTO<PatientDTO>(patient.getId(), new PatientDTO(patient.getEmail(), patient.getName(), patient.getSurname(), patient.getAddress(),
												new IdentifiableDTO<CityDTO>(patient.getCity().getId(), new CityDTO(patient.getCity().getName(), patient.getCity().getCountry().getId())),
												patient.getPhoneNumber(), patient.isActive(), patient.getUserAuthorities(), patient.getPenalty(), patient.getAllergens(), patient.getPoints()));

	}
}
