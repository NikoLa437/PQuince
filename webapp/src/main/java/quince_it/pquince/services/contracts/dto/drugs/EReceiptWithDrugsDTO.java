package quince_it.pquince.services.contracts.dto.drugs;

import java.util.Date;
import java.util.List;

import quince_it.pquince.entities.drugs.EReceiptStatus;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class EReceiptWithDrugsDTO {

	private EReceiptStatus status;
	
	private Date creationDate;
	
	private double price;

	private List<IdentifiableDTO<DrugEReceiptDTO>> drugs;

	public EReceiptWithDrugsDTO() {}
	
	public EReceiptWithDrugsDTO(EReceiptStatus status, Date creationDate, List<IdentifiableDTO<DrugEReceiptDTO>> drugs, double price) {
		super();
		this.status = status;
		this.creationDate = creationDate;
		this.drugs = drugs;
		this.price = price;
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

	public List<IdentifiableDTO<DrugEReceiptDTO>> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<IdentifiableDTO<DrugEReceiptDTO>> drugs) {
		this.drugs = drugs;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}	
}
