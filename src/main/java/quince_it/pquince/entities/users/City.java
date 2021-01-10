package quince_it.pquince.entities.users;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class City {

	@Id
    @Column(name = "id")
	private UUID id;
	
    @Column(name = "name", nullable = false)
	private String name;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Country country;
	
    @OneToMany(mappedBy = "city")
    private List<User> users;
    
    public City() {}
    
	public City(UUID id, String name, Country country) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
}
