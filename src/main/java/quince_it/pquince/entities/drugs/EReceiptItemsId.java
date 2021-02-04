package quince_it.pquince.entities.drugs;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class EReceiptItemsId implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private DrugInstance drugInstance;
	
	@ManyToOne(optional = false)
	private EReceipt eReceipt;

	public EReceiptItemsId() {}
	
	public EReceiptItemsId(DrugInstance drugInstance, EReceipt eReceipt) {
		super();
		this.drugInstance = drugInstance;
		this.eReceipt = eReceipt;
	}

	public DrugInstance getDrugInstance() {
		return drugInstance;
	}

	public void setDrugInstance(DrugInstance drugInstance) {
		this.drugInstance = drugInstance;
	}

	public EReceipt geteReceipt() {
		return eReceipt;
	}

	public void seteReceipt(EReceipt eReceipt) {
		this.eReceipt = eReceipt;
	}
	
	
}
