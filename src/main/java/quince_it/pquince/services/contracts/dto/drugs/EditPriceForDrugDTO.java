package quince_it.pquince.services.contracts.dto.drugs;

import java.sql.Date;
import java.util.UUID;

public class EditPriceForDrugDTO {
	private UUID drugInstanceId;
	private double price;
	private Date startDate;
	private Date endDate;
	
	public EditPriceForDrugDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EditPriceForDrugDTO(UUID drugInstanceId, double price,Date startDate, Date endDate) {
		super();
		this.drugInstanceId = drugInstanceId;
		this.price = price;
		this.startDate= startDate;
		this.endDate=endDate;
	}
	public UUID getDrugInstanceId() {
		return drugInstanceId;
	}
	public void setDrugInstanceId(UUID drugInstanceId) {
		this.drugInstanceId = drugInstanceId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
}
