package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class AllergenUserDTO {
	
	private UUID patientId;
	
	private UUID allergenId;
	
	public AllergenUserDTO() {}
	
	public AllergenUserDTO(UUID patientId, UUID allergenId) {
		super();
		this.patientId = patientId;
		this.allergenId = allergenId;
	}
	
	public UUID getPatientId() {
		return patientId;
	}

	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}

	public UUID getAllergenId() {
		return allergenId;
	}

	public void setAllergenId(UUID allergenId) {
		this.allergenId = allergenId;
	}

	
	
}
