package quince_it.pquince.services.contracts.dto.drugs;

import java.util.Date;

import quince_it.pquince.entities.drugs.EReceiptStatus;

public class EReceiptForDrugDTO {

	private EReceiptStatus status;
	
	private Date creationDate;
	
	private String pharmacyName;
	
	private int drugAmount;

	public EReceiptForDrugDTO() {}

	public EReceiptForDrugDTO(EReceiptStatus status, Date creationDate, String pharmacyName, int drugAmount) {
		super();
		this.status = status;
		this.creationDate = creationDate;
		this.pharmacyName = pharmacyName;
		this.drugAmount = drugAmount;
	}

	public EReceiptStatus getStatus() {
		return status;
	}

	public void setStatus(EReceiptStatus status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public int getDrugAmount() {
		return drugAmount;
	}

	public void setDrugAmount(int drugAmount) {
		this.drugAmount = drugAmount;
	}
	
	
}
