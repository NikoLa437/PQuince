package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.UUID;

import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class IdentifiablePharmacyDrugPriceDTO extends IdentifiableDTO<PharmacyDTO>{
	
	private double price;

	public IdentifiablePharmacyDrugPriceDTO(UUID id, String name, String address, String description, double price) {
		super(id, new PharmacyDTO(name, address,description));
		this.price = price;
	}


	public IdentifiablePharmacyDrugPriceDTO() {
		super();
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
