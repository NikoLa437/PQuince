package quince_it.pquince.services.implementation.util.users;

import java.util.List;

import quince_it.pquince.entities.users.Address;
import quince_it.pquince.entities.users.Authority;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.services.contracts.dto.users.AbsenceDTO;
import quince_it.pquince.services.contracts.dto.users.PatientDTO;
import quince_it.pquince.services.contracts.dto.users.StaffDTO;
import quince_it.pquince.services.contracts.dto.users.StaffFeedbackDTO;
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

	public static IdentifiableDTO<StaffDTO> MapStaffPersistenceToStaffIdentifiableDTO(Staff staff) {
		if(staff == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<StaffDTO>(staff.getId(), new StaffDTO(staff.getEmail(), staff.getName(), staff.getSurname(), staff.getAddress(),
				staff.getPhoneNumber(), staff.isActive(), staff.getUserAuthorities()));

	}
}
