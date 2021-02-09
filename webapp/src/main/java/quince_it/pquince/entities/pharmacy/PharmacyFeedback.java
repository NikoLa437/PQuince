package quince_it.pquince.entities.pharmacy;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import quince_it.pquince.entities.users.Patient;

@Entity
public class PharmacyFeedback implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PharmacyFeedbackId pharmacyId;
	
	@Column(name = "grade")
	private int grade;
	
	@Column(name = "date")
	private Date date;
	
	public PharmacyFeedback() {}
	
	public PharmacyFeedback(Pharmacy pharmacy, int grade, Patient patient, Date date) {
		super();
		this.pharmacyId = new PharmacyFeedbackId(pharmacy, patient);
		this.grade = grade;
		this.date = date;
	}

	public PharmacyFeedback(Pharmacy pharmacy, int grade, Patient patient) {
		this(pharmacy, grade, patient, new Date());
	}
	

	public void setGrade(int grade) {
		this.grade = grade;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Patient getPatient() {
		return pharmacyId.getPatient();
	}

	public Pharmacy getPharmacy() {
		return pharmacyId.getPharmacy();
	}

	public int getGrade() {
		return grade;
	}
        
}
