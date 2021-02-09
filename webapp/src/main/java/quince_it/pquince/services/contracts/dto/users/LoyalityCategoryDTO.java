package quince_it.pquince.services.contracts.dto.users;

public class LoyalityCategoryDTO {

	private String name;
	
	public LoyalityCategoryDTO() { }
	public LoyalityCategoryDTO(String name) {
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
