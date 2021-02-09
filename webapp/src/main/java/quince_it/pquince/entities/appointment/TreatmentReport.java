package quince_it.pquince.entities.appointment;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TreatmentReport {
	@Id
	@Column(name = "id")
	private UUID id;
	@Column(name = "anamnesis")
	private String anamnesis;
	@Column(name = "diagnosis")
	private String diagnosis;
	@Column(name = "therapy")
	private String therapy;
	@OneToOne
	private Appointment appointment;
	
	public TreatmentReport(UUID id, String anamnesis, String diagnosis, String therapy, Appointment appointment) {
		super();
		this.id = id;
		this.anamnesis = anamnesis;
		this.diagnosis = diagnosis;
		this.therapy = therapy;
		this.appointment = appointment;
	}
	
	public TreatmentReport(String anamnesis, String diagnosis, String therapy, Appointment appointment) {
		this(UUID.randomUUID(), anamnesis, diagnosis, therapy, appointment);
	}
	
	public TreatmentReport() {
		super();
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
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

}
