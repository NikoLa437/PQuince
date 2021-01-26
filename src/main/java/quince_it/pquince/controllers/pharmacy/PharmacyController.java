package quince_it.pquince.controllers.pharmacy;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.drugs.DrugReservationRequestDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugInstanceService;
import quince_it.pquince.services.implementation.pharmacy.PharmacyService;

@RestController
@RequestMapping(value = "api/pharmacy")
public class PharmacyController {

	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private IDrugInstanceService drugService;
		
	@GetMapping
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAll() {
		return new ResponseEntity<>(pharmacyService.findAllPharmaciesWithGrades(),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<UUID> addPharmacy(@RequestBody PharmacyDTO pharmacyDTO) {
		
		return new ResponseEntity<>(pharmacyService.create(pharmacyDTO) ,HttpStatus.CREATED);
	}
	
	@GetMapping("/find-by-drug/{drugId}")
	public ResponseEntity<List<IdentifiablePharmacyDrugPriceAmountDTO>> findPharnaciesWithPriceForDrug(@PathVariable UUID drugId) {
		return new ResponseEntity<>(drugService.findByDrugId(drugId),HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/get-pharmacy-profile")
	public ResponseEntity<IdentifiableDTO<PharmacyDTO>> findPharmacyProfile(@RequestParam UUID pharmacyId) {
		return new ResponseEntity<>(pharmacyService.findById(pharmacyId),HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findByNameGradeAndDistance(@RequestParam String name,@RequestParam String city, @RequestParam double gradeFrom, @RequestParam double gradeTo,
			@RequestParam double distanceFrom, @RequestParam double distanceTo, @RequestParam double latitude, @RequestParam double longitude) {
		
		try {
			PharmacyFiltrationDTO pharmacyFiltrationDTO = new PharmacyFiltrationDTO(name, city, gradeFrom, gradeTo, distanceFrom, distanceTo, latitude, longitude);
			List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findByNameGradeAndDistance(pharmacyFiltrationDTO);
			
			return new ResponseEntity<>(pharmacies, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
