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

import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.dto.drugs.CreateOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OrderDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IOrderService;
import quince_it.pquince.services.implementation.drugs.AllergenService;

@RestController
@RequestMapping(value = "api/order")
public class OrderController {

	
	@Autowired
	private IOrderService orderService;
	
	@PostMapping
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> create(@RequestBody CreateOrderDTO createOrderDTO) {
		
		return new ResponseEntity<>(orderService.create(createOrderDTO),HttpStatus.CREATED);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('SUPPLIER')") 
	public ResponseEntity<List<IdentifiableDTO<OrderDTO>>> findAll() {
		return new ResponseEntity<>(orderService.findAll(),HttpStatus.OK);
	}
}
