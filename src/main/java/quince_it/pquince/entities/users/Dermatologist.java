package quince_it.pquince.entities.users;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import quince_it.pquince.entities.pharmacy.Pharmacy;

@Entity
public class Dermatologist extends Staff{

	private static final long serialVersionUID = 1L;

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dermatologist_pharmacy",
            joinColumns = @JoinColumn(name = "dermatologist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
    private List<Pharmacy> pharmacies;
	
	public Dermatologist() {}
	

	public Dermatologist(UUID id, String email, String password, String name, String surname, String address, City city,
			String phoneNumber, boolean active, int penalty, List<Absence> absences,int points,LoyalityCategory loyalityCategory, List<Pharmacy> pharmacies) {
		super(id, email, password, name, surname, address, city, phoneNumber, active,penalty,absences,points,loyalityCategory);
		this.pharmacies= pharmacies;
	}


	public List<Pharmacy> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(List<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}
}
