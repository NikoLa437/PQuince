package quince_it.pquince.services.contracts.dto.users;

import java.util.UUID;

public class RemovePharmacistFromPharmacyDTO {
	private UUID pharmacyId;
	private UUID pharmacistId;
	
	public RemovePharmacistFromPharmacyDTO() {
		
	}
	
	public RemovePharmacistFromPharmacyDTO(UUID pharmacyId, UUID pharmacistId) {
		super();
		this.pharmacyId = pharmacyId;
		this.pharmacistId = pharmacistId;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public UUID getPharmacistId() {
		return pharmacistId;
	}

	public void setPharmacistId(UUID pharmacistId) {
		this.pharmacistId = pharmacistId;
	}
}
