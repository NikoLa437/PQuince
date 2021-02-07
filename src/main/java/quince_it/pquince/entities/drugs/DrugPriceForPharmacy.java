package quince_it.pquince.entities.drugs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import quince_it.pquince.entities.pharmacy.Pharmacy;

@Entity
public class DrugPriceForPharmacy{
	
	@EmbeddedId
	private DrugPriceForPharmacyId drugPriceForPharmacyId;
	
	@Column(name = "dateFrom", nullable = false)
	private Date dateFrom;
	
	@Column(name = "dateTo", nullable = false)
	private Date dateTo;
	
	@Column(name = "price", nullable = false)
	private double price;

	public DrugPriceForPharmacy() {}

	public DrugPriceForPharmacy(DrugInstance drugInstance, Pharmacy pharmacy, Date dateFrom, Date dateTo,
			double price) {
		super();
		this.drugPriceForPharmacyId = new DrugPriceForPharmacyId(drugInstance, pharmacy);
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.price = price;
	}

	public DrugInstance getDrugInstance() {
		return this.drugPriceForPharmacyId.getDrugInstance();
	}


	public void setDrugInstance(DrugInstance drugInstance) {
		this.drugPriceForPharmacyId.setDrugInstance(drugInstance);
	}


	public Pharmacy getPharmacy() {
		return this.drugPriceForPharmacyId.getPharmacy();
	}


	public void setPharmacy(Pharmacy pharmacy) {
		this.drugPriceForPharmacyId.setPharmacy(pharmacy);
	}


	public Date getDateFrom() {
		return dateFrom;
	}


	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}


	public Date getDateTo() {
		return dateTo;
	}


	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
