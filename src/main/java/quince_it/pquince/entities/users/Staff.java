package quince_it.pquince.entities.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;


@Entity
public class Staff extends User {
	
	private static final long serialVersionUID = 1L;
	
    @OneToMany(mappedBy = "forStaff")
	private List<Absence> absences;
	
    
    @Enumerated(EnumType.STRING)
	@Column(name="staffType")
	private StaffType staffType;
   
	public Staff() {
		super();
	}

	public Staff(String email, String password, String name, String surname, Address address, String phoneNumber, StaffType staffType) {
		super(email, password, name, surname, address, phoneNumber, false);
		
		this.absences = new ArrayList<Absence>();
		this.staffType = staffType;
	}

	public Staff(UUID id, String email, String password, String name, String surname, Address address,
			String phoneNumber, boolean active, int penalty, List<Absence> absences,int points,LoyalityCategory loyalityCategory, StaffType staffType) {
		super(id, email, password, name, surname, address, phoneNumber, active);

		this.absences = absences;
		this.staffType = staffType;
	}
	

	public List<Absence> getAbsences() {
		return absences;
	}

	public void setAbsences(List<Absence> absences) {
		this.absences = absences;
	}

	public StaffType getStaffType() {
		return staffType;
	}
	
	
}

