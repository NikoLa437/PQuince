package quince_it.pquince.entities.users;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Offer {

	@Id
    @Column(name = "id")
	private UUID id;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "dueToDate")
	private Date dueToDate;
	
	public Offer(double price, Date dueToDate) {
		this(UUID.randomUUID(), price,dueToDate);
	}
	public Offer(UUID id,double price, Date dueToDate) {
		super();
		this.price = price;
		this.dueToDate = dueToDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDueToDate() {
		return dueToDate;
	}

	public void setDueToDate(Date dueToDate) {
		this.dueToDate = dueToDate;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	
}
