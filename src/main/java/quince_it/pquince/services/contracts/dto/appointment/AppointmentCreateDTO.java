package quince_it.pquince.services.contracts.dto.appointment;

import java.util.Date;
import java.util.UUID;

import quince_it.pquince.entities.appointment.AppointmentStatus;

public class AppointmentCreateDTO {
	private UUID staff;
	
	private UUID pharmacy;
	
	private UUID patient;

	private Date startDateTime;
    
    private Date endDateTime;
    
    private double price;
    
    private AppointmentStatus appointmentStatus;
    
    public AppointmentCreateDTO() {}

	public AppointmentCreateDTO(UUID staff,UUID pharmacy, UUID patient,AppointmentStatus appointmentStatus, Date startDateTime, Date endDateTime, double price) {
		super();
		this.staff = staff;
		this.pharmacy=pharmacy;
		this.patient=patient;
		this.appointmentStatus = appointmentStatus;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.price = price;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}
	
	

	public UUID getPatient() {
		return patient;
	}

	public void setPatient(UUID patient) {
		this.patient = patient;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
    public UUID getStaff() {
		return staff;
	}

	public void setStaff(UUID staff) {
		this.staff = staff;
	}
	
	public UUID getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(UUID pharmacy) {
		this.pharmacy = pharmacy;
	}

	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
}
