package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class DrugOrderDTO {
	private UUID drugInstanceId;
	private double amount;
	

	public DrugOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public DrugOrderDTO(UUID drugInstanceId, double amount) {
		super();
		this.drugInstanceId = drugInstanceId;
		this.amount = amount;
	}


	public UUID getDrugInstanceId() {
		return drugInstanceId;
	}
	public void setDrugInstanceId(UUID drugInstanceId) {
		this.drugInstanceId = drugInstanceId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
