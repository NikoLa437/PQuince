package quince_it.pquince.services.implementation.util;

import quince_it.pquince.entities.drugs.Ingredient;
import quince_it.pquince.services.contracts.dto.IngredientDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class IngredientMapper {

	public static IdentifiableDTO<IngredientDTO> MapIngredientPersistenceToIngredientIdentifiableDTO(Ingredient ingredient){
		if(ingredient == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<IngredientDTO>(ingredient.getId(), new IngredientDTO(ingredient.getName()));
	}
}
