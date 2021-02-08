package quince_it.pquince.services.contracts.dto.drugs;

import java.sql.Date;
import java.util.List;

import quince_it.pquince.entities.drugs.OrderStatus;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.PharmacyAdminDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class OrderForProviderDTO {
	
	private IdentifiableDTO<PharmacyDTO> pharmacy;
	
	private IdentifiableDTO<PharmacyAdminDTO> pharmacyAdmin;
	
    private List<IdentifiableDTO<DrugOrderDTO>> order;
	
	private Date date;
	
	private List<IdentifiableDTO<OfferDTO>> offers;
	
	private OrderStatus orderStatus;

    
    
	public OrderForProviderDTO() {
		super();
	}

	public OrderForProviderDTO(IdentifiableDTO<PharmacyDTO> pharmacy, IdentifiableDTO<PharmacyAdminDTO> pharmacyAdmin, List<IdentifiableDTO<DrugOrderDTO>> order, Date date,
			OrderStatus orderStatus) {
		super();
		this.pharmacy = pharmacy;
		this.pharmacyAdmin = pharmacyAdmin;
		this.order = order;
		this.date = date;
		this.orderStatus = orderStatus;
	}
	
	public OrderForProviderDTO(IdentifiableDTO<PharmacyDTO> pharmacy, IdentifiableDTO<PharmacyAdminDTO> pharmacyAdmin, List<IdentifiableDTO<DrugOrderDTO>> order, Date date,
			OrderStatus orderStatus, List<IdentifiableDTO<OfferDTO>> offers) {
		super();
		this.pharmacy = pharmacy;
		this.pharmacyAdmin = pharmacyAdmin;
		this.order = order;
		this.date = date;
		this.orderStatus = orderStatus;
		this.offers = offers;
	}

	public IdentifiableDTO<PharmacyDTO> getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(IdentifiableDTO<PharmacyDTO> pharmacy) {
		this.pharmacy = pharmacy;
	}

	public IdentifiableDTO<PharmacyAdminDTO> getPharmacyAdmin() {
		return pharmacyAdmin;
	}

	public void setPharmacyAdmin(IdentifiableDTO<PharmacyAdminDTO> pharmacyAdmin) {
		this.pharmacyAdmin = pharmacyAdmin;
	}

	public List<IdentifiableDTO<DrugOrderDTO>> getOrder() {
		return order;
	}

	public void setOrder(List<IdentifiableDTO<DrugOrderDTO>> order) {
		this.order = order;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<IdentifiableDTO<OfferDTO>> getOffers() {
		return offers;
	}

	public void setOffers(List<IdentifiableDTO<OfferDTO>> offers) {
		this.offers = offers;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}