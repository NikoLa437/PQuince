package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;

public class RequestAbsenceDTO {
	private Date startDate;
	private Date endDate;
	public RequestAbsenceDTO(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public RequestAbsenceDTO() {
		super();
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
