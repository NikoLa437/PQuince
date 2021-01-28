package quince_it.pquince.services.contracts.dto.pharmacy;

import quince_it.pquince.entities.users.Address;

public class PharmacyGradeDTO {

	private String name;
	
	private Address address;
	
	private String description;

	private double grade;
	
	public PharmacyGradeDTO() {}
	
	public PharmacyGradeDTO(String name, Address address, String description, double grade) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.grade = grade;
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

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}
}
