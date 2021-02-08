package quince_it.pquince.entities.drugs;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Offers")
public class Offers {

	@Id
	private UUID id;
	
	//dodati provider
	
	@Column(name = "dateToDelivery", nullable = false)
	private Date dateToDelivery;
	
	@Column(name= "price" , nullable=false)
	private double price;
	
    @Enumerated(EnumType.STRING)
	@Column(name = "Status", nullable = false)
	private OfferStatus offerStatus;

    public Offers(){}
    
	public Offers( Date dateToDelivery, double price, OfferStatus offerStatus) {
		super();
		this.id = UUID.randomUUID();
		this.dateToDelivery = dateToDelivery;
		this.price = price;
		this.offerStatus = offerStatus;
	}
    
	public Offers(UUID id, Date dateToDelivery, double price, OfferStatus offerStatus) {
		super();
		this.id = id;
		this.dateToDelivery = dateToDelivery;
		this.price = price;
		this.offerStatus = offerStatus;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getDateToDelivery() {
		return dateToDelivery;
	}

	public void setDateToDelivery(Date dateToDelivery) {
		this.dateToDelivery = dateToDelivery;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public OfferStatus getOrderStatus() {
		return offerStatus;
	}

	public void setOrderStatus(OfferStatus offerStatus) {
		this.offerStatus = offerStatus;
	}
    
    
}
