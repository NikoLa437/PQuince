package quince_it.pquince.services.contracts.dto.users;

import java.util.UUID;

public class DrugManufacturerDTO {
	
	private UUID drug_id;
	
	private UUID manufacturer_id;

	public UUID getDrug_id() {
		return drug_id;
	}

	public void setDrug_id(UUID drug_id) {
		this.drug_id = drug_id;
	}

	public UUID getManufacturer_id() {
		return manufacturer_id;
	}

	public void setManufacturer_id(UUID manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}
	
}
