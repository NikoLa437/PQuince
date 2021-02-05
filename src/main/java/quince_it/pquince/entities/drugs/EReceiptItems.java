package quince_it.pquince.entities.drugs;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class EReceiptItems {

	@EmbeddedId
	private EReceiptItemsId	 eReceiptItemsId;
	
	@Column(name = "amount")
	private int amount;
	
	public EReceiptItems() {}

	public EReceiptItems(DrugInstance drugInstance, EReceipt eReceipt, int amount) {
		super();
		this.eReceiptItemsId = new EReceiptItemsId(drugInstance, eReceipt);
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public EReceiptItemsId geteReceiptItemsId() {
		return eReceiptItemsId;
	}
	
	public DrugInstance getDrugInstance() {
		return eReceiptItemsId.getDrugInstance();
	}

	public void setDrugInstance(DrugInstance drugInstance) {
		this.eReceiptItemsId.setDrugInstance(drugInstance);
	}

	public EReceipt geteReceipt() {
		return eReceiptItemsId.geteReceipt();
	}

	public void seteReceipt(EReceipt eReceipt) {
		this.eReceiptItemsId.seteReceipt(eReceipt);
	}
}
