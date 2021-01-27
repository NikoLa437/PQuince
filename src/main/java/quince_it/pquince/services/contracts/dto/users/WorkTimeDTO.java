package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;
import java.util.UUID;

public class WorkTimeDTO {
	
	
	//TODO: dodati deo za apoteku
	
	private UUID forStaff;
	
	private UUID pharmacyId;
	
	private Date startDate;
	
	private Date endDate;
    
    private int startTime;
	
    private int endTime;
    
    public WorkTimeDTO() {}
	
	public WorkTimeDTO(UUID forStaff, UUID pharmacyId, Date startDate, Date endDate, int startTime, int endTime) {
		super();
		this.forStaff= forStaff;
		this.pharmacyId = pharmacyId;
		this.startDate= startDate;
		this.endDate= endDate;
		this.startTime=startTime;
		this.endTime=endTime;
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

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
}
