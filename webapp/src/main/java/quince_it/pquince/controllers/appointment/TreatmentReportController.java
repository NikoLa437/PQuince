package quince_it.pquince.controllers.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.services.contracts.dto.appointment.TreatmentReportDTO;
import quince_it.pquince.services.contracts.interfaces.appointment.ITreatmentReportService;

@RestController
@RequestMapping(value = "api/treatment-report")
public class TreatmentReportController {
	@Autowired
	private ITreatmentReportService treatmentReportService;
	
	@PostMapping
	@CrossOrigin
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	public ResponseEntity<?> createTreatmentReport(@RequestBody TreatmentReportDTO treatmentReportDTO){
		try {
			if(treatmentReportService.create(treatmentReportDTO) != null)
				return new ResponseEntity<>(HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
