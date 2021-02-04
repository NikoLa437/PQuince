package quince_it.pquince.entities.appointment;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;

@Entity
public class Appointment {

	@Id
    @Column(name = "id")
	private UUID id;
	
	@ManyToOne
	private Pharmacy pharmacy;
	
	@ManyToOne
	private Staff staff;
	
	@ManyToOne(optional = true)
	private Patient patient;
	
    @Column(name = "startDateTime")
    private Date startDateTime;
    
    @Column(name = "endDateTime")
    private Date endDateTime;
    
    @Column(name = "price")
    private double price;
    
    @Column(name = "priceToPay")
    private double priceToPay;
    
    @Enumerated(EnumType.STRING)
	@Column(name = "appointmentType", nullable = false)
	private AppointmentType appointmentType;
    
    @Enumerated(EnumType.STRING)
	@Column(name = "appointmentStatus", nullable = false)
	private AppointmentStatus appointmentStatus;
    
    public Appointment() {}

    public Appointment(Pharmacy pharmacy, Staff staff, Patient patient, Date startDateTime, Date endDateTime,
			double price, AppointmentType appointmentType, AppointmentStatus appointmentStatus) {
    	this(UUID.randomUUID(), pharmacy, staff, patient, startDateTime, endDateTime, price, appointmentType, appointmentStatus);
	}
    
	public Appointment(UUID id, Pharmacy pharmacy, Staff staff, Patient patient, Date startDateTime, Date endDateTime,
			double price, AppointmentType appointmentType, AppointmentStatus appointmentStatus) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		this.staff = staff;
		this.patient = patient;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.price = price;
		this.appointmentType = appointmentType;
		this.appointmentStatus = appointmentStatus;
		this.priceToPay = price;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

	public UUID getId() {
		return id;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public Staff getStaff() {
		return staff;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public double getPriceToPay() {
		return priceToPay;
	}

	public void setPriceToPay(double priceToPay) {
		this.priceToPay = priceToPay;
	}
    
    
	
}
