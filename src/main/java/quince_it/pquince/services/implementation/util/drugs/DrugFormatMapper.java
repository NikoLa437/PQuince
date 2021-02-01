package quince_it.pquince.services.implementation.util.drugs;

import quince_it.pquince.entities.drugs.DrugFormatId;
import quince_it.pquince.services.contracts.dto.drugs.DrugFormatIdDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class DrugFormatMapper {
	public static IdentifiableDTO<DrugFormatIdDTO> MapDrugFormatIdPersistenceToDrugFormatIdIdentifiableDTO(DrugFormatId drugFormatId){
		if(drugFormatId == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<DrugFormatIdDTO>(drugFormatId.getId(), new DrugFormatIdDTO(drugFormatId.getType()));
	}
}
