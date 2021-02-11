package quince_it.pquince.entities.drugs;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Staff;

@Entity
public class DrugRequest {
	@Id
	private UUID id;
	@ManyToOne
    private Pharmacy pharmacy;
    @ManyToOne
    private DrugInstance drugInstance;
    @ManyToOne
    private Staff staff;
    @Column
    private Date dateTime;

	public DrugRequest() {
		super();
	}
	public DrugRequest(UUID id, Pharmacy pharmacy, DrugInstance drugInstance, Staff staff) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		this.drugInstance = drugInstance;
		this.staff = staff;
		dateTime = new Date();
	}
	public DrugRequest(Pharmacy pharmacy, DrugInstance drugInstance, Staff staff) {
		this(UUID.randomUUID(), pharmacy, drugInstance, staff);
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Pharmacy getPharmacy() {
		return pharmacy;
	}
	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	public DrugInstance getDrugInstance() {
		return drugInstance;
	}
	public void setDrugInstance(DrugInstance drugInstance) {
		this.drugInstance = drugInstance;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
}
