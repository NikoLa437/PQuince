package quince_it.pquince.services.contracts.dto.appointment;

import java.util.Date;

import quince_it.pquince.entities.appointment.AppointmentStatus;
import quince_it.pquince.services.contracts.dto.users.StaffDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class AppointmentDTO {
	
	private IdentifiableDTO<StaffDTO> staff;

	private Date startDateTime;
    
    private Date endDateTime;
    
    private double price;
    
    private AppointmentStatus appointmentStatus;
    
    public AppointmentDTO() {}

	public AppointmentDTO(IdentifiableDTO<StaffDTO> staff, AppointmentStatus appointmentStatus, Date startDateTime, Date endDateTime, double price) {
		super();
		this.staff = staff;
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

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
    public IdentifiableDTO<StaffDTO> getStaff() {
		return staff;
	}

	public void setStaff(IdentifiableDTO<StaffDTO> staff) {
		this.staff = staff;
	}
	
	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
}
