package quince_it.pquince.services.contracts.dto.users;

import java.util.UUID;

import quince_it.pquince.entities.users.Address;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class IdentifiableStaffGradeDTO extends IdentifiableDTO<StaffGradeDTO> {

	public IdentifiableStaffGradeDTO() {
		super();
	}

	public IdentifiableStaffGradeDTO(UUID id, String email, String name, String surname, Address address, String phoneNumber, double grade) {
		super(id, new StaffGradeDTO(email, name, surname, address, phoneNumber, grade));
	}

}
