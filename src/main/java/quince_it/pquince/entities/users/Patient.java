package quince_it.pquince.entities.users;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Patient extends User {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "penalty")
	private int penalty;

	public Patient() {
		super();
	}

	public Patient(String email, String password, String name, String surname, String address, City city, String phoneNumber,
			boolean active, int penalty) {
		super(email, password, name, surname, address, city, phoneNumber, active);
		
		this.penalty = penalty;
	}

	public Patient(UUID id, String email, String password, String name, String surname, String address, City city,
			String phoneNumber, boolean active, int penalty) {
		super(id, email, password, name, surname, address, city, phoneNumber, active);

		this.penalty = penalty;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}
	
}
