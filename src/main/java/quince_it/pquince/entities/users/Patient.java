package quince_it.pquince.entities.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import quince_it.pquince.entities.drugs.Allergen;

@Entity
public class Patient extends User {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "penalty")
	private int penalty;
	
	@Column(name = "points")
	private int points;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "patient_allergen",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "allergen_id", referencedColumnName = "id"))
    private List<Allergen> allergens;
	
	public Patient() {
		super();
	}

	public Patient(String email, String password, String name, String surname, Address address, String phoneNumber) {
		super(email, password, name, surname, address, phoneNumber, false);
		
		this.allergens = new ArrayList<Allergen>();
		this.penalty = 0;
		this.points = 0;
	}

	public Patient(UUID id, String email, String password, String name, String surname, Address address,
			String phoneNumber, boolean active, int penalty, List<Allergen> allergens,int points,LoyalityCategory loyalityCategory) {
		super(id, email, password, name, surname, address, phoneNumber, active);

		this.penalty = penalty;
		this.allergens = allergens;
		this.points = points;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}
	
	public void addAllergen(Allergen allergen) {
		
		if(allergens == null)
			this.allergens = new ArrayList<Allergen>();
		
		this.allergens.add(allergen);
	}

	public void removeAllergen(UUID allergenId) {
		
		if(allergens == null)
			return;
		
		for (Allergen a : this.allergens) {
			if(a.getId().equals(allergenId)) {
				this.allergens.remove(a);
				break;
			}
		}
	}
	public List<Allergen> getAllergens() {
		return allergens;
	}

	public void setAllergens(List<Allergen> allergens) {
		this.allergens = allergens;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public void addPenalty(int amount) {
		this.penalty += amount;
	}
	
}
