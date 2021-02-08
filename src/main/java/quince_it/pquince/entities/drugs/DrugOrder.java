package quince_it.pquince.entities.drugs;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DrugOrder {
	@Id
	private UUID id;
	
	@ManyToOne
	private DrugInstance drugInstance;
	
	@Column(name = "amount", nullable = false)
	double amount;

	public DrugOrder(UUID id, DrugInstance drugInstance, double amount) {
		super();
		this.id = id;
		this.drugInstance = drugInstance;
		this.amount = amount;
	}
	
	public DrugOrder(DrugInstance drugInstance, double amount) {
		super();
		this.id = UUID.randomUUID();
		this.drugInstance = drugInstance;
		this.amount = amount;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public DrugInstance getDrugInstance() {
		return drugInstance;
	}

	public void setDrugInstance(DrugInstance drugInstance) {
		this.drugInstance = drugInstance;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
