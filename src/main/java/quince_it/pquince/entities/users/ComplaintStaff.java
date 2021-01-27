package quince_it.pquince.entities.users;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ComplaintStaff {
	
	@EmbeddedId
	private StaffFeedbackId staffComplaintId;
	
    @Column(name = "date")
	private Date date;

	@Column(name="text")
	private String text;
	
	public ComplaintStaff() {}
	
	public ComplaintStaff(Staff staff, Patient patient, String text) {
		this(staff, patient, text, new Date());
	}
	
	public ComplaintStaff(Staff staff, Patient patient, String text, Date date) {
		super();
		this.staffComplaintId = new StaffFeedbackId(staff, patient);
		this.date=date;
		this.text=text;
	}


	public Staff getStaff() {
		return staffComplaintId.getStaff();
	}

	public void setStaff(Staff staff) {
		staffComplaintId.setStaff(staff);
	}
	
	public Patient getPatient() {
		return staffComplaintId.getPatient();
	}

	public void setPatient(Patient patient) {
		staffComplaintId.setPatient(patient);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
