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
public class DrugReservation {
	
	@Version
	private Long version;
	
	@Id
	private UUID id;
    
    @ManyToOne
    private Pharmacy pharmacy;

	@ManyToOne
    private DrugInstance drugInstance;
    
    @ManyToOne
    private Patient patient;
    
	@Column(name = "amount", nullable = false)
    private int amount;
	
	@Column(name = "startDate", nullable = false)
    private Date startDate;
	
	@Column(name = "endDate", nullable = false)
    private Date endDate;
	
	@Column(name = "drugPeacePrice", nullable = false)
	private double drugPeacePrice;

	@Enumerated(EnumType.STRING)
	@Column(name = "reservationStatus", nullable = false)
	private ReservationStatus reservationStatus;
	
	public DrugReservation() {}
	
	public DrugReservation(UUID id, Pharmacy pharmacy, DrugInstance drugInstance,
			Patient patient, int amount, Date startDate, Date endDate,double drugPeacePrice,ReservationStatus reservationStatus) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		this.drugInstance = drugInstance;
		this.patient = patient;
		this.amount = amount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.drugPeacePrice = drugPeacePrice;
		this.reservationStatus = reservationStatus;
	}
	
	public DrugReservation(Pharmacy pharmacy, DrugInstance drugInstance,
			Patient patient, int amount, Date endDate,double drugPeacePrice) {
		super();
		this.id = UUID.randomUUID();
		this.pharmacy = pharmacy;
		this.drugInstance = drugInstance;
		this.patient = patient;
		this.amount = amount;
		this.startDate = new Date();
		this.endDate = endDate;
		this.drugPeacePrice = drugPeacePrice;
		this.reservationStatus = ReservationStatus.ACTIVE;
	}

	public UUID getId() {
		return id;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public DrugInstance getDrugInstance() {
		return drugInstance;
	}

	public void setDrugInstance(DrugInstance drugInstance) {
		this.drugInstance = drugInstance;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getDrugPeacePrice() {
		return drugPeacePrice;
	}

	public void setDrugPeacePrice(double drugPeacePrice) {
		this.drugPeacePrice = drugPeacePrice;
	}

	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
    public Long getVersion() {
		return version;
	}
	
}
