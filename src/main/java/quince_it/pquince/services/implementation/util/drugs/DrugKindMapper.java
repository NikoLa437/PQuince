package quince_it.pquince.services.implementation.util.drugs;

import quince_it.pquince.entities.drugs.DrugKindId;
import quince_it.pquince.services.contracts.dto.drugs.DrugKindIdDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class DrugKindMapper {
	public static IdentifiableDTO<DrugKindIdDTO> MapDrugKindIdPersistenceToDrugKindIdIdentifiableDTO(DrugKindId drugKindId){
		if(drugKindId == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<DrugKindIdDTO>(drugKindId.getId(), new DrugKindIdDTO(drugKindId.getType()));
	}
}
