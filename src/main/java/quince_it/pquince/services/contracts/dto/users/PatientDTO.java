package quince_it.pquince.services.contracts.dto.users;

import java.util.List;

import quince_it.pquince.entities.users.Authority;

public class PatientDTO extends UserDTO {
	
	private int penalty;

	public PatientDTO(String email, String name, String surname, String address, CityDTO city, String phoneNumber, boolean active,
			List<Authority> authorities, int penalty) {
		
		super(email, name, surname, address, city, phoneNumber, active, authorities);
		this.penalty = penalty;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}
}
