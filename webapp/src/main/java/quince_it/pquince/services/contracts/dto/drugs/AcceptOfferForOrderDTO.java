package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class AcceptOfferForOrderDTO {
	private UUID offerId;
	private UUID orderId;
	public AcceptOfferForOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AcceptOfferForOrderDTO(UUID offerId, UUID orderId) {
		super();
		this.offerId = offerId;
		this.orderId = orderId;
	}
	public UUID getOfferId() {
		return offerId;
	}
	public void setOfferId(UUID offerId) {
		this.offerId = offerId;
	}
	public UUID getOrderId() {
		return orderId;
	}
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	
	
}
