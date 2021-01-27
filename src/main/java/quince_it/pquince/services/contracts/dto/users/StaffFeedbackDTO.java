package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;
import java.util.UUID;

public class StaffFeedbackDTO {
	
	private UUID staffId;
	
	private Date Date;
    
	private int grade;
	
	public StaffFeedbackDTO() {}
		
	public StaffFeedbackDTO(UUID staffId, Date date, int grade) {
		this.staffId= staffId;
		this.Date=date;
		this.grade=grade;
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

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
