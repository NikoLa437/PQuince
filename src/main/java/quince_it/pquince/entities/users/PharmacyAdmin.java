package quince_it.pquince.entities.users;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import quince_it.pquince.entities.pharmacy.Pharmacy;

@Entity
public class PharmacyAdmin extends Staff {

private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Pharmacy pharmacy;
	
	public PharmacyAdmin() {
		super();
	}
	

	public PharmacyAdmin(String email, String password, String name, String surname, Address address,
			String phoneNumber, Pharmacy pharmacy) {
		super(email, password, name, surname, address, phoneNumber, StaffType.PHARMACYADMIN);
		this.pharmacy= pharmacy;
	}
	
	public PharmacyAdmin(String email, String password, String name, String surname, Address address,
			String phoneNumber) {
		super(email, password, name, surname, address, phoneNumber, StaffType.PHARMACYADMIN);
		this.pharmacy= new Pharmacy();
	}
	
	
	public PharmacyAdmin(UUID id, String email, String password, String name, String surname, Address address,
			String phoneNumber, List<Absence> absences, Pharmacy pharmacy) {
		super(id, email, password, name, surname, address, phoneNumber, absences, StaffType.PHARMACYADMIN);
		this.pharmacy= pharmacy;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
}
