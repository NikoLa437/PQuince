package quince_it.pquince.services.contracts.dto.users;

public class CountryDTO {
	
	private String name;

	public CountryDTO(String name) {
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
