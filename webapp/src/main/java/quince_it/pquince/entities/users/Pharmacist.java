package quince_it.pquince.entities.users;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.pharmacy.Pharmacy;

@Entity
public class Pharmacist extends Staff {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Pharmacy pharmacy;

	public Pharmacist() {}
	
	public Pharmacist(String email, String password, String name, String surname, Address address,
			String phoneNumber, Pharmacy pharmacy) {
		super(email, password, name, surname, address, phoneNumber, StaffType.PHARMACIST);
		this.pharmacy= pharmacy;
	}
	
	public Pharmacist(UUID id, String email, String password, String name, String surname, Address address,
			String phoneNumber, List<Absence> absences, Pharmacy pharmacy) {
		super(id, email, password, name, surname, address, phoneNumber, absences, StaffType.PHARMACIST);
		this.pharmacy= pharmacy;
	}
	
	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}


	public void removePharmacy() {
		this.pharmacy = null;
	}
}
