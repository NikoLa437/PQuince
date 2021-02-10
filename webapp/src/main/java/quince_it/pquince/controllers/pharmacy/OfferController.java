package quince_it.pquince.controllers.pharmacy;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.drugs.AcceptOfferForOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OfferDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IOfferService;

@RestController
@RequestMapping(value = "api/offer")
public class OfferController {
	@Autowired
	private IOfferService offerService;
	
	@PostMapping
	@PreAuthorize("hasRole('SUPPLIER')")
	public ResponseEntity<?> create(@RequestBody OfferDTO offerDTO) {
		
		return new ResponseEntity<>(offerService.create(offerDTO),HttpStatus.CREATED);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('SUPPLIER')") 
	public ResponseEntity<List<IdentifiableDTO<OfferDTO>>> findAll() {
		return new ResponseEntity<>(offerService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("offers-for-order")
	@PreAuthorize("hasRole('PHARMACYADMIN')") 
	public ResponseEntity<List<IdentifiableDTO<OfferDTO>>> findAll(@RequestParam UUID orderId) {
		try {
			List<IdentifiableDTO<OfferDTO>> offers = offerService.findOffersForOrder(orderId);
			
			if(offers!=null) {
				return new ResponseEntity<>(offerService.findOffersForOrder(orderId),HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/accept")
	@CrossOrigin
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> acceptOffer(@RequestBody AcceptOfferForOrderDTO acceptOfferForOrderDTO) {
		
		try {
			offerService.acceptOffer(acceptOfferForOrderDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
