package quince_it.pquince.entities.users;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.pharmacy.Pharmacy;

@Entity
public class WorkTime {
	@Id
    @Column(name = "id")
	private UUID id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Pharmacy forPharmacy;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Staff forStaff;
	
	@Column(name = "startDate")
	private Date startDate;
	
    @Column(name = "endDate")
	private Date endDate;
    
	@Column(name = "startTime")
    private int startTime;
	
    @Column(name = "endTime")
    private int endTime;
    
    public WorkTime() {}
	
	public WorkTime(Staff forStaff, Date startDate, Date endDate, int startTime, int endTime,Pharmacy forPharmacy) {
		this(UUID.randomUUID(), forStaff,startDate,endDate,startTime,endTime,forPharmacy);
	}
	
	public WorkTime(UUID id, Staff forStaff, Date startDate, Date endDate, int startTime, int endTime,Pharmacy forPharmacy) {
		super();
		this.id = id;
		this.forStaff= forStaff;
		this.startDate= startDate;
		this.endDate= endDate;
		this.startTime=startTime;
		this.endTime=endTime;
		this.forPharmacy=forPharmacy;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Staff getForStaff() {
		return forStaff;
	}

	public void setForStaff(Staff forStaff) {
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
}
