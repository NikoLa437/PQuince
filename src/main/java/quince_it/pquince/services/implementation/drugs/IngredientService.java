package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.Ingredient;
import quince_it.pquince.repository.drugs.IngredientRepository;
import quince_it.pquince.services.contracts.dto.drugs.IngredientDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IIngredientService;
import quince_it.pquince.services.implementation.util.drugs.IngredientMapper;

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
	public IdentifiableDTO<IngredientDTO> findByName(String name) {
		return IngredientMapper.MapIngredientPersistenceToIngredientIdentifiableDTO(ingredientRepository.findByName(name));
	}
	
	private Ingredient findByIngredientName(String name) {
		return ingredientRepository.findByName(name);
	}
	

	@Override
	public UUID create(IngredientDTO entityDTO) {
		if(findByIngredientName(entityDTO.getName()) != null)
			update(entityDTO,findByIngredientName(entityDTO.getName()).getId());
		Ingredient ingredient = CreateIngredientFromDTO(entityDTO);
		ingredientRepository.save(ingredient);
		return ingredient.getId();
	}
	
	private boolean checkifIngredientExist(IngredientDTO ingredientDTO) {
		for(Ingredient i : ingredientRepository.findAll()){
			if(i.getName().equals(ingredientDTO.getName()))
				return true;
		}
		
		return false;
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
