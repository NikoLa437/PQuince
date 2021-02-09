package quince_it.pquince.services.implementation.util.drugs;

import java.util.ArrayList;
import java.util.List;

import quince_it.pquince.entities.drugs.Ingredient;
import quince_it.pquince.services.contracts.dto.drugs.IngredientDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class IngredientMapper {

	public static IdentifiableDTO<IngredientDTO> MapIngredientPersistenceToIngredientIdentifiableDTO(Ingredient ingredient){
		if(ingredient == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<IngredientDTO>(ingredient.getId(), new IngredientDTO(ingredient.getName()));
	}
	
	public static List<IdentifiableDTO<IngredientDTO>> MapIngredientPersistenceListToIngredientIdentifiableDTOList(List<Ingredient> ingredients){
		
		List<IdentifiableDTO<IngredientDTO>> ingredientsDTO = new ArrayList<IdentifiableDTO<IngredientDTO>>();
		ingredients.forEach((i) -> ingredientsDTO.add(MapIngredientPersistenceToIngredientIdentifiableDTO(i)));
		return ingredientsDTO;
	}
}
