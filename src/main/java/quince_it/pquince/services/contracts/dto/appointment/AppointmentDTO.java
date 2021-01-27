package quince_it.pquince.services.contracts.dto.appointment;

import java.util.Date;

public class AppointmentDTO {
	
    private Date startDateTime;
    
    private Date endDateTime;
    
    private double price;
    
    public AppointmentDTO() {}

	public AppointmentDTO(Date startDateTime, Date endDateTime, double price) {
		super();
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
}
