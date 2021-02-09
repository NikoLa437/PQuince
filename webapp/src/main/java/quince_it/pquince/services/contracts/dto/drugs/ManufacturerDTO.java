package quince_it.pquince.services.contracts.dto.drugs;

public class ManufacturerDTO {
	
	private String name;
	
	public ManufacturerDTO() { }
	
	public ManufacturerDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
