package quince_it.pquince.services.contracts.dto.appointment;

import java.util.Date;

import quince_it.pquince.services.contracts.dto.users.IdentifiableStaffGradeDTO;

public class DermatologistAppointmentDTO {

	private IdentifiableStaffGradeDTO staff;
	
    private Date startDateTime;
    
    private Date endDateTime;
    
    private double price;
    
    public DermatologistAppointmentDTO() {}

	public DermatologistAppointmentDTO(IdentifiableStaffGradeDTO staff, Date startDateTime, Date endDateTime, double price) {
		super();
		this.staff = staff;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.price = price;
	}

	public IdentifiableStaffGradeDTO getStaff() {
		return staff;
	}

	public void setStaff(IdentifiableStaffGradeDTO staff) {
		this.staff = staff;
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
}
