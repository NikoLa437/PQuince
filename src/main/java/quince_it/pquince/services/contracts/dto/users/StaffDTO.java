package quince_it.pquince.services.contracts.dto.users;

import java.util.List;

import quince_it.pquince.entities.users.Address;
import quince_it.pquince.entities.users.Authority;

public class StaffDTO extends UserDTO{
	public StaffDTO(String email, String name, String surname, 	Address address,
			String phoneNumber, boolean active, List<Authority> authorities) {
		super(email, name, surname, address, phoneNumber, active, authorities);
		// TODO Auto-generated constructor stub
	}
}
