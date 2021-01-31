package quince_it.pquince.entities.users;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ComplaintStaff {

	@Id
	private UUID id;
	
	@Column
	private StaffFeedbackId staffComplaintId;
	
    @Column(name = "date")
	private Date date;

	@Column(name="text")
	private String text;

	@Column(name="reply")
	private String reply;
	
	public ComplaintStaff() {}
	
	public ComplaintStaff(Staff staff, Patient patient, String text) {
		this(UUID.randomUUID(),staff, patient, text, new Date(), "");
	}
	
	public ComplaintStaff(UUID id,Staff staff, Patient patient, String text, Date date, String reply) {
		super();
		this.id = id;
		this.staffComplaintId = new StaffFeedbackId(staff, patient);
		this.date=date;
		this.text=text;
		this.reply=reply;
	}

	public UUID getId() {
		return id;
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
	
	public String getReply() {
		return text;
	}

	public void setReply(String text) {
		this.text = text;
	}
}
