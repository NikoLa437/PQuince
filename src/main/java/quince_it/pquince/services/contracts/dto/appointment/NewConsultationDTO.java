package quince_it.pquince.services.contracts.dto.appointment;

import java.util.Date;
import java.util.UUID;

public class NewConsultationDTO {
	private Date startDateTime;
	private UUID patientId;

	public NewConsultationDTO(Date startDateTime, UUID patientId) {
		super();
		this.startDateTime = startDateTime;
		this.patientId = patientId;
	}
	public NewConsultationDTO() {
		super();
	}
	public Date getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	public UUID getPatientId() {
		return patientId;
	}
	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}
}
