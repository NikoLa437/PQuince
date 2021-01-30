package quince_it.pquince.controllers.drugs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.drugs.AllergenService;

@RestController
@RequestMapping(value = "api/allergens")
public class AllergenController {

	@Autowired
	private AllergenService allergenService;
	
	@GetMapping
	@PreAuthorize("hasRole('PATIENT')") //or hasRole...
	public ResponseEntity<List<IdentifiableDTO<AllergenDTO>>> findAll() {
		return new ResponseEntity<>(allergenService.findAll(),HttpStatus.OK);
	}
}
