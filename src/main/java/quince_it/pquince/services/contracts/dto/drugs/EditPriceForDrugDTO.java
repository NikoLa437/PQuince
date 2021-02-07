package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class EditPriceForDrugDTO {
	private UUID drugInstanceId;
	private double price;
	
	public EditPriceForDrugDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EditPriceForDrugDTO(UUID drugInstanceId, double price) {
		super();
		this.drugInstanceId = drugInstanceId;
		this.price = price;
	}
	public UUID getDrugInstanceId() {
		return drugInstanceId;
	}
	public void setDrugInstanceId(UUID drugInstanceId) {
		this.drugInstanceId = drugInstanceId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
