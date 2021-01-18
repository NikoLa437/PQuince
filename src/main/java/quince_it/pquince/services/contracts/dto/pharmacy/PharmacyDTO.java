package quince_it.pquince.services.contracts.dto.pharmacy;

import quince_it.pquince.entities.users.Address;

public class PharmacyDTO {

	private String name;
	
	private Address address;
	
	private String description;

	public PharmacyDTO() {}
	
	public PharmacyDTO(String name, Address address, String description) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
