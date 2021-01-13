package quince_it.pquince.services.contracts.dto.users;

import java.util.List;

import quince_it.pquince.entities.drugs.Allergen;
import quince_it.pquince.entities.users.Authority;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class PatientDTO extends UserDTO {
	
	private int penalty;
	
	private int points;
	
	private List<Allergen> allergens;

	public PatientDTO(String email, String name, String surname, String address, IdentifiableDTO<CityDTO> city, String phoneNumber, boolean active,
			List<Authority> authorities, int penalty,List<Allergen> allergens, int points) {
		
		super(email, name, surname, address, city, phoneNumber, active, authorities);
		this.allergens = allergens;
		this.penalty = penalty;
		this.points = points;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public List<Allergen> getAllergens() {
		return allergens;
	}

	public void setAllergens(List<Allergen> allergens) {
		this.allergens = allergens;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
