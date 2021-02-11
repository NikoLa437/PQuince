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

import quince_it.pquince.services.contracts.dto.EntityIdDTO;
import quince_it.pquince.services.contracts.dto.drugs.CreateOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugForOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OrderDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.dto.drugs.CreateOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OrderForProviderDTO;
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
		try {
			return new ResponseEntity<>(orderService.create(createOrderDTO),HttpStatus.CREATED);

		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@GetMapping("/find-orders-for-pharmacy")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<List<IdentifiableDTO<OrderDTO>>> findOrdersForPharmacy(@RequestParam UUID pharmacyId) {
		try {
			return new ResponseEntity<>(orderService.findOrdersForPharmacy(pharmacyId),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/remove-order-from-pharmacy")
	@CrossOrigin
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> removeOrder(@RequestBody EntityIdDTO removeOrderId) {
		try {
			if(orderService.removeOrder(removeOrderId))
				return new ResponseEntity<>(HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin
	@GetMapping("/provider")
	@PreAuthorize("hasRole('SUPPLIER')") 
	public ResponseEntity<List<IdentifiableDTO<OrderForProviderDTO>>> findAllForProvider() {
		return new ResponseEntity<>(orderService.findAllProvider(),HttpStatus.OK);
	}
}
