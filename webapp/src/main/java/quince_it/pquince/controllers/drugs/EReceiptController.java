package quince_it.pquince.controllers.drugs;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.entities.drugs.EReceiptStatus;
import quince_it.pquince.services.contracts.dto.drugs.RefuseReceiptDTO;
import quince_it.pquince.services.contracts.dto.users.PatientAllergicDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IEReceiptService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyService;

@RestController
@RequestMapping(value = "api/ereceipt")
public class EReceiptController {

	@Autowired
	private IEReceiptService eReceiptService;
	
	@Autowired
	private IPharmacyService pharmacyService;
	
	@GetMapping("/for-patient")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(eReceiptService.findAllForPatient(),HttpStatus.OK);
	}
	
	@GetMapping("/for-patient/sort-by-date-ascending")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> findAllSortByDateAscending() {
		return new ResponseEntity<>(eReceiptService.findAllForPatientSortByDateAscending(),HttpStatus.OK);
	}
	
	@GetMapping("/for-patient/sort-by-date-descending")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> findAllSortByDateDescending() {
		return new ResponseEntity<>(eReceiptService.findAllForPatientSortByDateDescending(),HttpStatus.OK);
	}
		
	@GetMapping("/for-patient/search")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> findAllSearch(@RequestParam EReceiptStatus status) {
		return new ResponseEntity<>(eReceiptService.findAllByPatientSearchByStatus(status),HttpStatus.OK);
	}
	
	@GetMapping("/for-patient/sort-by-date-ascending/search")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> findAllSearchSortByDateAscending(@RequestParam EReceiptStatus status) {
		return new ResponseEntity<>(eReceiptService.findAllByPatientSearchByStatusSortByDateAscending(status),HttpStatus.OK);
	}
	
	@GetMapping("/for-patient/sort-by-date-descending/search")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> findAllSearchSortByDateDescending(@RequestParam EReceiptStatus status) {
		return new ResponseEntity<>(eReceiptService.findAllByPatientSearchByStatusSortByDateDescending(status),HttpStatus.OK);
	}
	
	@GetMapping("/processed-drugs")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> findAllProcessedDistinctDrugsByPatientId() {
		return new ResponseEntity<>(eReceiptService.findAllProcessedDistinctDrugsByPatientId(),HttpStatus.OK);
	}
	
	@GetMapping("/can-patient-use/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> canPatientUseEReceipt(@PathVariable UUID id) {
		return new ResponseEntity<>(pharmacyService.canPatientUseQR(id),HttpStatus.OK);
	}
	
	@PostMapping("/refuse") 
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> refuseEReceipt(@RequestBody RefuseReceiptDTO recieptId) {
		try {
			eReceiptService.refuseEReceipt(recieptId.getId());			
			return new ResponseEntity<>(HttpStatus.OK); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PostMapping("/check-if-refused") 
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<PatientAllergicDTO> checkIfRefused(@RequestBody RefuseReceiptDTO recieptId) {
		try {	
			PatientAllergicDTO patientAllergicDTO = new PatientAllergicDTO(eReceiptService.checkIfRefused(recieptId.getId()));
			
			return new ResponseEntity<>(patientAllergicDTO,HttpStatus.OK); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
}
