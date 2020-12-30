package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.data_provider.IngredientRepository;
import quince_it.pquince.entities.drugs.Ingredient;
import quince_it.pquince.services.contracts.dto.IngredientDTO;
import quince_it.pquince.services.contracts.identifiable.dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IIngredientService;
import quince_it.pquince.services.implementation.util.IngredientMapper;

@Service
public class IngredientService implements IIngredientService{

	@Autowired
	private IngredientRepository ingredientRepository;

	@Override
	public List<IdentifiableDTO<IngredientDTO>> findAll() {
		
		List<IdentifiableDTO<IngredientDTO>> ingredients = new ArrayList<IdentifiableDTO<IngredientDTO>>();
		ingredientRepository.findAll().forEach((i) -> ingredients.add(IngredientMapper.MapIngredientPersistenceToIngredientIdentifiableDTO(i)));

		return ingredients;
	}

	@Override
	public IdentifiableDTO<IngredientDTO> findById(UUID id) {
		return IngredientMapper.MapIngredientPersistenceToIngredientIdentifiableDTO(ingredientRepository.getOne(id));
	}

	@Override
	public UUID create(IngredientDTO entityDTO) {
		Ingredient ingredient = CreateIngredientFromDTO(entityDTO);
		ingredientRepository.save(ingredient);
		return ingredient.getId();
	}

	@Override
	public void update(IngredientDTO entityDTO, UUID id) {
		Ingredient ingredient = CreateIngredientFromDTO(entityDTO, id);
		ingredientRepository.save(ingredient);
	}

	@Override
	public boolean delete(UUID id) {
		try {
			ingredientRepository.deleteById(id);
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		}
	}

	private Ingredient CreateIngredientFromDTO(IngredientDTO ingredientDTO) {
		return new Ingredient(ingredientDTO.getName());
	}
	 
	private Ingredient CreateIngredientFromDTO(IngredientDTO ingredientDTO, UUID id) {
		return new Ingredient(id, ingredientDTO.getName());
	}
}
