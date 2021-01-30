package quince_it.pquince.controllers.users;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.users.LoyaltyProgramDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.users.LoyaltyProgramService;

@RestController
@RequestMapping(value = "api/loyaltyProgram")
public class LoyaltyProgramController {

	@Autowired
	private LoyaltyProgramService loyaltyProgramService;
	
	@GetMapping("/{loyaltyProgramId}")
	@CrossOrigin
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<IdentifiableDTO<LoyaltyProgramDTO>> getLoyaltyProgramById(@PathVariable UUID loyaltyProgramId) {
		
		try {
				
			IdentifiableDTO<LoyaltyProgramDTO> loyaltyProgram = loyaltyProgramService.findById(loyaltyProgramId);
			return new ResponseEntity<>(loyaltyProgram,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PutMapping("/{loyaltyProgramId}") 
	@CrossOrigin
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> updateLoyaltyProgramInformation(@PathVariable UUID loyaltyProgramId,@RequestBody LoyaltyProgramDTO loyaltyProgramDTO ) {
	  
		try {
			loyaltyProgramService.update(loyaltyProgramDTO, loyaltyProgramId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
}
