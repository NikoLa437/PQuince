package quince_it.pquince.services.implementation.util.drugs;

import java.util.ArrayList;
import java.util.List;

import quince_it.pquince.entities.drugs.Allergen;
import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class AllergenMapper {

	public static IdentifiableDTO<AllergenDTO> MapAllergenPersistenceToAllergenIdentifiableDTO(Allergen allergen){
		if(allergen == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<AllergenDTO>(allergen.getId(), new AllergenDTO(allergen.getName()));
	}
	
	public static List<IdentifiableDTO<AllergenDTO>> MapAllergenPersistenceListToAllergenIdentifiableDTOList(List<Allergen> allergens){
		
		List<IdentifiableDTO<AllergenDTO>> allergeListDTO = new ArrayList<IdentifiableDTO<AllergenDTO>>();
		allergens.forEach((a) -> allergeListDTO.add(MapAllergenPersistenceToAllergenIdentifiableDTO(a)));
		return allergeListDTO;
	}
}
