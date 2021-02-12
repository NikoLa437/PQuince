package quince_it.pquince.services.contracts.dto.drugs;

import java.util.Date;
import java.util.UUID;

import quince_it.pquince.entities.drugs.OfferStatus;

public class OfferDTO {

		private Date dateToDelivery;
		
		private UUID id;
		
		private double price;
		
		private OfferStatus offerStatus;

		public OfferDTO( ) {}
	    
		public OfferDTO(Date dateToDelivery, double price, OfferStatus offerStatus) {
			super();
			this.dateToDelivery = dateToDelivery;
			this.price = price;
			this.offerStatus = offerStatus;
		}
		
		public OfferDTO(Date dateToDelivery, double price, OfferStatus offerStatus, UUID id) {
			super();
			this.dateToDelivery = dateToDelivery;
			this.price = price;
			this.offerStatus = offerStatus;
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

		public OfferStatus getOfferStatus() {
			return offerStatus;
		}

		public void setOfferStatus(OfferStatus offerStatus) {
			this.offerStatus = offerStatus;
		}

		public UUID getId() {
			return id;
		}

		public void setId(UUID id) {
			this.id = id;
		}
	    
	
}
