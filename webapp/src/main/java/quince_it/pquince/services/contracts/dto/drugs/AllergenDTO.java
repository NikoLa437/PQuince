package quince_it.pquince.services.contracts.dto.drugs;

public class AllergenDTO {

	private String name;
	
	public AllergenDTO() { }
	public AllergenDTO(String name) {
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
