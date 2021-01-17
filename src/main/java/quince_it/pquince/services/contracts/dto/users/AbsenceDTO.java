package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;
import java.util.UUID;

import quince_it.pquince.entities.users.AbsenceStatus;
import quince_it.pquince.entities.users.Staff;

public class AbsenceDTO {

	private UUID forStaff;
	
	private Date startDate;
	
	private Date endDate;
    
	private AbsenceStatus absenceStatus;
   
	public AbsenceDTO() {
		
	}
	
	public AbsenceDTO(UUID forStaff, Date startDate, Date endDate, AbsenceStatus absenceStatus) {
		super();
		this.forStaff= forStaff;
		this.startDate= startDate;
		this.endDate= endDate;
		this.absenceStatus=absenceStatus;
	}
	

	public UUID getForStaff() {
		return forStaff;
	}

	public void setForStaff(UUID forStaff) {
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

	public AbsenceStatus getAbsenceStatus() {
		return absenceStatus;
	}

	public void setAbsenceStatus(AbsenceStatus absenceStatus) {
		this.absenceStatus = absenceStatus;
	}
}
