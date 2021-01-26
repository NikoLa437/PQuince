package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;
import java.util.UUID;

public class StaffFeedbackDTO {

	private UUID id;
	
	private UUID forStaff;
	
	private Date Date;
    
	private int grade;
		
	public StaffFeedbackDTO(UUID id, UUID forStaff, Date date, int grade) {
		this.id = id;
		this.forStaff= forStaff;
		this.Date=date;
		this.grade=grade;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getForStaff() {
		return forStaff;
	}

	public void setForStaff(UUID forStaff) {
		this.forStaff = forStaff;
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
