package quince_it.pquince.controllers.drugs;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.entities.drugs.EReceiptStatus;
import quince_it.pquince.services.contracts.interfaces.drugs.IEReceiptService;

@RestController
@RequestMapping(value = "api/ereceipt")
public class EReceiptController {

	@Autowired
	private IEReceiptService eReceiptService;
	
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
}
