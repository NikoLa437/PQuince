package quince_it.pquince.services.contracts.dto.users;

import java.util.UUID;

public class PharmacistFiltrationDTO {

	
	private String name;
	private String surname;
	private double gradeFrom;
	private double gradeTo;
	private UUID pharmacyId;
	
	public PharmacistFiltrationDTO(String name, String surname, double gradeFrom, double gradeTo, UUID pharmacyId) {
		super();
		this.name = name;
		this.surname = surname;
		this.gradeFrom = gradeFrom;
		this.gradeTo = gradeTo;
		this.pharmacyId = pharmacyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public double getGradeFrom() {
		return gradeFrom;
	}

	public void setGradeFrom(double gradeFrom) {
		this.gradeFrom = gradeFrom;
	}

	public double getGradeTo() {
		return gradeTo;
	}

	public void setGradeTo(double gradeTo) {
		this.gradeTo = gradeTo;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

}
