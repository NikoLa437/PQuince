package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class DrugForOrderDTO {
	private UUID drugInstanceId;
	private int amount;
	private String drugName;
	private String drugInstanceName;
	private String drugManufacturer;

	public DrugForOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrugForOrderDTO(UUID drugInstanceId, int amount, String drugName, String drugInstanceName,
			String drugManufacturer) {
		super();
		this.drugInstanceId = drugInstanceId;
		this.amount = amount;
		this.drugName = drugName;
		this.drugInstanceName = drugInstanceName;
		this.drugManufacturer = drugManufacturer;
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

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getDrugInstanceName() {
		return drugInstanceName;
	}

	public void setDrugInstanceName(String drugInstanceName) {
		this.drugInstanceName = drugInstanceName;
	}

	public String getDrugManufacturer() {
		return drugManufacturer;
	}

	public void setDrugManufacturer(String drugManufacturer) {
		this.drugManufacturer = drugManufacturer;
	}
	
}
