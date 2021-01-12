package quince_it.pquince.services.implementation.util;

import quince_it.pquince.entities.users.City;
import quince_it.pquince.services.contracts.dto.users.CityDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class CityMapper {
	
	public static IdentifiableDTO<CityDTO> MapCityPersistenceToCityIdentifiableDTO(City city){
		if(city == null) throw new IllegalArgumentException();

		
		return new IdentifiableDTO<CityDTO>(city.getId(), new CityDTO(city.getName(), city.getCountry().getId()));

	}
}
