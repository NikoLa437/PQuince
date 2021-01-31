package quince_it.pquince.entities.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import quince_it.pquince.entities.drugs.Allergen;
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
	
	public Dermatologist(UUID id, String email, String password, String name, String surname, Address address,
			String phoneNumber,List<Absence> absences,List<Pharmacy> pharmacies) {
		super(id, email, password, name, surname, address, phoneNumber, absences, StaffType.DERMATOLOGIST);
		this.pharmacies= pharmacies;
	}


	public List<Pharmacy> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(List<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}
	
	public void addPharmacy(Pharmacy pharmacy) {
		if(this.pharmacies == null)
			this.pharmacies = new ArrayList<Pharmacy>();
		
		this.pharmacies.add(pharmacy);
	}
	
	public void removePharmacy(UUID pharmacyId) {
		if(this.pharmacies == null)
			return;
		
		for(Pharmacy workInPharmacy : pharmacies) {
			if(pharmacyId.equals(workInPharmacy.getId())) {
				pharmacies.remove(workInPharmacy);
				return;
			}
		}
	}
}
