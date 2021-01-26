package quince_it.pquince.controllers.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.drugs.DrugFeedbackDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationRequestDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugFeedbackService;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugInstanceService;
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
	
	@GetMapping
	public ResponseEntity<List<IdentifiableDTO<DrugInstanceDTO>>> findAll() {
		return new ResponseEntity<>(drugInstanceService.findAll(),HttpStatus.OK);
	}
	
	@PostMapping("/reserve")
	public ResponseEntity<UUID> reserveDrug(@RequestBody DrugReservationRequestDTO drugReservationRequestDTO) {
		
		UUID reservationId = drugReservationService.create(drugReservationRequestDTO);
		
		return new ResponseEntity<>(reservationId ,HttpStatus.CREATED);
	}
	
	//NECE TREBATI ID KAD BUDE ULOGOVAN
	@GetMapping("/future-reservations")
	public ResponseEntity<List<IdentifiableDTO<DrugReservationDTO>>> findAllFutureDrugReservationByPatientId() {
		
		return new ResponseEntity<>(drugReservationService.findAllFutureReservationsByPatientId(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")) ,HttpStatus.OK);
	}
	
	
	@GetMapping("/processed-reservations")
	public ResponseEntity<List<IdentifiableDTO<DrugReservationDTO>>> findProcessedDrugReservationsForPatient() {
		
		return new ResponseEntity<>(drugReservationService.findProcessedDrugReservationsForPatient(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")) ,HttpStatus.OK);
	}
	
	@GetMapping("/feedback/{drugId}")
	public ResponseEntity<DrugFeedbackDTO> findByPatientAndDrug(@PathVariable UUID drugId) {
		try {
			return new ResponseEntity<>(drugFeedbackService.findByPatientAndDrug(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002"), drugId) ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/feedback")
	public ResponseEntity<?> createFeedback(@RequestBody DrugFeedbackDTO drugFeedbackDTO) {
		
		drugFeedbackService.create(drugFeedbackDTO);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/feedback")
	@CrossOrigin
	public ResponseEntity<?> updateFeedback(@RequestBody DrugFeedbackDTO drugFeedbackDTO) {
		
		drugFeedbackService.update(drugFeedbackDTO);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@PutMapping("/reservations/cancel/{reservationId}")
	@CrossOrigin
	public ResponseEntity<?> cancelReservation(@PathVariable UUID reservationId) {
		drugReservationService.cancelDrugReservation(reservationId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
