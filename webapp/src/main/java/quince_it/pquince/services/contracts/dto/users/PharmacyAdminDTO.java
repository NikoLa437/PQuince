package quince_it.pquince.services.contracts.dto.users;

import java.util.List;

import quince_it.pquince.entities.users.Address;
import quince_it.pquince.entities.users.Authority;

public class PharmacyAdminDTO extends StaffDTO{

	public PharmacyAdminDTO(String email, String name, String surname, Address address, String phoneNumber,
			boolean active, List<Authority> userAuthorities) {
		super(email, name, surname, address, phoneNumber, active, userAuthorities);
	}

}
