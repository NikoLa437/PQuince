package quince_it.pquince.services.contracts.dto.appointment;

import java.util.Date;
import java.util.UUID;


public class AppointmentRequestDTO {
	
	private UUID dermatologistId;

	private Date date;
    
    private int duration;
    
    private boolean forAllDay;
        
    public AppointmentRequestDTO() {}

	public AppointmentRequestDTO(UUID dermatologistId, Date date, int duration, boolean forAllDay) {
		super();
		this.dermatologistId = dermatologistId;
		this.date = date;
		this.duration = duration;
		this.forAllDay=forAllDay;
	}

	public UUID getDermatologistId() {
		return dermatologistId;
	}

	public void setDermatologistId(UUID dermatologistId) {
		this.dermatologistId = dermatologistId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isForAllDay() {
		return forAllDay;
	}

	public void setForAllDay(boolean forAllDay) {
		this.forAllDay = forAllDay;
	}
}
