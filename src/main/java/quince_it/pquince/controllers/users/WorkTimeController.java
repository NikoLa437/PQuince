package quince_it.pquince.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.users.AbsenceDTO;
import quince_it.pquince.services.contracts.dto.users.WorkTimeDTO;
import quince_it.pquince.services.implementation.users.AbsenceService;
import quince_it.pquince.services.implementation.users.WorkTimeService;

@RestController
@RequestMapping(value = "api/worktime")
public class WorkTimeController {
	
	@Autowired
	private WorkTimeService workTimeService;
	
	@CrossOrigin
	@PostMapping 
	public ResponseEntity<?>addAllergensForPatient(@RequestBody WorkTimeDTO workTimeDTO) {
		workTimeService.create(workTimeDTO);
		return new ResponseEntity<>(HttpStatus.OK); 
	}
}
