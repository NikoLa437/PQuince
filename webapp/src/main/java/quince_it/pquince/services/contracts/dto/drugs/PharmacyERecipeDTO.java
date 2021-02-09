package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class PharmacyERecipeDTO {
	
	private UUID pharmacyId;
	
	private UUID eRecipeId;
	
	private double price;

	public PharmacyERecipeDTO() {}
	
	public PharmacyERecipeDTO(UUID pharmacyId, UUID eRecipeId, double price) {
		super();
		this.pharmacyId = pharmacyId;
		this.eRecipeId = eRecipeId;
		this.price = price;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public UUID geteRecipeId() {
		return eRecipeId;
	}

	public void seteRecipeId(UUID eRecipeId) {
		this.eRecipeId = eRecipeId;
	}
	
}
