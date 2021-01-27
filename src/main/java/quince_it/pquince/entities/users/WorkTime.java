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
	
	public WorkTime(Staff staff, Pharmacy pharmacy, Date startDate, Date endDate, int startTime, int endTime) {
		this(UUID.randomUUID(), staff, pharmacy, startDate,endDate,startTime,endTime);
	}
	
	public WorkTime(UUID id, Staff staff, Pharmacy pharmacy, Date startDate, Date endDate, int startTime, int endTime) {
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
}
