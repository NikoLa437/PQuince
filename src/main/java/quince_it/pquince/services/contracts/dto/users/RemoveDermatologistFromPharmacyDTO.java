package quince_it.pquince.services.contracts.dto.users;

import java.util.UUID;

public class RemoveDermatologistFromPharmacyDTO {
	private UUID pharmacyId;
	private UUID dermatologistId;
	
	public RemoveDermatologistFromPharmacyDTO() {
		
	}
	
	public RemoveDermatologistFromPharmacyDTO(UUID pharmacyId, UUID dermatologistId) {
		super();
		this.pharmacyId = pharmacyId;
		this.dermatologistId = dermatologistId;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public UUID getDermatologistId() {
		return dermatologistId;
	}

	public void setDermatologistId(UUID dermatologistId) {
		this.dermatologistId = dermatologistId;
	}
	
	
	
}
