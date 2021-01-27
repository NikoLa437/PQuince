package quince_it.pquince.entities.pharmacy;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import quince_it.pquince.entities.users.Patient;

@Entity
public class ComplaintPharmacy {
	
	@EmbeddedId
	private ComplaintPharmacyId complaintPharmacyId;
	
    @Column(name = "date")
	private Date date;
    
	@Column(name="text")
	private String text;
	
	public ComplaintPharmacy() {}
	
	public ComplaintPharmacy(Pharmacy pharmacy, Patient patient, String text) {
		this(pharmacy, patient, text, new Date());
	}
	
	public ComplaintPharmacy(Pharmacy pharmacy, Patient patient,String text, Date date) {
		super();
		this.complaintPharmacyId = new ComplaintPharmacyId(pharmacy, patient);
		this.date=date;
		this.text=text;
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
}
