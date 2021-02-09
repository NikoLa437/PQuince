package quince_it.pquince.services.contracts.dto.appointment;

import java.util.UUID;

public class TreatmentReportDTO {
	private String anamnesis;
	private String diagnosis;
	private String therapy;
	private UUID appointmentId;
	
	public TreatmentReportDTO(String anamnesis, String diagnosis, String therapy, UUID appointmentId) {
		super();
		this.anamnesis = anamnesis;
		this.diagnosis = diagnosis;
		this.therapy = therapy;
		this.appointmentId = appointmentId;
	}
	public TreatmentReportDTO() {
		super();
	}
	
	public String getAnamnesis() {
		return anamnesis;
	}
	public void setAnamnesis(String anamnesis) {
		this.anamnesis = anamnesis;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getTherapy() {
		return therapy;
	}
	public void setTherapy(String therapy) {
		this.therapy = therapy;
	}
	public UUID getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(UUID appointmentId) {
		this.appointmentId = appointmentId;
	}
}
