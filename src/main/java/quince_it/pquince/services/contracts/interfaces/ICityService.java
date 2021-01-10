package quince_it.pquince.services.contracts.interfaces;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.users.CityDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface ICityService {
	List<IdentifiableDTO<CityDTO>> findAll();
	IdentifiableDTO<CityDTO> findById(UUID id);
	
}
