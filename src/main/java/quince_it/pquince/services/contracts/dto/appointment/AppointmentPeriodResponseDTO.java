package quince_it.pquince.services.contracts.dto.appointment;

import java.time.LocalDateTime;
import java.util.Date;

public class AppointmentPeriodResponseDTO {
	private Date startDate;
	private Date endDate;
	
	public AppointmentPeriodResponseDTO() {}

	public AppointmentPeriodResponseDTO(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
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
}

