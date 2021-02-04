package quince_it.pquince.services.contracts.dto.appointment;

import java.util.Date;
import java.util.UUID;

public class ConsultationRequestDTO {

	private UUID pharmacistId;
	
	private Date startDateTime;
	
	public ConsultationRequestDTO() {}

	public ConsultationRequestDTO(UUID pharmacistId, Date startDateTime) {
		super();
		this.pharmacistId = pharmacistId;
		this.startDateTime = startDateTime;
	}

	public UUID getPharmacistId() {
		return pharmacistId;
	}

	public void setPharmacistId(UUID pharmacistId) {
		this.pharmacistId = pharmacistId;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
		
}
