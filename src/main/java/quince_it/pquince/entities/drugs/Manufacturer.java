package quince_it.pquince.entities.drugs;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Manufacturer {

	@Id
	private UUID id;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	public Manufacturer() {}
	
	public Manufacturer(String name) {
		this(UUID.randomUUID(), name);
	}
	
	public Manufacturer(UUID id, String name) {
		super();
		this.id = id;
		this.name = name;
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
}
