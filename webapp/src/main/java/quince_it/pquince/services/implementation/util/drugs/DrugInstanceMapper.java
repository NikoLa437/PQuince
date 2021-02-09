package quince_it.pquince.services.implementation.util.drugs;

import java.util.ArrayList;
import java.util.List;

import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.dto.drugs.ManufacturerDTO;
import quince_it.pquince.services.contracts.dto.drugs.ReplaceDrugDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class DrugInstanceMapper {

	public static IdentifiableDTO<DrugInstanceDTO> MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(DrugInstance drug){
		if(drug == null) throw new IllegalArgumentException();
				
		return new IdentifiableDTO<DrugInstanceDTO>(drug.getId(), new DrugInstanceDTO(drug.getName(), drug.getCode(), drug.getDrugInstanceName(),
																					  ManufacturerMapper.MapManufacturerPersistenceToManufacturerIdentifiableDTO(drug.getManufacturer()),
																					  drug.getDrugFormat(), drug.getQuantity(), drug.getSideEffects(), drug.getRecommendedAmount(),
																					  MapListReplaceDugsPersistenceToListReplaceDrugsIdentifiableDTO(drug.getReplacingDrugs()),
																					  AllergenMapper.MapAllergenPersistenceListToAllergenIdentifiableDTOList(drug.getAllergens()),
																					  IngredientMapper.MapIngredientPersistenceListToIngredientIdentifiableDTOList(drug.getIngredients()),
																					  drug.getLoyalityPoints(), drug.isOnReciept(), drug.getDrugKind()));
	}
	
	public static List<IdentifiableDTO<ReplaceDrugDTO>> MapListReplaceDugsPersistenceToListReplaceDrugsIdentifiableDTO(List<DrugInstance> drugs){
		List<IdentifiableDTO<ReplaceDrugDTO>> returnDrugs = new ArrayList<IdentifiableDTO<ReplaceDrugDTO>>();
		
		drugs.forEach((drug) -> returnDrugs.add(MapDrugInstancePersistenceToReplaceDugIdentifiableDTO(drug)));
		return returnDrugs;
	}
	
	public static IdentifiableDTO<ReplaceDrugDTO> MapDrugInstancePersistenceToReplaceDugIdentifiableDTO(DrugInstance drug){
		if(drug == null) throw new IllegalArgumentException();
		
		
		return new IdentifiableDTO<ReplaceDrugDTO>(drug.getId(), new ReplaceDrugDTO(drug.getName(), drug.getCode(), drug.getDrugInstanceName(), new ManufacturerDTO(drug.getManufacturer().getName())));
	}

}