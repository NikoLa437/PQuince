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

	public List<Absence> getAbsences() {
		return absences;
	}

	public void setAbsences(List<Absence> absences) {
		this.absences = absences;
	}
	
	
}

