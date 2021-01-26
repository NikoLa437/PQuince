package quince_it.pquince.controllers.users;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.dto.drugs.AllergenUserDTO;
import quince_it.pquince.services.contracts.dto.users.DermatologistForPharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.users.IdentifiableDermatologistForPharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
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
		
		try {
			if (userService.activatePatientAccount(patientId))
				return new ResponseEntity<>(HttpStatus.OK);
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	@GetMapping("/{userId}") 
	public ResponseEntity<IdentifiableDTO<UserDTO>> getUserById(@PathVariable UUID userId) {
		
		try {
			IdentifiableDTO<UserDTO> user = userService.findById(userId);
			return new ResponseEntity<>(user,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@GetMapping("/search") 
	public ResponseEntity<List<IdentifiableDTO<UserDTO>>> findByNameAndSurname(@RequestParam String name,@RequestParam String surname) {
		
		try {
			List<IdentifiableDTO<UserDTO>> users = userService.findByNameAndSurname(name, surname);
			return new ResponseEntity<>(users,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PutMapping("/{patientId}") 
	@CrossOrigin
	public ResponseEntity<?> updatePatientInformation(@PathVariable UUID patientId,@RequestBody UserInfoChangeDTO userInfoChangeDTO ) {
	  
		try {
			userService.updatePatient(patientId, userInfoChangeDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	@GetMapping("/patient/{patientId}") 
	public ResponseEntity<IdentifiableDTO<PatientDTO>> getPatientById(@PathVariable UUID patientId) {
	  
		try {
			IdentifiableDTO<PatientDTO> patient = userService.getPatientById(patientId);
			return new ResponseEntity<>(patient,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} 
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	@GetMapping("/patient-allergens/{patientId}") 
	public ResponseEntity<List<IdentifiableDTO<AllergenDTO>>> getAllergensForPatient(@PathVariable UUID patientId) {
	  
		try {
			List<IdentifiableDTO<AllergenDTO>> allergens = allergenService.getPatientAllergens(patientId);
			return new ResponseEntity<>(allergens,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PostMapping("/patient-allergens") 
	public ResponseEntity<?> addAllergensForPatient(@RequestBody AllergenUserDTO allergenUserDTO) {
	  
		try {
			if(userService.addAllergen(allergenUserDTO))
				return new ResponseEntity<>(HttpStatus.OK); 
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PutMapping("/patient-allergens") 
	@CrossOrigin
	public ResponseEntity<?> deleteAllergensForPatient(@RequestBody AllergenUserDTO allergenUserDTO) {
		
		try {
			if(userService.removeAllergen(allergenUserDTO))
				return new ResponseEntity<>(HttpStatus.OK); 
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@GetMapping("/dermatologist-for-pharmacy/{pharmacyId}") 
	@CrossOrigin
	public ResponseEntity<List<IdentifiableDermatologistForPharmacyGradeDTO>> getDermatologistForPharmacy(@PathVariable UUID pharmacyId) {
	  
		try {
			List<IdentifiableDermatologistForPharmacyGradeDTO> dermatologist = userService.findAllDermatologistForPharmacy(pharmacyId);
			return new ResponseEntity<>(dermatologist,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
}
