package quince_it.pquince.controllers.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.users.CountryDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.users.CountryService;

@RestController
@RequestMapping(value = "api/country")
public class CountryController {
	
	@Autowired
	private CountryService countryService;
	
	@GetMapping
	public ResponseEntity<List<IdentifiableDTO<CountryDTO>>> findAll() {
		return new ResponseEntity<>(countryService.findAll(),HttpStatus.OK);
	}
}
