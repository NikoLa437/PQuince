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

import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.dto.drugs.AllergenUserDTO;
import quince_it.pquince.services.contracts.dto.users.PatientDTO;
import quince_it.pquince.services.contracts.dto.users.UserDTO;
import quince_it.pquince.services.contracts.dto.users.UserInfoChangeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.drugs.AllergenService;
import quince_it.pquince.services.implementation.users.UserService;

@RestController
@RequestMapping(value = "api/users")
public class UsersController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AllergenService allergenService;
	
	@GetMapping("/activate-patient/{patientId}")
	public ResponseEntity<?> activatePatient(@PathVariable UUID patientId) {
		
		if (userService.activatePatientAccount(patientId))
			return new ResponseEntity<>(HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	@GetMapping("/{userId}") 
	public ResponseEntity<IdentifiableDTO<UserDTO>>getUserById(@PathVariable UUID userId) {
	  
		IdentifiableDTO<UserDTO> user = userService.findById(userId);
	  
		return new ResponseEntity<>(user,HttpStatus.OK); 
	}
	
	@PutMapping("/{patientId}") 
	@CrossOrigin
	public ResponseEntity<?>updatePatientInformation(@PathVariable UUID patientId,@RequestBody UserInfoChangeDTO userInfoChangeDTO ) {
	  
		userService.updatePatient(patientId, userInfoChangeDTO);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	
	
	@GetMapping("/patient/{patientId}") 
	public ResponseEntity<IdentifiableDTO<PatientDTO>>getPatientById(@PathVariable UUID patientId) {
	  
		IdentifiableDTO<PatientDTO> patient = userService.getPatientById(patientId);
	  
		return new ResponseEntity<>(patient,HttpStatus.OK); 
	}
	
	
	@GetMapping("/patient-allergens/{patientId}") 
	public ResponseEntity<List<IdentifiableDTO<AllergenDTO>>>getAllergensForPatient(@PathVariable UUID patientId) {
	  
		List<IdentifiableDTO<AllergenDTO>> allergens = allergenService.getPatientAllergens(patientId);
		
		return new ResponseEntity<>(allergens,HttpStatus.OK); 
	}
	
	@PostMapping("/patient-allergens") 
	public ResponseEntity<?>addAllergensForPatient(@RequestBody AllergenUserDTO allergenUserDTO) {
	  
		userService.addAllergen(allergenUserDTO);
		
		return new ResponseEntity<>(HttpStatus.OK); 
	}
	
	@PutMapping("/patient-allergens") 
	@CrossOrigin
	public ResponseEntity<?>deleteAllergensForPatient(@RequestBody AllergenUserDTO allergenUserDTO) {
	  
		userService.removeAllergen(allergenUserDTO);
		
		return new ResponseEntity<>(HttpStatus.OK); 
	}
	
}
