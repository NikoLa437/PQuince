package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class IngredientDTO {
	
	private String name;
	private UUID id;
		
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
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	
}
