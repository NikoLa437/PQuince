package quince_it.pquince.controllers.pharmacy;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.drugs.DrugReservationRequestDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFeedbackDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradePriceDTO;
import quince_it.pquince.services.contracts.dto.users.ComplaintPharmacyDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugInstanceService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyComplaintService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyFeedbackService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyService;

@RestController
@RequestMapping(value = "api/pharmacy")
public class PharmacyController {

	@Autowired
	private IPharmacyService pharmacyService;
	
	@Autowired
	private IPharmacyComplaintService pharmacyComplaintService;
	
	@Autowired
	private IDrugInstanceService drugService;
	
	@Autowired
	private IPharmacyFeedbackService pharmacyFeedbackService;
		
	@GetMapping
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAll() {
		return new ResponseEntity<>(pharmacyService.findAllPharmaciesWithGrades(),HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<UUID> addPharmacy(@RequestBody PharmacyDTO pharmacyDTO) {
		
		return new ResponseEntity<>(pharmacyService.create(pharmacyDTO) ,HttpStatus.CREATED);
	}
	
	@GetMapping("/find-by-drug/{drugId}")
	public ResponseEntity<List<IdentifiablePharmacyDrugPriceAmountDTO>> findPharnaciesWithPriceForDrug(@PathVariable UUID drugId) {
		return new ResponseEntity<>(drugService.findByDrugId(drugId),HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/find-by-drug-in-pharmacy")
	public ResponseEntity<IdentifiablePharmacyDrugPriceAmountDTO> findPharnaciesWithPriceForDrug(@RequestParam UUID drugId,@RequestParam UUID pharmacyId) {
		return new ResponseEntity<>(drugService.findByDrugInPharmacy(drugId, pharmacyId),HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@GetMapping("/get-pharmacy-profile")
	public ResponseEntity<IdentifiableDTO<PharmacyGradeDTO>> findPharmacyProfile(@RequestParam UUID pharmacyId) {
		try {
			return new ResponseEntity<>(pharmacyService.findByIdWithGrade(pharmacyId),HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/get-pharmacy-by-appointment-time/{startDateTime}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradePriceDTO>>> findAllPharmaciesFreeForPeriodWithGradesAndPrice(@PathVariable long startDateTime){
	
		try {
			return new ResponseEntity<>(pharmacyService.findAllPharmaciesFreeForPeriodWithGradesAndPrice(new Date(startDateTime)),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/get-pharmacy-by-appointment-time/sort-by-grade-ascending/{startDateTime}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradePriceDTO>>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByGradeAscending(@PathVariable long startDateTime){
	
		try {
			return new ResponseEntity<>(pharmacyService.findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByGradeAscending(new Date(startDateTime)),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/get-pharmacy-by-appointment-time/sort-by-grade-descending/{startDateTime}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradePriceDTO>>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByGradeDescending(@PathVariable long startDateTime){
	
		try {
			return new ResponseEntity<>(pharmacyService.findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByGradeDescending(new Date(startDateTime)),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/get-pharmacy-by-appointment-time/sort-by-price-ascending/{startDateTime}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradePriceDTO>>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByPriceAscending(@PathVariable long startDateTime){
	
		try {
			return new ResponseEntity<>(pharmacyService.findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByPriceAscending(new Date(startDateTime)),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/get-pharmacy-by-appointment-time/sort-by-price-descending/{startDateTime}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradePriceDTO>>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByPriceDescending(@PathVariable long startDateTime){
	
		try {
			return new ResponseEntity<>(pharmacyService.findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByPriceDescending(new Date(startDateTime)),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
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
	
	@GetMapping("/feedback/{pharmacyId}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<PharmacyFeedbackDTO> findByPatientAndPharmacy(@PathVariable UUID pharmacyId) {
		try {
			return new ResponseEntity<>(pharmacyFeedbackService.findByPatientAndPharmacy(pharmacyId) ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/feedback")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> createFeedback(@RequestBody PharmacyFeedbackDTO pharmacyFeedbackDTO) {
		
		pharmacyFeedbackService.create(pharmacyFeedbackDTO);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/complaint-pharmacy")
	public ResponseEntity<?> createFeedback(@RequestBody ComplaintPharmacyDTO complaintPharmacyDTO) {
		
		pharmacyComplaintService.create(complaintPharmacyDTO);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/feedback")
	@CrossOrigin
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> updateFeedback(@RequestBody PharmacyFeedbackDTO pharmacyFeedbackDTO) {
		
		pharmacyFeedbackService.update(pharmacyFeedbackDTO);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/search/sort-by/name-ascending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(@RequestParam String name,@RequestParam String city, @RequestParam double gradeFrom, @RequestParam double gradeTo,
			@RequestParam double distanceFrom, @RequestParam double distanceTo, @RequestParam double latitude, @RequestParam double longitude){
		
		PharmacyFiltrationDTO pharmacyFiltrationDTO = new PharmacyFiltrationDTO(name, city, gradeFrom, gradeTo, distanceFrom, distanceTo, latitude, longitude);
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(pharmacyFiltrationDTO);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/search/sort-by/name-descending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameDescending(@RequestParam String name,@RequestParam String city, @RequestParam double gradeFrom, @RequestParam double gradeTo,
			@RequestParam double distanceFrom, @RequestParam double distanceTo, @RequestParam double latitude, @RequestParam double longitude){
		
		PharmacyFiltrationDTO pharmacyFiltrationDTO = new PharmacyFiltrationDTO(name, city, gradeFrom, gradeTo, distanceFrom, distanceTo, latitude, longitude);
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameDescending(pharmacyFiltrationDTO);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/search/sort-by/city-name-ascending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameAscending(@RequestParam String name,@RequestParam String city, @RequestParam double gradeFrom, @RequestParam double gradeTo,
			@RequestParam double distanceFrom, @RequestParam double distanceTo, @RequestParam double latitude, @RequestParam double longitude){
		
		PharmacyFiltrationDTO pharmacyFiltrationDTO = new PharmacyFiltrationDTO(name, city, gradeFrom, gradeTo, distanceFrom, distanceTo, latitude, longitude);
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameAscending(pharmacyFiltrationDTO);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/search/sort-by/city-name-descending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameDescending(@RequestParam String name,@RequestParam String city, @RequestParam double gradeFrom, @RequestParam double gradeTo,
			@RequestParam double distanceFrom, @RequestParam double distanceTo, @RequestParam double latitude, @RequestParam double longitude){
		
		PharmacyFiltrationDTO pharmacyFiltrationDTO = new PharmacyFiltrationDTO(name, city, gradeFrom, gradeTo, distanceFrom, distanceTo, latitude, longitude);
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameDescending(pharmacyFiltrationDTO);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}

	@GetMapping("/search/sort-by/grade-ascending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeAscending(@RequestParam String name,@RequestParam String city, @RequestParam double gradeFrom, @RequestParam double gradeTo,
			@RequestParam double distanceFrom, @RequestParam double distanceTo, @RequestParam double latitude, @RequestParam double longitude){
		
		PharmacyFiltrationDTO pharmacyFiltrationDTO = new PharmacyFiltrationDTO(name, city, gradeFrom, gradeTo, distanceFrom, distanceTo, latitude, longitude);
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeAscending(pharmacyFiltrationDTO);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/search/sort-by/grade-descending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeDescending(@RequestParam String name,@RequestParam String city, @RequestParam double gradeFrom, @RequestParam double gradeTo,
			@RequestParam double distanceFrom, @RequestParam double distanceTo, @RequestParam double latitude, @RequestParam double longitude){
		
		PharmacyFiltrationDTO pharmacyFiltrationDTO = new PharmacyFiltrationDTO(name, city, gradeFrom, gradeTo, distanceFrom, distanceTo, latitude, longitude);
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeDescending(pharmacyFiltrationDTO);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by/name-ascending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(){
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(null);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by/name-descending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameDescending(){
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameDescending(null);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by/city-name-ascending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameAscending(){
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameAscending(null);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by/city-name-descending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameDescending(){
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameDescending(null);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}

	@GetMapping("/sort-by/grade-ascending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeAscending(){
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeAscending(null);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by/grade-descending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeDescending(){
	
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeDescending(null);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	
}
