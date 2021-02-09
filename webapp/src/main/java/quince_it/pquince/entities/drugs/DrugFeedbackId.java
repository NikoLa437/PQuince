package quince_it.pquince.entities.drugs;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.users.Patient;

@Embeddable
public class DrugFeedbackId implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private DrugInstance drug;
	
    @ManyToOne(optional = false)
	private Patient patient;
    
    public DrugFeedbackId() {}

	public DrugFeedbackId(DrugInstance drug, Patient patient) {
		super();
		this.drug = drug;
		this.patient = patient;
	}

	public DrugInstance getDrug() {
		return drug;
	}

	public void setDrug(DrugInstance drug) {
		this.drug = drug;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
    
    
    
}
