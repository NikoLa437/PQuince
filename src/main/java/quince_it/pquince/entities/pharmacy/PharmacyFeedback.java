package quince_it.pquince.entities.pharmacy;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.users.Patient;

@Entity
@IdClass(PharmacyFeedbackId.class)
public class PharmacyFeedback implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	private Pharmacy pharmacy;
	
	@Id
    @ManyToOne
	private Patient patient;
	
	@Column(name = "grade")
	private int grade;
	
	@Column(name = "date")
	private Date date;
	
	public PharmacyFeedback() {}
	
	public PharmacyFeedback(Pharmacy pharmacy, int grade, Patient patient, Date date) {
		super();
		this.pharmacy = pharmacy;
		this.grade = grade;
		this.date = date;
		this.patient = patient;
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
		return patient;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public int getGrade() {
		return grade;
	}
        
}
