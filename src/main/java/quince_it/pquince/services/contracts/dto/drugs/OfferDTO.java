package quince_it.pquince.services.contracts.dto.drugs;

import java.sql.Date;

import quince_it.pquince.entities.drugs.OfferStatus;

public class OfferDTO {

		private Date dateToDelivery;
		
		private double price;
		
		private OfferStatus offerStatus;

		public OfferDTO( ) {}
	    
		public OfferDTO(Date dateToDelivery, double price, OfferStatus offerStatus) {
			super();
			this.dateToDelivery = dateToDelivery;
			this.price = price;
			this.offerStatus = offerStatus;
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
