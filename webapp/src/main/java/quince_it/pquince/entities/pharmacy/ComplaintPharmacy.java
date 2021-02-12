package quince_it.pquince.entities.pharmacy;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import quince_it.pquince.entities.users.Patient;

@Entity
public class ComplaintPharmacy {
	
	@Id
	private UUID id;
	
	@Column
	private ComplaintPharmacyId complaintPharmacyId;
	
    @Column(name = "date")
	private Date date;
    
	@Column(name="text")
	private String text;

	@Column(name="name")
	private String name;
	
	@Column(name="reply")
	private String reply;
	
	@Version
	private Long version;
	
	public ComplaintPharmacy() {}
	
	public ComplaintPharmacy(Pharmacy pharmacy, Patient patient, String text, String name) {
		this(UUID.randomUUID(), pharmacy, "", patient, text, new Date(), name);
	}
	
	public ComplaintPharmacy(UUID id,Pharmacy pharmacy, String reply, Patient patient,String text, Date date, String name) {
		super();
		this.id = id;
		this.complaintPharmacyId = new ComplaintPharmacyId(pharmacy, patient);
		this.date=date;
		this.text=text;
		this.reply = reply;
		this.name = name;
	}


	public Long getVersion() {
		return version;
	}
	
	public UUID getId() {
		return id;
	}

	public Pharmacy getPharmacy() {
		return complaintPharmacyId.getPharmacy();
	}

	public void setPharmacy(Pharmacy pharmacy) {
		complaintPharmacyId.setPharmacy(pharmacy);
	}
	
	public Patient getPatient() {
		return complaintPharmacyId.getPatient();
	}

	public void setPatient(Patient patient) {
		complaintPharmacyId.setPatient(patient);
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

	public ComplaintPharmacyId getComplaintPharmacyId() {
		return complaintPharmacyId;
	}

	public void setComplaintPharmacyId(ComplaintPharmacyId complaintPharmacyId) {
		this.complaintPharmacyId = complaintPharmacyId;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
	
	public String getReply() {
		return reply;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
