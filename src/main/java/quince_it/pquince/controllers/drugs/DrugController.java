package quince_it.pquince.controllers.drugs;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationRequestDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugInstanceService;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugReservationService;


@RestController
@RequestMapping(value = "api/drug")
public class DrugController {

	@Autowired
	private IDrugInstanceService drugInstanceService;
	
	@Autowired
	private IDrugReservationService drugReservationService;
	
	@GetMapping
	public ResponseEntity<List<IdentifiableDTO<DrugInstanceDTO>>> findAll() {
		return new ResponseEntity<>(drugInstanceService.findAll(),HttpStatus.OK);
	}
	
	@PostMapping("/reserve")
	public ResponseEntity<UUID> reserveDrug(@RequestBody DrugReservationRequestDTO drugReservationRequestDTO) {
		
		UUID reservationId = drugReservationService.create(drugReservationRequestDTO);
		
		return new ResponseEntity<>(reservationId ,HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/find-drug-by-pharmacy")
	public ResponseEntity<List<IdentifiableDTO<DrugInstanceDTO>>> findDrugsFromPharmacy(@RequestParam UUID pharmacyId) {
		return new ResponseEntity<>(drugInstanceService.findDrugsByPharmacy(pharmacyId),HttpStatus.OK);
	}
}
