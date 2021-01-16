package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.UUID;

public class PharmacyFiltrationRepositoryDTO {

	private UUID pharmacyId;
	
	private String name;
	
	private String address;
	
	private String description;

	private double grade;
	
	public PharmacyFiltrationRepositoryDTO() {}
	
	public PharmacyFiltrationRepositoryDTO(UUID pharmacyId, String name, String address, String description,
			double grade) {
		super();
		this.pharmacyId = pharmacyId;
		this.name = name;
		this.address = address;
		this.description = description;
		this.grade = grade;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
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
