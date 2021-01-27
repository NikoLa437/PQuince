package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.Date;
import java.util.UUID;

public class PharmacyFeedbackDTO {

	private UUID pharmacyId;
	
	private Date Date;
    
	private int grade;
	
	public PharmacyFeedbackDTO() {}

	public PharmacyFeedbackDTO(UUID pharmacyId, java.util.Date date, int grade) {
		super();
		this.pharmacyId = pharmacyId;
		Date = date;
		this.grade = grade;
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

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	
}
