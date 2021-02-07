package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class RemoveDrugFromPharmacyDTO {
	private UUID drugId;
	private UUID pharmacyId;
	
	public RemoveDrugFromPharmacyDTO() {}
	
	public RemoveDrugFromPharmacyDTO(UUID drugId, UUID pharmacyId) {
		super();
		this.drugId = drugId;
		this.pharmacyId = pharmacyId;
	}
	public UUID getDrugId() {
		return drugId;
	}
	public void setDrugId(UUID drugId) {
		this.drugId = drugId;
	}
	public UUID getPharmacyId() {
		return pharmacyId;
	}
	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	
	
}
