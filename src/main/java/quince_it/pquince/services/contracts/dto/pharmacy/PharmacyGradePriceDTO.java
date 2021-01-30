package quince_it.pquince.services.contracts.dto.pharmacy;

import quince_it.pquince.entities.users.Address;

public class PharmacyGradePriceDTO extends PharmacyGradeDTO {

	private double price;

	public PharmacyGradePriceDTO() {
		super();
	}
	
	public PharmacyGradePriceDTO(String name, Address address, String description, double grade, double price) {
		super(name, address, description, grade);
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
}
