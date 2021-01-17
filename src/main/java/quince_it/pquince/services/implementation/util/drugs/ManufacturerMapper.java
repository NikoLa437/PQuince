package quince_it.pquince.services.implementation.util.drugs;

import quince_it.pquince.entities.drugs.Manufacturer;
import quince_it.pquince.services.contracts.dto.drugs.ManufacturerDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class ManufacturerMapper {

	public static IdentifiableDTO<ManufacturerDTO> MapManufacturerPersistenceToManufacturerIdentifiableDTO(Manufacturer manufacturer){
		if(manufacturer == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<ManufacturerDTO>(manufacturer.getId(), new ManufacturerDTO(manufacturer.getName()));
	}
}
