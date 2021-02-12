package quince_it.pquince.entities.pharmacy;

import java.util.Date;

public class IncomeStatistics {
	private Date date;
	private double price;
	
	public IncomeStatistics(Date date, double price) {
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
