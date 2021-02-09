package quince_it.pquince.services.contracts.dto.appointment;

import java.util.Date;

public class PharmacistConsultationRequestTimeDTO {

	private Date startDateTime;
	
	private Date endDateTime;
	
	public PharmacistConsultationRequestTimeDTO() {}

	public PharmacistConsultationRequestTimeDTO(Date startDateTime, Date endDateTime) {
		super();
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
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
	
}
