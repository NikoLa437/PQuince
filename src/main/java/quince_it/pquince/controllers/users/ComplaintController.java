package quince_it.pquince.controllers.users;

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

import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFeedbackDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.users.ComplaintPharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.ComplaintStaffDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyComplaintService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyFeedbackService;
import quince_it.pquince.services.contracts.interfaces.users.IComplaintService;


@RestController
@RequestMapping(value = "api/staff/complaint")
public class ComplaintController {


	@Autowired
	private IComplaintService complaintService;
	
	@Autowired
	private IPharmacyComplaintService pharmacyComplaintService;

	@GetMapping
	public ResponseEntity<List<IdentifiableDTO<ComplaintStaffDTO>>> findAll() {
		return new ResponseEntity<>(complaintService.findAll(),HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/{staffId}")
	public ResponseEntity<ComplaintStaffDTO> findByStaffIdAndPatientId(@PathVariable UUID staffId) {
		try {
			return new ResponseEntity<>(complaintService.findByStaffIdAndPatientId(staffId, UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")) ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	
	@PostMapping
	public ResponseEntity<?> createComplaintStuff(@RequestBody ComplaintStaffDTO complaintStaffDTO) {
		
		complaintService.create(complaintStaffDTO);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping
	@CrossOrigin
	public ResponseEntity<?> updateComplaint(@RequestBody ComplaintStaffDTO complaintStaffDTO) {
		
		complaintService.update(complaintStaffDTO);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
