package quince_it.pquince.services.contracts.dto.drugs;

import java.util.List;

import quince_it.pquince.entities.drugs.DrugKind;
import quince_it.pquince.entities.drugs.FormatDrug;

public class DrugWithEReceiptsDTO {

	private String drugInstanceName;

	private FormatDrug drugFormat;
	
	private DrugKind drugKind;
	
	private List<IdentifiableEReceiptForDrugDTO> eReceipts;
	
	public DrugWithEReceiptsDTO() {}

	public DrugWithEReceiptsDTO(String drugInstanceName, FormatDrug drugFormat, DrugKind drugKind,
			List<IdentifiableEReceiptForDrugDTO> eReceipts) {
		super();
		this.drugInstanceName = drugInstanceName;
		this.drugFormat = drugFormat;
		this.drugKind = drugKind;
		this.eReceipts = eReceipts;
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

	public DrugKind getDrugKind() {
		return drugKind;
	}

	public void setDrugKind(DrugKind drugKind) {
		this.drugKind = drugKind;
	}

	public List<IdentifiableEReceiptForDrugDTO> geteReceipts() {
		return eReceipts;
	}

	public void seteReceipts(List<IdentifiableEReceiptForDrugDTO> eReceipts) {
		this.eReceipts = eReceipts;
	}

	
}
