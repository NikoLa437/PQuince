package quince_it.pquince.controllers.pharmacy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.pharmacy.PharmacyService;

@RestController
@RequestMapping(value = "api/pharmacy")
public class PharmacyController {

	@Autowired
	private PharmacyService pharmacyService;
		
	@GetMapping
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAll() {
		return new ResponseEntity<>(pharmacyService.findAllPharmaciesWithGrades(),HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findByNameGradeAndDistance(@RequestParam String name, @RequestParam double gradeFrom, @RequestParam double gradeTo,
			@RequestParam double distanceFrom, @RequestParam double distanceTo) {
		
		PharmacyFiltrationDTO pharmacyFiltrationDTO = new PharmacyFiltrationDTO(name, gradeFrom, gradeTo, distanceFrom, distanceTo);
		
		return new ResponseEntity<>(pharmacyService.findByNameGradeAndDistance(pharmacyFiltrationDTO),HttpStatus.OK);
	}
}
