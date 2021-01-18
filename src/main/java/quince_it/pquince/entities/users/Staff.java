package quince_it.pquince.entities.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
public class Staff extends User {
	private static final long serialVersionUID = 1L;
	
    @OneToMany(mappedBy = "forStaff")
	private List<Absence> absences;
	
    @OneToMany(mappedBy = "forStaff")
    private List<UserFeedback> feedbacks;
    
	public Staff() {
		super();
	}

	public Staff(String email, String password, String name, String surname, String address, City city, String phoneNumber) {
		super(email, password, name, surname, address, city, phoneNumber, false);
		
		this.absences = new ArrayList<Absence>();
	}

	public Staff(UUID id, String email, String password, String name, String surname, String address, City city,
			String phoneNumber, boolean active, int penalty, List<Absence> absences,int points,LoyalityCategory loyalityCategory) {
		super(id, email, password, name, surname, address, city, phoneNumber, active);

		this.absences = absences;
	}
	
	public void addFeedback(UserFeedback userFeedback) {
		
		if(this.feedbacks == null)
			this.feedbacks = new ArrayList<UserFeedback>();
		
		this.feedbacks.add(userFeedback);
	}

	public void removeFeedback(UUID userFeedbackId) {
		
		if(this.feedbacks == null)
			return;
		
		for (UserFeedback userFeedback : this.feedbacks) {
			if(userFeedback.getId().equals(userFeedbackId)) {
				this.feedbacks.remove(userFeedback);
				break;
			}
		}
	}
	
	public double averageGrade() {
		double sum=0;
		for(UserFeedback feedback : this.feedbacks) {
			sum += this.getGradeFromEnum(feedback.getGrade());
		}
		
		return sum/this.feedbacks.size();
	}

	private double getGradeFromEnum(Grade grade) {
		if(grade == Grade.I)
			return 1;
		else if(grade==Grade.II)
			return 2;
		else if(grade==Grade.III)
			return 3;
		else if(grade==Grade.IV)
			return 4;
		else
			return 5;
	}

	public List<Absence> getAbsences() {
		return absences;
	}

	public void setAbsences(List<Absence> absences) {
		this.absences = absences;
	}
	
	
}

