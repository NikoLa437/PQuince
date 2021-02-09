package quince_it.pquince.entities.users;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.pharmacy.Pharmacy;

@Entity
public class WorkTime {
	@Id
    @Column(name = "id")
	private UUID id;
	
	@ManyToOne
	private Pharmacy pharmacy;
	
	@ManyToOne
	private Staff staff;
	
	@Column(name = "startDate")
	private Date startDate;
	
    @Column(name = "endDate")
	private Date endDate;
    
	@Column(name = "startTime")
    private int startTime;
	
    @Column(name = "endTime")
    private int endTime;
    
    public WorkTime() {}
	
	public WorkTime(Pharmacy pharmacy,Staff staff, Date startDate, Date endDate, int startTime, int endTime) {
		this(UUID.randomUUID(), staff,startDate,endDate,startTime,endTime,pharmacy);
	}
	
	public WorkTime(UUID id, Staff staff, Date startDate, Date endDate, int startTime, int endTime,Pharmacy pharmacy) {
		super();
		this.id = id;
		this.staff= staff;
		this.startDate= startDate;
		this.endDate= endDate;
		this.startTime=startTime;
		this.endTime=endTime;
		this.pharmacy = pharmacy;

	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
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

	public Pharmacy getPharmacy() {
		return pharmacy;
	}
	
	public boolean IsWorkTimesOverlap(WorkTime workTime) {
		 if(this.startDate.before(workTime.getEndDate()) && workTime.getStartDate().before(this.endDate)) {
		    	if(this.startTime<workTime.getEndTime() && workTime.getStartTime()<this.endTime) {
		    		return true;
		    	}
		    }
		    return false;
	}
	
	public boolean IsCorrectWorkTimeFormat() {
		if(this.endDate.before(this.startDate) || this.startTime>=this.endTime)
			return false;
		return true;
	}
}
