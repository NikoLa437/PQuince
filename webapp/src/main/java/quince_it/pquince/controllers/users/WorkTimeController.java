package quince_it.pquince.controllers.users;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

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

import quince_it.pquince.services.contracts.dto.users.WorkTimeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.users.WorkTimeService;

@RestController
@RequestMapping(value = "api/worktime")
public class WorkTimeController {
	
	@Autowired
	private WorkTimeService workTimeService;
	
	@CrossOrigin
	@PostMapping 
	public ResponseEntity<?>addWorkTimeForStaff(@RequestBody WorkTimeDTO workTimeDTO) {
		
		try {
			if(workTimeService.create(workTimeDTO)!=null)
				return new ResponseEntity<>(HttpStatus.OK); 
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/worktime-for-staff/{staffId}") 	
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	@CrossOrigin
	public ResponseEntity<List<IdentifiableDTO<WorkTimeDTO>>> getWorkTimeForStaff(@PathVariable UUID staffId) {
	  
		try {
			List<IdentifiableDTO<WorkTimeDTO>> workTimes = workTimeService.findWorkTimeForStaff(staffId);
			return new ResponseEntity<>(workTimes,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
}
