package quince_it.pquince.services.contracts.interfaces;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.users.CountryDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface ICountryService {
	List<IdentifiableDTO<CountryDTO>> findAll();
	IdentifiableDTO<CountryDTO> findById(UUID id);
}
