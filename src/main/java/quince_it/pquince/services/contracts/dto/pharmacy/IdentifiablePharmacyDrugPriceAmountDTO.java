package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.UUID;

import quince_it.pquince.entities.users.Address;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class IdentifiablePharmacyDrugPriceAmountDTO extends IdentifiableDTO<PharmacyDTO>{
	
	private double price;
	
	private int count;
	
	public IdentifiablePharmacyDrugPriceAmountDTO(UUID id, String name, Address address, String description, double price, int count) {
		super(id, new PharmacyDTO(name, address,description));
		this.price = price;
		this.count = count;
	}

	public IdentifiablePharmacyDrugPriceAmountDTO() {
		super();
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}
	
	
}
