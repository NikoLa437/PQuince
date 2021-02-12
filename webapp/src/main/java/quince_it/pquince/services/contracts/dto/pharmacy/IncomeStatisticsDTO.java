package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.Date;

public class IncomeStatisticsDTO {
	private Date date;
	private double price;
	
	public IncomeStatisticsDTO(Date date, double price) {
		super();
		this.date = date;
		this.price = price;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
}
