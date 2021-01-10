package quince_it.pquince.services.implementation.util;

import quince_it.pquince.entities.users.Country;
import quince_it.pquince.services.contracts.dto.users.CountryDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class CountryMapper {

	public static IdentifiableDTO<CountryDTO> MapCountryPersistenceToCountryIdentifiableDTO(Country country){
		if(country == null) throw new IllegalArgumentException();

		
		return new IdentifiableDTO<CountryDTO>(country.getId(), new CountryDTO(country.getName()));

	}

}
