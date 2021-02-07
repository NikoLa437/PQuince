package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;
import java.util.UUID;

public class OfferDTO {
	
	private double price;
	
	private Date dueToDate;

	public OfferDTO() {}
	
	public OfferDTO(double price, Date dueToDate) {
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
	
}
