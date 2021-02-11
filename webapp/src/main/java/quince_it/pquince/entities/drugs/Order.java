package quince_it.pquince.entities.drugs;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.PharmacyAdmin;

@Entity
@Table(name="Orders")
public class Order {

	@Id
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pharmacy pharmacy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private PharmacyAdmin pharmacyAdmin;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_drugForOrder",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "drugOrder_id", referencedColumnName = "id"))
    private List<DrugOrder> order;
	
	@Column(name = "date", nullable = false)
	private Date date;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "offers_for_order",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "offers_id", referencedColumnName = "id"))
	private List<Offers> offers;
	
    @Enumerated(EnumType.STRING)
	@Column(name = "Type", nullable = false)
	private OrderStatus orderStatus;

    
    
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(UUID id, Pharmacy pharmacy, PharmacyAdmin pharmacyAdmin, List<DrugOrder> order, Date date,
			List<Offers> offers, OrderStatus orderStatus) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		this.pharmacyAdmin = pharmacyAdmin;
		this.order = order;
		this.date = date;
		this.offers = offers;
		this.orderStatus = orderStatus;
	}
	
	public Order(Pharmacy pharmacy, PharmacyAdmin pharmacyAdmin, List<DrugOrder> order, Date date,
			List<Offers> offers, OrderStatus orderStatus) {
		super();
		this.id = UUID.randomUUID();
		this.pharmacy = pharmacy;
		this.pharmacyAdmin = pharmacyAdmin;
		this.order = order;
		this.date = date;
		this.offers = offers;
		this.orderStatus = orderStatus;
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

	public PharmacyAdmin getPharmacyAdmin() {
		return pharmacyAdmin;
	}

	public void setPharmacyAdmin(PharmacyAdmin pharmacyAdmin) {
		this.pharmacyAdmin = pharmacyAdmin;
	}

	public List<DrugOrder> getOrder() {
		return order;
	}

	public void setOrder(List<DrugOrder> order) {
		this.order = order;
	}
	
	public void addOffer(Offers offer) {
		if(this.offers == null)
			offers = new ArrayList<Offers>();
		
		offers.add(offer);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Offers> getOffers() {
		return offers;
	}

	public void setOffers(List<Offers> offers) {
		this.offers = offers;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}
