package quince_it.pquince.services.contracts.dto.drugs;

import quince_it.pquince.entities.drugs.DrugKind;
import quince_it.pquince.entities.drugs.FormatDrug;

public class DrugEReceiptDTO {

	private String drugInstanceName;

	private FormatDrug drugFormat;
	
	private DrugKind drugKind;

	private int drugAmount;

	public DrugEReceiptDTO() {}
	
	public DrugEReceiptDTO(String drugInstanceName, FormatDrug drugFormat, int drugAmount, DrugKind drugKind) {
		super();
		this.drugInstanceName = drugInstanceName;
		this.drugFormat = drugFormat;
		this.drugAmount = drugAmount;
		this.drugKind = drugKind;
	}

	public String getDrugInstanceName() {
		return drugInstanceName;
	}

	public void setDrugInstanceName(String drugInstanceName) {
		this.drugInstanceName = drugInstanceName;
	}

	public FormatDrug getDrugFormat() {
		return drugFormat;
	}

	public void setDrugFormat(FormatDrug drugFormat) {
		this.drugFormat = drugFormat;
	}

	public int getDrugAmount() {
		return drugAmount;
	}

	public void setDrugAmount(int drugAmount) {
		this.drugAmount = drugAmount;
	}

	public DrugKind getDrugKind() {
		return drugKind;
	}

	public void setDrugKind(DrugKind drugKind) {
		this.drugKind = drugKind;
	}
	
	

}
