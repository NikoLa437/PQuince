package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class EditStorageAmountForDrugDTO {
	private UUID drugInstanceId;
	private int amount;
	
	public EditStorageAmountForDrugDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EditStorageAmountForDrugDTO(UUID drugInstanceId, int amount) {
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	
}
