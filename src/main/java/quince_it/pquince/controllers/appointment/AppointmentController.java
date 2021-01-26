package quince_it.pquince.controllers.appointment;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentWithPharmacyDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.appointment.IAppointmentService;

@RestController
@RequestMapping(value = "api/appointment")
public class AppointmentController {

	
	@Autowired
	private IAppointmentService appointmentService;
	
	@GetMapping("/dermatologist/pending/find-by-patient")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>>> findAllFuturePatientsAppointments() {
		//TODO : URADITI SA ULOGOVANIM
		
		return new ResponseEntity<>(appointmentService.findAllFutureAppointmentsForPatient(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002"),AppointmentType.EXAMINATION),HttpStatus.OK);
	}
	
	@GetMapping("/dermatologist/find-by-pharmacy/{pharmacyId}")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllFreeAppointmentsByPharmacyAndAppointmentType(@PathVariable UUID pharmacyId) {
		return new ResponseEntity<>(appointmentService.findAllFreeAppointmentsByPharmacyAndAppointmentType(pharmacyId, AppointmentType.EXAMINATION),HttpStatus.OK);
	}
	
	@GetMapping("/dermatologist/find-by-pharmacy/sort-by-price-ascending/{pharmacyId}")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(@PathVariable UUID pharmacyId) {
		return new ResponseEntity<>(appointmentService.findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(pharmacyId, AppointmentType.EXAMINATION),HttpStatus.OK);
	}
	
	@GetMapping("/dermatologist/find-by-pharmacy/sort-by-price-descending/{pharmacyId}")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(@PathVariable UUID pharmacyId) {
		return new ResponseEntity<>(appointmentService.findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(pharmacyId, AppointmentType.EXAMINATION),HttpStatus.OK);
	}
	
	@GetMapping("/dermatologist/find-by-pharmacy/sort-by-grade-ascending/{pharmacyId}")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeAscending(@PathVariable UUID pharmacyId) {
		return new ResponseEntity<>(appointmentService.findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeAscending(pharmacyId, AppointmentType.EXAMINATION),HttpStatus.OK);
	}
	
	@GetMapping("/dermatologist/find-by-pharmacy/sort-by-grade-descending/{pharmacyId}")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeDescending(@PathVariable UUID pharmacyId) {
		return new ResponseEntity<>(appointmentService.findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeDescending(pharmacyId, AppointmentType.EXAMINATION),HttpStatus.OK);
	}
	
	@PostMapping("/reserve-appointment/{appointmentId}")
	@CrossOrigin
	public ResponseEntity<?> reserveAppointment(@PathVariable UUID appointmentId) {
		//TODO : URADITI SA ULOGOVANIM
		boolean isSuccesfull = appointmentService.reserveAppointment(appointmentId, UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002"));
		
		if(isSuccesfull) return new ResponseEntity<>(appointmentId,HttpStatus.CREATED);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/cancel-appointment/{appointmentId}")
	@CrossOrigin
	public ResponseEntity<?> cancelAppointment(@PathVariable UUID appointmentId) {
		
		
		//TODO : PROVERA JEL PACIJENTOV APPOINTMENT
		boolean isSuccesfull = appointmentService.cancelAppointment(appointmentId);
		
		if(isSuccesfull) return new ResponseEntity<>(appointmentId,HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
