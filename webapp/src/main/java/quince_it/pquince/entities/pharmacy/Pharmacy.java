package quince_it.pquince.entities.pharmacy;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import quince_it.pquince.entities.users.Address;

@Entity
public class Pharmacy {

	@Version
	private Long version;
	
	@Id
	private UUID id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Embedded
	private Address address;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "consultationPrice")
    private double consultationPrice;
	
	public Pharmacy() {}
	
	public Pharmacy(UUID id, String name, String description, Address address, double consultationPrice) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.consultationPrice = consultationPrice;
	}
	
	public Pharmacy(String name, String description, Address address, double consultationPrice) {
		this(UUID.randomUUID(), name, description, address, consultationPrice);
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public double getConsultationPrice() {
		return consultationPrice;
	}

	public void setConsultationPrice(double consultationPrice) {
		this.consultationPrice = consultationPrice;
	}
	
	public Long getVersion() {
		return version;
	}
}
