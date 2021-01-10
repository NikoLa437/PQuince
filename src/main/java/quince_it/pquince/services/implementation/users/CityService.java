package quince_it.pquince.services.implementation.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.repository.users.CityRepository;
import quince_it.pquince.services.contracts.dto.users.CityDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.ICityService;
import quince_it.pquince.services.implementation.util.CityMapper;

@Service
public class CityService implements ICityService {

	@Autowired
	private CityRepository cityRepository;
	
	@Override
	public List<IdentifiableDTO<CityDTO>> findAll() {
		List<IdentifiableDTO<CityDTO>> cities = new ArrayList<IdentifiableDTO<CityDTO>>();
		cityRepository.findAll().forEach((c) -> cities.add(CityMapper.MapCityPersistenceToCityIdentifiableDTO(c)));
		return cities;
	}

	@Override
	public IdentifiableDTO<CityDTO> findById(UUID id) {
		return CityMapper.MapCityPersistenceToCityIdentifiableDTO(cityRepository.getOne(id));
	}

	@Override
	public List<IdentifiableDTO<CityDTO>> findByCountryId(UUID countryId) {
		List<IdentifiableDTO<CityDTO>> cities = new ArrayList<IdentifiableDTO<CityDTO>>();
		cityRepository.findByCountryId(countryId).forEach((c) -> cities.add(CityMapper.MapCityPersistenceToCityIdentifiableDTO(c)));
		return cities;
	}

}
