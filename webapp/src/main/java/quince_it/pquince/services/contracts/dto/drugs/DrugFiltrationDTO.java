package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class DrugFiltrationDTO {
	private String name;
	private String manufacturer;
	private double gradeFrom;
	private double gradeTo;
	private UUID pharmacyId;
	
	public DrugFiltrationDTO(String name, String manufacturer, double gradeFrom, double gradeTo, UUID pharmacyId) {
		super();
		this.name = name;
		this.manufacturer = manufacturer;
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

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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
