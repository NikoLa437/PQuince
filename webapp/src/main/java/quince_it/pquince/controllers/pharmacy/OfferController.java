package quince_it.pquince.controllers.pharmacy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.drugs.CreateOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OfferDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IOfferService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IOrderService;

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
}
