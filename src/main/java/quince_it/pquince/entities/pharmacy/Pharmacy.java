package quince_it.pquince.entities.pharmacy;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pharmacy {

	@Id
	private UUID id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	public Pharmacy() {}
	
	public Pharmacy(UUID id, String name, String description, String address) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
	}
	
	public Pharmacy(String name, String description, String address) {
		this(UUID.randomUUID(), name, description, address);
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
