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

@Entity
public class UserFeedback {
	@Id
    @Column(name = "id")
	private UUID id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Staff forStaff;
	
    @Column(name = "date")
	private Date Date;
    
	@Enumerated(EnumType.STRING)
	@Column(name="grade")
	private Grade grade;
	
	public UserFeedback() {}
	
	public UserFeedback(Staff forStaff, Date date, Grade grade) {
		this(UUID.randomUUID(), forStaff,date,grade);
	}
	
	public UserFeedback(UUID id, Staff forStaff, Date date, Grade grade) {
		super();
		this.id = id;
		this.forStaff= forStaff;
		this.Date=date;
		this.grade=grade;
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

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
}
