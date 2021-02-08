package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;
import java.util.UUID;

public class ComplaintPharmacyDTO {

	private UUID pharmacyId;

	private String name;
	
	private Date Date;
    
	private String text;
	
	private String reply;
	
	public ComplaintPharmacyDTO() {}
		
	public ComplaintPharmacyDTO(UUID pharmacyId, Date date, String text, String name, String reply) {
		this.pharmacyId= pharmacyId;
		this.Date=date;
		this.text=text;
		this.name = name;
		this.reply = reply;
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
	
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
