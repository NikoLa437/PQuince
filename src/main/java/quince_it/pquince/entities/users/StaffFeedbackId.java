package quince_it.pquince.entities.users;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class StaffFeedbackId implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private Staff staff;
	
	@ManyToOne(optional = false)
	private Patient patient;
	
	
    public StaffFeedbackId() {}
	
	public StaffFeedbackId(Staff staff, Patient patient) {
		super();
		this.staff = staff;
		this.patient = patient;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	
	
}
