package quince_it.pquince.services.contracts.dto.appointment;

import java.util.UUID;

public class ScheduleAppointmentDTO {
	private UUID patientId;
	private UUID appointmentId;
	public ScheduleAppointmentDTO(UUID patientId, UUID appointmentId) {
		super();
		this.patientId = patientId;
		this.appointmentId = appointmentId;
	}
	public ScheduleAppointmentDTO() {
		super();
	}
	public UUID getPatientId() {
		return patientId;
	}
	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}
	public UUID getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(UUID appointmentId) {
		this.appointmentId = appointmentId;
	}
}
