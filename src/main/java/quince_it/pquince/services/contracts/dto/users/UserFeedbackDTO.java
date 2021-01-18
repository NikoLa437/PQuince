package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;
import java.util.UUID;

import quince_it.pquince.entities.users.Grade;

public class UserFeedbackDTO {

	private UUID id;
	
	private UUID forStaff;
	
	private Date Date;
    
	private Grade grade;
		
	public UserFeedbackDTO(UUID id, UUID forStaff, Date date, Grade grade) {
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

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
}
