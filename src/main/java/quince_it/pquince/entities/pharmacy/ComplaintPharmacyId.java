package quince_it.pquince.entities.pharmacy;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.users.Patient;

@Embeddable
public class ComplaintPharmacyId implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private Pharmacy pharmacy;
	
	@ManyToOne(optional = false)
	private Patient patient;
	
	
    public ComplaintPharmacyId() {}
	
	public ComplaintPharmacyId(Pharmacy pharmacy, Patient patient) {
		super();
		this.pharmacy = pharmacy;
		this.patient = patient;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	
}
