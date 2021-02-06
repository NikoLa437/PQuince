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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.users.AbsenceDTO;
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
	
	
	
	@GetMapping("/auth") 
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	public ResponseEntity<?>checkAuthority() {	  
		return new ResponseEntity<>(HttpStatus.OK); 
	}
}
