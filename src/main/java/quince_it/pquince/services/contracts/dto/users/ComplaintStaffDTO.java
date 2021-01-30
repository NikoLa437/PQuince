package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;
import java.util.UUID;

public class ComplaintStaffDTO {
	
	private UUID staffId;
	
	private Date Date;
    
	private String text;
	
	public ComplaintStaffDTO() {}
		
	public ComplaintStaffDTO(UUID staffId, Date date, String text) {
		this.staffId= staffId;
		this.Date=date;
		this.text=text;
	}

	public UUID getStaffId() {
		return staffId;
	}

	public void setStaffId(UUID staffId) {
		this.staffId = staffId;
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
