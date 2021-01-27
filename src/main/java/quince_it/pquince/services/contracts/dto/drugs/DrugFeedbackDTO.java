package quince_it.pquince.services.contracts.dto.drugs;

import java.util.Date;
import java.util.UUID;

public class DrugFeedbackDTO {

	private UUID drugId;
	
	private Date Date;
    
	private int grade;
	
	public DrugFeedbackDTO() {}

	public DrugFeedbackDTO(UUID drugId, java.util.Date date, int grade) {
		super();
		this.drugId = drugId;
		Date = date;
		this.grade = grade;
	}

	public UUID getDrugId() {
		return drugId;
	}

	public void setDrugId(UUID drugId) {
		this.drugId = drugId;
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
