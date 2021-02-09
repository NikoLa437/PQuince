package quince_it.pquince.entities.users;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class StaffFeedback {
	
	@EmbeddedId
	private StaffFeedbackId staffFedbackId;
	
    @Column(name = "date")
	private Date date;
    
	@Column(name="grade")
	private int grade;
	
	public StaffFeedback() {}
	
	public StaffFeedback(Staff staff, Patient patient, int grade) {
		this(staff, patient, grade, new Date());
	}
	
	public StaffFeedback(Staff staff, Patient patient, int grade, Date date) {
		super();
		this.staffFedbackId = new StaffFeedbackId(staff, patient);
		this.date=date;
		this.grade=grade;
	}


	public Staff getStaff() {
		return staffFedbackId.getStaff();
	}

	public void setStaff(Staff staff) {
		staffFedbackId.setStaff(staff);
	}
	
	public Patient getPatient() {
		return staffFedbackId.getPatient();
	}

	public void setPatient(Patient patient) {
		staffFedbackId.setPatient(patient);
	}

	public Date getDate() {
		return date;
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
}
