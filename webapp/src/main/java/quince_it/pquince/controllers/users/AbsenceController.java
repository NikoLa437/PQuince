package quince_it.pquince.controllers.users;



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

import quince_it.pquince.services.contracts.dto.EntityIdDTO;
import quince_it.pquince.services.contracts.dto.users.AbsenceDTO;
import quince_it.pquince.services.contracts.dto.users.AbsenceResponseDTO;
import quince_it.pquince.services.contracts.dto.users.RejectAbsenceDTO;
import quince_it.pquince.services.contracts.dto.users.RequestAbsenceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.users.AbsenceService;


@RestController
@RequestMapping(value = "api/absence")
public class AbsenceController {
	
	@Autowired
	private AbsenceService absenceService;
	
	@PostMapping("/request")
	@CrossOrigin
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	public ResponseEntity<?>requestAbsence(@RequestBody RequestAbsenceDTO requestAbsenceDTO) {
		try {
			absenceService.createAbsence(requestAbsenceDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/forStaff/{staffId}") 
	public ResponseEntity<List<IdentifiableDTO<AbsenceDTO>>>getAbsenceForStaff(@PathVariable UUID staffId) {
	  
		List<IdentifiableDTO<AbsenceDTO>> absences = absenceService.getAbsencesForStaff(staffId);
	  
		return new ResponseEntity<>(absences,HttpStatus.OK); 
	}
	
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	@CrossOrigin
	@GetMapping("/get-absence-for-pharmacy") 
	public ResponseEntity<List<IdentifiableDTO<AbsenceResponseDTO>>>getAbsenceForPharmacy(@RequestParam UUID pharmacyId) {
	  
		List<IdentifiableDTO<AbsenceResponseDTO>> absences = absenceService.getAbsencesForPharmacy(pharmacyId);
	  
		return new ResponseEntity<>(absences,HttpStatus.OK); 
	}
	

	@GetMapping("/auth") 
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	public ResponseEntity<?>checkAuthority() {	  
		return new ResponseEntity<>(HttpStatus.OK); 
	}
	
	
	@PutMapping("/accept-absence") 
	@CrossOrigin
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> acceptAbsence(@RequestBody EntityIdDTO absenceIdDTO ) {
	  
		try {
			if(absenceService.approveAbsence(absenceIdDTO))
				return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PutMapping("/reject-absence") 
	@CrossOrigin
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> rejectAbsence(@RequestBody RejectAbsenceDTO rejectAbsenceDTO ) {
	  
		try {
			absenceService.rejectAbsence(rejectAbsenceDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
}
