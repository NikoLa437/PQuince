package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;


public class AbsenceResponseDTO {
	private String forStaff;
	
	private Date startDate;
	
	private Date endDate;
    	
   
	public AbsenceResponseDTO() {
		
	}

	public AbsenceResponseDTO(String forStaff, Date startDate, Date endDate) {
		super();
		this.forStaff= forStaff;
		this.startDate= startDate;
		this.endDate= endDate;
	}


	public String getForStaff() {
		return forStaff;
	}

	public void setForStaff(String forStaff) {
		this.forStaff = forStaff;
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
