package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.Date;

public class DateRangeDTO {
	private Date dateFrom;
	private Date dateTo;
	
	
	public DateRangeDTO(Date dateFrom, Date dateTo) {
		super();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
	public DateRangeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	
}
