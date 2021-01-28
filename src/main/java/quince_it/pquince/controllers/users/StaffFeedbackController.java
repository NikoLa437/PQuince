package quince_it.pquince.controllers.users;

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

import quince_it.pquince.services.contracts.dto.users.StaffFeedbackDTO;
import quince_it.pquince.services.contracts.interfaces.users.IStaffFeedbackService;

@RestController
@RequestMapping(value = "api/staff/feedback")
public class StaffFeedbackController {

	
	@Autowired
	private IStaffFeedbackService staffFeedbackService;
	
	@GetMapping("/{staffId}")
	public ResponseEntity<StaffFeedbackDTO> findByStaffIdAndPatientId(@PathVariable UUID staffId) {
		try {
			return new ResponseEntity<>(staffFeedbackService.findByStaffIdAndPatientId(staffId, UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")) ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createFeedback(@RequestBody StaffFeedbackDTO staffFeedbackDTO) {
		
		staffFeedbackService.create(staffFeedbackDTO);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping
	@CrossOrigin
	public ResponseEntity<?> updateFeedback(@RequestBody StaffFeedbackDTO staffFeedbackDTO) {
		
		staffFeedbackService.update(staffFeedbackDTO);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
