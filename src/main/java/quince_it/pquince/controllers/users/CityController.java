package quince_it.pquince.controllers.users;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.users.CityDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.users.CityService;

@RestController
@RequestMapping(value = "api/city")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@GetMapping
	public ResponseEntity<List<IdentifiableDTO<CityDTO>>> findAll() {
		return new ResponseEntity<>(cityService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/filterByCountry/{countryId}")
	public ResponseEntity<List<IdentifiableDTO<CityDTO>>> findByCountryId(@PathVariable UUID countryId) {
		return new ResponseEntity<>(cityService.findByCountryId(countryId),HttpStatus.OK);
	}
}
