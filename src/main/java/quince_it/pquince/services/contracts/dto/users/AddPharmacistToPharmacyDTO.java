package quince_it.pquince.services.contracts.dto.users;

import java.util.Date;
import java.util.UUID;

public class AddPharmacistToPharmacyDTO {
	private UUID pharmacyId;
	
	private UUID pharmacistId;
	
	private Date startDate;
	
	private Date endDate;
    
    private int startTime;
	
    private int endTime;
	
	public AddPharmacistToPharmacyDTO() {
		
	}
	
	public AddPharmacistToPharmacyDTO(UUID pharmacyId, UUID pharmacistId, Date startDate, Date endDate, int startTime, int endTime) {
		super();
		this.pharmacyId = pharmacyId;
		this.pharmacistId = pharmacistId;
		this.startDate= startDate;
		this.endDate= endDate;
		this.startTime=startTime;
		this.endTime=endTime;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public UUID getPharmacistId() {
		return pharmacistId;
	}

	public void setPharmacistId(UUID pharmacistId) {
		this.pharmacistId = pharmacistId;
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
	
	
}
