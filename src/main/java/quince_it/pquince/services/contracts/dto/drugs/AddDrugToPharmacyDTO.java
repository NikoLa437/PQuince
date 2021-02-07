package quince_it.pquince.services.contracts.dto.drugs;

import java.util.Date;
import java.util.UUID;

public class AddDrugToPharmacyDTO {
	private UUID drugId;
		
	private Date dateTo;
	
	private int amount;
	
	private double price;
	
	public AddDrugToPharmacyDTO() {
		
	}

	public AddDrugToPharmacyDTO(UUID drugId, Date dateTo, int amount, double price) {
		super();
		this.drugId = drugId;
		this.dateTo = dateTo;
		this.amount = amount;
		this.price = price;
	}

	public UUID getDrugId() {
		return drugId;
	}

	public void setDrugId(UUID drugId) {
		this.drugId = drugId;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	

}
