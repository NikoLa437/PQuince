package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.UUID;

import quince_it.pquince.entities.users.Address;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class IdentifiablePharmacyDrugPriceAmountDTO extends IdentifiableDTO<PharmacyDTO>{
	
	private double price;
	
	private double priceWithDiscount;
	
	private int count;
	
	
	public IdentifiablePharmacyDrugPriceAmountDTO(UUID id, String name, Address address, String description, double consultationPrice, double price, int count, double priceWithDiscount) {
		super(id, new PharmacyDTO(name, address,description, consultationPrice));
		this.price = price;
		this.count = count;
		this.priceWithDiscount = priceWithDiscount;
	}
	
	// ovde mora consulting price da se doda negde na frontu, pa obrisati posle ovaj konstruktor
	public IdentifiablePharmacyDrugPriceAmountDTO(UUID id, String name, Address address, String description, double price, int count, double priceWithDiscount) {
		super(id, new PharmacyDTO(name, address,description, 0));
		this.price = price;
		this.count = count;
		this.priceWithDiscount = priceWithDiscount;
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

	public double getPriceWithDiscount() {
		return priceWithDiscount;
	}

	public void setPriceWithDiscount(double priceWithDiscount) {
		this.priceWithDiscount = priceWithDiscount;
	}
	
	
}
