package quince_it.pquince.entities.drugs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import quince_it.pquince.entities.users.Patient;

@Entity
public class DrugFeedback {

	@EmbeddedId
	private DrugFeedbackId drugFeedbackId;
	
	@Column(name = "date")
	private Date date;
    
	@Column(name="grade")
	private int grade;
	
	public DrugFeedback() {}
	
	public DrugFeedback(DrugInstance drugInstance, Patient patient, int grade) {
		this(drugInstance, patient, grade, new Date());
	}

	public DrugFeedback(DrugInstance drugInstance, Patient patient, int grade, Date date) {
		super();
		this.drugFeedbackId = new DrugFeedbackId(drugInstance, patient);
		this.date = date;
		this.grade = grade;
	}

	public DrugFeedbackId getDrugFeedbackId() {
		return drugFeedbackId;
	}

	public DrugInstance getDrug() {
		return drugFeedbackId.getDrug();
	}

	public void setDrug(DrugInstance drug) {
		this.drugFeedbackId.setDrug(drug);
	}

	public Patient getPatient() {
		return drugFeedbackId.getPatient();
	}

	public void setPatient(Patient patient) {
		this.drugFeedbackId.setPatient(patient);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Date getDate() {
		return date;
	}
	
	
}
