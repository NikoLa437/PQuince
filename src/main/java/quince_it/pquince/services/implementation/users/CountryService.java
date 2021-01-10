package quince_it.pquince.services.implementation.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.repository.users.CountryRepository;
import quince_it.pquince.services.contracts.dto.users.CountryDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.ICountryService;
import quince_it.pquince.services.implementation.util.CountryMapper;

@Service
public class CountryService implements ICountryService {

	@Autowired
	private CountryRepository countryRepository;

	@Override
	public List<IdentifiableDTO<CountryDTO>> findAll() {
		List<IdentifiableDTO<CountryDTO>> countries = new ArrayList<IdentifiableDTO<CountryDTO>>();
		countryRepository.findAll().forEach((c) -> countries.add(CountryMapper.MapCountryPersistenceToCountryIdentifiableDTO(c)));
		return countries;
	}

	@Override
	public IdentifiableDTO<CountryDTO> findById(UUID id) {
		return CountryMapper.MapCountryPersistenceToCountryIdentifiableDTO(countryRepository.getOne(id));

	}
	
	

}
