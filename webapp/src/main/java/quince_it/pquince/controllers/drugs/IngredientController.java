package quince_it.pquince.controllers.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.drugs.IngredientDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.drugs.IngredientService;

@RestController
@RequestMapping(value = "api/ingredients")
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;

	@CrossOrigin
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('SYSADMIN')")
	public ResponseEntity<UUID> create(@RequestBody IngredientDTO entityDTO) {
		return new ResponseEntity<>(ingredientService.create(entityDTO), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<IdentifiableDTO<IngredientDTO>>> findAll() {
		return new ResponseEntity<>(ingredientService.findAll(),HttpStatus.OK);
	}

}
