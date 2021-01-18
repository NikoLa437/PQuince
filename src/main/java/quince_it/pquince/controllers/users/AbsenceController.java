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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.users.AbsenceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.users.AbsenceService;


@RestController
@RequestMapping(value = "api/absence")
public class AbsenceController {
	
	@Autowired
	private AbsenceService absenceService;
	
	@CrossOrigin
	@PostMapping 
	public ResponseEntity<?>addAllergensForPatient(@RequestBody AbsenceDTO absenceDTO) {
		absenceService.createAbsence(absenceDTO);
		return new ResponseEntity<>(HttpStatus.OK); 
	}
	
	@GetMapping("/forStaff/{staffId}") 
	public ResponseEntity<List<IdentifiableDTO<AbsenceDTO>>>getAbsenceForStaff(@PathVariable UUID staffId) {
	  
		List<IdentifiableDTO<AbsenceDTO>> absences = absenceService.getAbsencesForStaff(staffId);
	  
		return new ResponseEntity<>(absences,HttpStatus.OK); 
	}
}
