package quince_it.pquince.services.contracts.dto.appointment;

import java.util.Date;
import java.util.UUID;

import quince_it.pquince.entities.appointment.AppointmentStatus;

public class DermatologistCreateAppointmentDTO {
	private UUID patientId;
	private Date startDateTime;
	private Date endDateTime;
	private int price;
	
	public DermatologistCreateAppointmentDTO(UUID patientId, Date startDateTime, Date endDateTime, int price) {
		super();
		this.patientId = patientId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.price = price;
	}
	
	public DermatologistCreateAppointmentDTO() {
		super();
	}
	
	public UUID getPatientId() {
		return patientId;
	}
	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
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
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
