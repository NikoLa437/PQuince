package quince_it.pquince.entities.drugs;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.pharmacy.Pharmacy;

@Entity
@IdClass(DrugPriceForPharmacyId.class)
public class DrugPriceForPharmacy implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	private DrugInstance drugInstance;
	
	@Id
	@ManyToOne
	private Pharmacy pharmacy;
	
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
		this.drugInstance = drugInstance;
		this.pharmacy = pharmacy;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.price = price;
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
