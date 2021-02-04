package quince_it.pquince.entities.drugs;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Patient;

@Entity
public class EReceipt {

	@Id
	private UUID id;
	
	@ManyToOne
	private Patient patient;
	
	@Column(name = "creationDate")
	private Date creationDate;

	@ManyToOne(optional = true)
	private Pharmacy pharmacy;
	
	public EReceipt() {}

	public EReceipt(UUID id, Patient patient, Date creationDate, Pharmacy pharmacy) {
		super();
		this.id = id;
		this.patient = patient;
		this.creationDate = creationDate;
		this.pharmacy = pharmacy;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public UUID getId() {
		return id;
	}

	public Date getCreationDate() {
		return creationDate;
	}
		
}
