package quince_it.pquince.entities.drugs;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.pharmacy.Pharmacy;

@Embeddable
public class DrugStorageId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private DrugInstance drugInstance;
	
	@ManyToOne(optional = false)
	private Pharmacy pharmacy;

	public DrugStorageId() {}
	
	public DrugStorageId(DrugInstance drugInstance, Pharmacy pharmacy) {
		super();
		this.drugInstance = drugInstance;
		this.pharmacy = pharmacy;
	}

	public DrugInstance getDrugInstance() {
		return drugInstance;
	}

	public void setDrugInstance(DrugInstance drugInstance) {
		this.drugInstance = drugInstance;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
}
