package quince_it.pquince.controllers.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AuthorizationServiceException;
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

import quince_it.pquince.services.contracts.dto.EntityIdDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugFeedbackDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugFormatIdDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugKindIdDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationRequestDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugsWithGradesDTO;
import quince_it.pquince.services.contracts.dto.drugs.IngredientDTO;
import quince_it.pquince.services.contracts.dto.drugs.ManufacturerDTO;
import quince_it.pquince.services.contracts.dto.drugs.ReplaceDrugIdDTO;
import quince_it.pquince.services.contracts.dto.users.DrugManufacturerDTO;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugFeedbackService;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugFormatService;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugInstanceService;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugKindIdService;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugReservationService;


@RestController
@RequestMapping(value = "api/drug")
public class DrugController {

	@Autowired
	private IDrugInstanceService drugInstanceService;

	@Autowired
	private IDrugReservationService drugReservationService;
	
	@Autowired
	private IDrugFeedbackService drugFeedbackService;

	@Autowired
	private IDrugKindIdService drugKindIdService;
	
	@Autowired
	private IDrugFormatService drugFormatService;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<IdentifiableDTO<DrugInstanceDTO>>> findAll() {
		return new ResponseEntity<>(drugInstanceService.findAll(),HttpStatus.OK);
	}


	@PutMapping() 
	@CrossOrigin
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<UUID> addDrugInstance(@RequestBody DrugInstanceDTO drugInstanceDTO) {
		
		UUID drugInstanceId = drugInstanceService.create(drugInstanceDTO);
		
		return new ResponseEntity<>(drugInstanceId ,HttpStatus.CREATED);
	}

	@CrossOrigin
	@GetMapping("/grade") 
	public ResponseEntity<List<IdentifiableDTO<DrugsWithGradesDTO>>> findDrugsWithGrades() {
		return new ResponseEntity<>(drugFeedbackService.findDrugsWithGrades() ,HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/search-drugs") 
	public ResponseEntity<List<IdentifiableDTO<DrugsWithGradesDTO>>> searchDrugs(@RequestParam String name, @RequestParam double gradeFrom, @RequestParam double gradeTo, @RequestParam String drugKind) {
		return new ResponseEntity<>(drugFeedbackService.searchDrugs(name, gradeFrom, gradeTo, drugKind) ,HttpStatus.CREATED);
	}
	
	@PutMapping("/ingredient/{drugId}") 
	@CrossOrigin
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<UUID> addDrugIngredient(@PathVariable UUID drugId, @RequestBody IngredientDTO ingredientDTO) {
		
		UUID drugInstanceId = drugInstanceService.addDrugIngredients(drugId, ingredientDTO);
		
		return new ResponseEntity<>(drugInstanceId ,HttpStatus.CREATED);
	}
	
	@PutMapping("/manufacturer") 
	@CrossOrigin
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<UUID> addDrugManufacturer(@RequestBody DrugManufacturerDTO drugManufacturerDTO) {
		
		UUID drugInstanceId = drugInstanceService.addDrugManufacturer(drugManufacturerDTO.getDrug_id(), drugManufacturerDTO.getManufacturer_id());
		
		return new ResponseEntity<>(drugInstanceId ,HttpStatus.CREATED);
	}
	
	@PutMapping("/replacement") 
	@CrossOrigin
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<UUID> addDrugReplacement(@RequestBody ReplaceDrugIdDTO replaceDrugIdDTO) {
		
		UUID drugInstanceId = drugInstanceService.addDrugReplacement(replaceDrugIdDTO.getId(), replaceDrugIdDTO.getReplacement_id());
		
		return new ResponseEntity<>(drugInstanceId ,HttpStatus.CREATED);
	}

	@CrossOrigin
	@GetMapping("/manufacturers")
	public ResponseEntity<List<IdentifiableDTO<ManufacturerDTO>>> findAllManufacturers() {
		return new ResponseEntity<>(drugInstanceService.findDrugManufacturers(),HttpStatus.OK);
	}
	
	@PostMapping("/reserve")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> reserveDrug(@RequestBody DrugReservationRequestDTO drugReservationRequestDTO) {
		try {
			UUID reservationId = drugReservationService.create(drugReservationRequestDTO);
			return new ResponseEntity<>(reservationId ,HttpStatus.CREATED);
		} catch (ObjectOptimisticLockingFailureException e) {
			return new ResponseEntity<>("Not enough drugs in storage.",HttpStatus.BAD_REQUEST);
		} catch (AuthorizationServiceException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@CrossOrigin
	@GetMapping("/find-drug-by-pharmacy")
	public ResponseEntity<List<IdentifiableDTO<DrugInstanceDTO>>> findDrugsFromPharmacy(@RequestParam UUID pharmacyId) {
		return new ResponseEntity<>(drugInstanceService.findDrugsByPharmacy(pharmacyId),HttpStatus.OK);
	}

	@GetMapping("/future-reservations")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<DrugReservationDTO>>> findAllFutureDrugReservationByPatientId() {
		
		return new ResponseEntity<>(drugReservationService.findAllFutureReservationsByPatientId() ,HttpStatus.OK);
	}
	
	
	@GetMapping("/processed-reservations")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<DrugReservationDTO>>> findProcessedDrugReservationsForPatient() {
		
		return new ResponseEntity<>(drugReservationService.findProcessedDrugReservationsForPatient() ,HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/drugkind")
	public ResponseEntity<List<IdentifiableDTO<DrugKindIdDTO>>> findAllDrugKinds() {
		
		return new ResponseEntity<>(drugKindIdService.findAll() ,HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping("/drugkind")
	public ResponseEntity<?>addDrugKind(@RequestBody DrugKindIdDTO drugKindIdDTO) {
		drugKindIdService.create(drugKindIdDTO);
		return new ResponseEntity<>(HttpStatus.OK); 
	}
	@CrossOrigin
	@GetMapping("/drugformat")
	public ResponseEntity<List<IdentifiableDTO<DrugFormatIdDTO>>> findAllDrugFormats() {
		
		return new ResponseEntity<>(drugFormatService.findAll() ,HttpStatus.OK);
	}
	
	@GetMapping("/feedback/{drugId}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<DrugFeedbackDTO> findByPatientAndDrug(@PathVariable UUID drugId) {
		try {
			return new ResponseEntity<>(drugFeedbackService.findByPatientAndDrug(drugId) ,HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/feedback")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> createFeedback(@RequestBody DrugFeedbackDTO drugFeedbackDTO) {
		try {
			drugFeedbackService.create(drugFeedbackDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (FeedbackNotAllowedException e) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/feedback")
	@CrossOrigin
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> updateFeedback(@RequestBody DrugFeedbackDTO drugFeedbackDTO) {
		try {
			drugFeedbackService.update(drugFeedbackDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/reservations/cancel")
	@CrossOrigin
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> cancelReservation(@RequestBody EntityIdDTO reservationId) {
		try {
			drugReservationService.cancelDrugReservation(reservationId.getId());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (ObjectOptimisticLockingFailureException e) {
			return new ResponseEntity<>("Not enough drugs in storage.",HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
