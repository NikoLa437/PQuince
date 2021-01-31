package quince_it.pquince.entities.users;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateRange {
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	
	public DateRange() {
		
	}

	public DateRange(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		super();
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	
	public DateRange(Date startDateTime, Date endDateTime) {
		super();
		this.startDateTime = convertToLocalDateTimeViaInstant(startDateTime);
		this.endDateTime = convertToLocalDateTimeViaInstant(endDateTime);
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
}
