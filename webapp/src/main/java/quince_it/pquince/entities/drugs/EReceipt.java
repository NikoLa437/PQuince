package quince_it.pquince.entities.drugs;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

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
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private EReceiptStatus status;

	@Column(name = "price")
	private double price;
	
	@Version
	private Long version;
	
	public EReceipt() {}

	public EReceipt(UUID id, Patient patient, Date creationDate, Pharmacy pharmacy, EReceiptStatus status) {
		super();
		this.id = id;
		this.patient = patient;
		this.creationDate = creationDate;
		this.pharmacy = pharmacy;
		this.status = status;
		this.price = -1;
	}

	public Patient getPatient() {
		return patient;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
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

	public EReceiptStatus getStatus() {
		return status;
	}

	public void setStatus(EReceiptStatus status) {
		this.status = status;
	}
		
}
