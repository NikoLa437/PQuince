package quince_it.pquince.entities.drugs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.pharmacy.Pharmacy;

@Entity
@IdClass(DrugStorageId.class)
public class DrugStorage implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	private DrugInstance drugInstance;
	
	@Id
	@ManyToOne
	private Pharmacy pharmacy;
	
	@Column(name = "count", nullable = false)
	private int count;

	public DrugStorage() {}
	
	public DrugStorage(DrugInstance drugInstance, Pharmacy pharmacy, int count) {
		super();
		this.drugInstance = drugInstance;
		this.pharmacy = pharmacy;
		this.count = count;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	

}
