package quince_it.pquince.services.contracts.dto.drugs;

import quince_it.pquince.entities.drugs.FormatDrug;

public class DrugWithPriceDTO {
	private String name;
	
	private String drugInstanceName;
	
	private String manufacturerName;
	
	private FormatDrug drugFormat;
	
	private double quantity;
	
	private boolean onReciept;

	private double avgGrade;

	private double price;

	public DrugWithPriceDTO(String name, String drugInstanceName, String manufacturerName, FormatDrug drugFormat,
			double quantity, boolean onReciept, double avgGrade, double price) {
		super();
		this.name = name;
		this.drugInstanceName = drugInstanceName;
		this.manufacturerName = manufacturerName;
		this.drugFormat = drugFormat;
		this.quantity = quantity;
		this.onReciept = onReciept;
		this.avgGrade = avgGrade;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDrugInstanceName() {
		return drugInstanceName;
	}

	public void setDrugInstanceName(String drugInstanceName) {
		this.drugInstanceName = drugInstanceName;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public FormatDrug getDrugFormat() {
		return drugFormat;
	}

	public void setDrugFormat(FormatDrug drugFormat) {
		this.drugFormat = drugFormat;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public boolean isOnReciept() {
		return onReciept;
	}

	public void setOnReciept(boolean onReciept) {
		this.onReciept = onReciept;
	}

	public double getAvgGrade() {
		return avgGrade;
	}

	public void setAvgGrade(double avgGrade) {
		this.avgGrade = avgGrade;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
