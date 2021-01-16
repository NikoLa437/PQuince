package quince_it.pquince.controllers.drugs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugInstanceService;


@RestController
@RequestMapping(value = "api/drug")
public class DrugController {

	@Autowired
	private IDrugInstanceService drugInstanceService;
	
	@GetMapping
	public ResponseEntity<List<IdentifiableDTO<DrugInstanceDTO>>> findAll() {
		return new ResponseEntity<>(drugInstanceService.findAll(),HttpStatus.OK);
	}
}
