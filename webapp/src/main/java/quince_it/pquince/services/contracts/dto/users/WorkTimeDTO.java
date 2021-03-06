package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;
import java.util.UUID;

public class WorkTimeDTO {
	
	private UUID forPharmacy;
	
	private UUID forStaff;
		
	private String pharmacyName;
	
	private Date startDate;
	
	private Date endDate;
    
    private int startTime;
	
    private int endTime;
    
    public WorkTimeDTO() {}
    
    public WorkTimeDTO(UUID forPharmacy,UUID forStaff, Date startDate, Date endDate, int startTime, int endTime,String pharmacyName) {
		super();
		this.forPharmacy= forPharmacy;
		this.forStaff= forStaff;
		this.startDate= startDate;
		this.endDate= endDate;
		this.startTime=startTime;
		this.endTime=endTime;
		this.pharmacyName=pharmacyName;
	}


	public UUID getForPharmacy() {
		return forPharmacy;
	}

	public void setForPharmacy(UUID forPharmacy) {
		this.forPharmacy = forPharmacy;
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


	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	
}
