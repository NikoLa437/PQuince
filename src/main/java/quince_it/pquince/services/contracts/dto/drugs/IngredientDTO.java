package quince_it.pquince.services.contracts.dto.drugs;

public class IngredientDTO {
	
	private String name;
		
	public IngredientDTO() { }
	public IngredientDTO(String name) {
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
