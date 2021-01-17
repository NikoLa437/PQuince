package quince_it.pquince.services.contracts.dto.drugs;

public class ReplaceDrugDTO {

	private String name;
	
	private String code;
	
	private String drugInstanceName;
	
	private ManufacturerDTO manufacturer;

	public ReplaceDrugDTO() {}
	
	public ReplaceDrugDTO(String name, String code, String drugInstanceName, ManufacturerDTO manufacturer) {
		super();
		this.name = name;
		this.code = code;
		this.drugInstanceName = drugInstanceName;
		this.manufacturer = manufacturer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDrugInstanceName() {
		return drugInstanceName;
	}

	public void setDrugInstanceName(String drugInstanceName) {
		this.drugInstanceName = drugInstanceName;
	}

	public ManufacturerDTO getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(ManufacturerDTO manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
}
