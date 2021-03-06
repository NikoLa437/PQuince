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

import quince_it.pquince.entities.pharmacy.IncomeStatistics;
import quince_it.pquince.entities.pharmacy.PharmacyIncomeStatistics;
import quince_it.pquince.services.contracts.dto.drugs.PharmacyERecipeDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.ActionAndPromotionsDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.DateRangeDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.DrugsStatisticsDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.EditPharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.ExaminationsStatisticsDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IncomeStatisticsDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDrugPriceDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFeedbackDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradePriceDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyIncomeStatisticsDTO;
import quince_it.pquince.services.contracts.dto.users.ComplaintPharmacyDTO;
import quince_it.pquince.services.contracts.exceptions.ComplaintsNotAllowedException;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugInstanceService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IActionAndPromotionsService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyComplaintService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyFeedbackService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;

@RestController
@RequestMapping(value = "api/pharmacy")
public class PharmacyController {

	@Autowired
	private IPharmacyService pharmacyService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPharmacyComplaintService pharmacyComplaintService;
	
	@Autowired
	private IDrugInstanceService drugService;
	
	@Autowired
	private IPharmacyFeedbackService pharmacyFeedbackService;
	
	@Autowired
	private IActionAndPromotionsService actionAndPromotionService;
	
	@GetMapping
	@CrossOrigin
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAll() {
		try {
			return new ResponseEntity<>(pharmacyService.findAllPharmaciesWithGrades(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/qrpharmacies/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyDrugPriceDTO>>> findWithQR(@PathVariable UUID id) {
		
		return new ResponseEntity<List<IdentifiableDTO<PharmacyDrugPriceDTO>>>(pharmacyService.findWithQR(id),HttpStatus.OK);
		
	}
	
	@GetMapping("/has-qrpharmacies")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> findIfPharmacyHasQR(@RequestParam UUID pharamcyId,@RequestParam UUID qrID) {
		
		if(pharmacyService.findIfPharmacyHasQRCode(pharamcyId,qrID)) {
			return new ResponseEntity<>(HttpStatus.OK); 
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 

	}
	
	@PostMapping("/qrpharmacies/buy")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> buyWithQR(@RequestBody PharmacyERecipeDTO pharmacyERecipeDTO) {
		
		return new ResponseEntity<>(pharmacyService.buyWithQR(pharmacyERecipeDTO),HttpStatus.CREATED);
		
	}
	
	@GetMapping("/subscribed")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllSubscribedPharmacies() {
		try {
			return new ResponseEntity<>(pharmacyService.findAllSubscribedPharmaciesWithGrades(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	@PreAuthorize("hasRole('SYSADMIN')")
	public ResponseEntity<UUID> addPharmacy(@RequestBody PharmacyDTO pharmacyDTO) {
		
		return new ResponseEntity<>(pharmacyService.create(pharmacyDTO) ,HttpStatus.CREATED);
	}

	@GetMapping("/find-by-drug/{drugId}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiablePharmacyDrugPriceAmountDTO>> findPharmaciesWithPriceForDrug(@PathVariable UUID drugId) {
		try {
			return new ResponseEntity<>(drugService.findByDrugId(drugId),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@GetMapping("/find-by-drug-in-pharmacy")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<IdentifiablePharmacyDrugPriceAmountDTO> findPharmaciesWithPriceForDrug(@RequestParam UUID drugId,@RequestParam UUID pharmacyId) {
		try {
			return new ResponseEntity<>(drugService.findByDrugInPharmacy(drugId, pharmacyId),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
	
	@GetMapping("/get-pharmacy-with-price/{pharmacyId}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<IdentifiableDTO<PharmacyGradePriceDTO>> findPharmacyByPharmacyId(@PathVariable UUID pharmacyId){
	
		try {
			return new ResponseEntity<>(pharmacyService.findPharmacyByPharmacyId(pharmacyId),HttpStatus.OK);
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
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
		
		try {
			pharmacyFeedbackService.create(pharmacyFeedbackDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (FeedbackNotAllowedException e) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/complaint-pharmacy")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> createComplaint(@RequestBody ComplaintPharmacyDTO complaintPharmacyDTO) {
		
		try {
			pharmacyComplaintService.create(complaintPharmacyDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (ComplaintsNotAllowedException e) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/feedback")
	@CrossOrigin
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> updateFeedback(@RequestBody PharmacyFeedbackDTO pharmacyFeedbackDTO) {
		
		try {
			pharmacyFeedbackService.update(pharmacyFeedbackDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/search/sort-by/name-ascending")
	public ResponseEntity<List<IdentifiableDTO<PharmacyGradeDTO>>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(@RequestParam String name,@RequestParam String city, @RequestParam double gradeFrom, @RequestParam double gradeTo,
			@RequestParam double distanceFrom, @RequestParam double distanceTo, @RequestParam double latitude, @RequestParam double longitude){
		
		PharmacyFiltrationDTO pharmacyFiltrationDTO = new PharmacyFiltrationDTO(name, city, gradeFrom, gradeTo, distanceFrom, distanceTo, latitude, longitude);
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = pharmacyService.findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(pharmacyFiltrationDTO);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by-qr/name-ascending/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyDrugPriceDTO>>> findQrPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(@PathVariable UUID id){
	
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.findQrPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by-qr/name-descending/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyDrugPriceDTO>>> findQrPharmaciesWithGradesByNameGradeAndDistanceSortByNameDescending(@PathVariable UUID id){
	
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.findQrPharmaciesWithGradesByNameGradeAndDistanceSortByNameDescending(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by-qr/city-name-ascending/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyDrugPriceDTO>>> findQrPharmaciesWithGradesByCityAscending(@PathVariable UUID id){
	
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.findQrPharmaciesWithGradesByCityAscending(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by-qr/city-name-descending/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyDrugPriceDTO>>> findQrPharmaciesWithGradesByCityDescending(@PathVariable UUID id){
	
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.findQrPharmaciesWithGradesByCityDescending(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by-qr/grade-ascending/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyDrugPriceDTO>>> findQrPharmaciesWithGradesByGradeAscending(@PathVariable UUID id){
	
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.findQrPharmaciesWithGradesByGradeAscending(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by-qr/grade-descending/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyDrugPriceDTO>>> findQrPharmaciesWithGradesByGradeDescending(@PathVariable UUID id){
	
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.findQrPharmaciesWithGradesByGradeDescending(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by-qr/price-ascending/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyDrugPriceDTO>>> findQrPharmaciesWithGradesByPriceAscending(@PathVariable UUID id){
	
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.findQrPharmaciesWithGradesByPriceAscending(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/sort-by-qr/price-descending/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<PharmacyDrugPriceDTO>>> findQrPharmaciesWithGradesByPriceDescending(@PathVariable UUID id){
	
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.findQrPharmaciesWithGradesByPriceDescending(id);
		
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
	
	@PutMapping
	@CrossOrigin
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> update(@RequestBody EditPharmacyDTO editPharmacyDTO) {
		try {
			pharmacyService.updatePharmacy(editPharmacyDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	@PostMapping("/action-and-promotions")
	@CrossOrigin
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> createActionAndPromotions(@RequestBody ActionAndPromotionsDTO actionAndPromotions) {
		try {

			if(actionAndPromotionService.create(actionAndPromotions))
				return new ResponseEntity<>(HttpStatus.CREATED);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@GetMapping("/find-action-and-promotion-by-pharmacy")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<List<IdentifiableDTO<ActionAndPromotionsDTO>>> findActionAndPromotionsByPharmacy() {
		try {
			return new ResponseEntity<>(actionAndPromotionService.findActionAndPromotionsInPharmacy(),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@GetMapping("/find-statistics-for-examinations-and-consultations")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<ExaminationsStatisticsDTO> findStatisticsForExaminationsAndColsutations() {
		try {
			return new ResponseEntity<>(pharmacyService.findStatisticsForExaminationsAndColsutations(),HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@GetMapping("/find-statistics-for-drugs")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<DrugsStatisticsDTO> findStatisticsForDrugs() {
		try {
			return new ResponseEntity<>(pharmacyService.findStatisticsForDrugs(),HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@GetMapping("/find-income-statistics-for-pharmacy/{startDateTime}/{endDateTime}")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<PharmacyIncomeStatistics> findIncomeStatisticsForPharmacy(@PathVariable long startDateTime,@PathVariable long endDateTime) {
		try {
			return new ResponseEntity<>(pharmacyService.findIncomeStatisticsForPharmacy(new Date(startDateTime),new Date(endDateTime)),HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
