package quince_it.pquince.entities.users;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.pharmacy.Pharmacy;

@Entity
public class Absence {
	
	@Id
    @Column(name = "id")
	private UUID id;
	
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Staff forStaff;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

	@Column(name = "startDate")
	private Date startDate;
	
    @Column(name = "endDate")
	private Date endDate;
    
	@Enumerated(EnumType.STRING)
	@Column(name="absence_status")
	private AbsenceStatus absenceStatus;
	
    @Column(name = "rejectReason")
	private String rejectReason;
    
    public Absence() {}
	
	public Absence(Staff forStaff, Pharmacy pharmacy, Date startDate, Date endDate) {
		this(UUID.randomUUID(), forStaff, pharmacy, startDate,endDate,AbsenceStatus.WAIT, "");
	}
	
	public Absence(UUID id, Staff forStaff, Pharmacy pharmacy, Date startDate, Date endDate, AbsenceStatus absenceStatus, String rejectReason) {
		super();
		this.id = id;
		this.forStaff= forStaff;
		this.pharmacy = pharmacy;
		this.startDate= startDate;
		this.endDate= endDate;
		this.absenceStatus=absenceStatus;
		this.rejectReason= rejectReason;
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

	public AbsenceStatus getAbsenceStatus() {
		return absenceStatus;
	}

	public void setAbsenceStatus(AbsenceStatus absenceStatus) {
		this.absenceStatus = absenceStatus;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
    public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
}
