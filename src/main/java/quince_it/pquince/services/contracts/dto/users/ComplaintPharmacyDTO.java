package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;
import java.util.UUID;

public class ComplaintPharmacyDTO {

	private UUID pharmacyId;
	
	private Date Date;
    
	private String text;
	
	public ComplaintPharmacyDTO() {}
		
	public ComplaintPharmacyDTO(UUID pharmacyId, Date date, String text) {
		this.pharmacyId= pharmacyId;
		this.Date=date;
		this.text=text;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
