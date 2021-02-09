package quince_it.pquince.entities.drugs;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.pharmacy.Pharmacy;

@Embeddable
public class SupplierDrugStorageId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private DrugInstance drugInstance;

	public SupplierDrugStorageId() {}
	
	public SupplierDrugStorageId(DrugInstance drugInstance) {
		super();
		this.drugInstance = drugInstance;	
	}

	public DrugInstance getDrugInstance() {
		return drugInstance;
	}

	public void setDrugInstance(DrugInstance drugInstance) {
		this.drugInstance = drugInstance;
	}

}
