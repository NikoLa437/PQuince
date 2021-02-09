package quince_it.pquince.services.contracts.dto.users;

import java.util.List;

import quince_it.pquince.entities.users.Address;
import quince_it.pquince.entities.users.Authority;
import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class PatientDTO extends UserDTO {
	
	private int penalty;
	
	private int points;
	
	private List<IdentifiableDTO<AllergenDTO>> allergens;
	
	private PatientLoyalityProgramDTO loyalityProgramDTO;

	public PatientDTO(String email, String name, String surname, Address address, String phoneNumber, boolean active,
			List<Authority> authorities, int penalty,List<IdentifiableDTO<AllergenDTO>> allergens, int points, PatientLoyalityProgramDTO loyalityProgramDTO) {
		
		super(email, name, surname, address, phoneNumber, active, authorities);
		this.allergens = allergens;
		this.penalty = penalty;
		this.points = points;
		this.loyalityProgramDTO = loyalityProgramDTO;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public List<IdentifiableDTO<AllergenDTO>> getAllergens() {
		return allergens;
	}

	public void setAllergens(List<IdentifiableDTO<AllergenDTO>> allergens) {
		this.allergens = allergens;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public PatientLoyalityProgramDTO getLoyalityProgramDTO() {
		return loyalityProgramDTO;
	}

	public void setLoyalityProgramDTO(PatientLoyalityProgramDTO loyalityProgramDTO) {
		this.loyalityProgramDTO = loyalityProgramDTO;
	}

}
