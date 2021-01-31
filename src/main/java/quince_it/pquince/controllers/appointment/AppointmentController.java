package quince_it.pquince.controllers.appointment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.services.contracts.dto.EntityIdDTO;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentPeriodResponseDTO;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentRequestDTO;
import quince_it.pquince.services.contracts.dto.appointment.ConsultationRequestDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentWithPharmacyDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugFeedbackDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFeedbackDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.appointment.IAppointmentService;

@RestController
@RequestMapping(value = "api/appointment")
public class AppointmentController {

	@Autowired
	private IAppointmentService appointmentService;
	
	@PostMapping("/create-appointment")
	@CrossOrigin
	public ResponseEntity<?> createAppointment(@RequestBody DermatologistAppointmentDTO dermatologistAppointmentDTO ) {
		
		appointmentService.create(dermatologistAppointmentDTO);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/dermatologist/pending/find-by-patient")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>>> findAllFuturePatientsAppointments() {
		
		return new ResponseEntity<>(appointmentService.findAllFutureAppointmentsForPatient(AppointmentType.EXAMINATION),HttpStatus.OK);
	}
	
	@GetMapping("/pharmacist/pending/find-by-patient")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>>> findAllFuturePatientsConsultations() {
		
		return new ResponseEntity<>(appointmentService.findAllFutureAppointmentsForPatient(AppointmentType.CONSULTATION),HttpStatus.OK);
	}
	
	@GetMapping("/dermatologist-history")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllPreviousAppointmentsForPatient() {

		return new ResponseEntity<>(appointmentService.findAllPreviousAppointmentsForPatient(AppointmentType.EXAMINATION),HttpStatus.OK);
	}
	
	@GetMapping("/pharmacist-history")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllPreviousConsultationsForPatient() {

		return new ResponseEntity<>(appointmentService.findAllPreviousAppointmentsForPatient(AppointmentType.CONSULTATION),HttpStatus.OK);
	}
	
	@GetMapping("/appointment-history/sort-by-price-ascending")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllPreviousAppointmentsForPatientSortByPriceAscending(@RequestParam AppointmentType appointmentType) {

		return new ResponseEntity<>(appointmentService.findAllPreviousAppointmentsForPatientSortByPriceAscending(appointmentType),HttpStatus.OK);
	}
	
	@GetMapping("/appointment-history/sort-by-price-descending")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllPreviousAppointmentsForPatientSortByPriceDescending(@RequestParam AppointmentType appointmentType) {

		return new ResponseEntity<>(appointmentService.findAllPreviousAppointmentsForPatientSortByPriceDescending(appointmentType),HttpStatus.OK);
	}
	
	@GetMapping("/appointment-history/sort-by-date-descending")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllPreviousAppointmentsForPatientSortByDateDescending(@RequestParam AppointmentType appointmentType) {

		return new ResponseEntity<>(appointmentService.findAllPreviousAppointmentsForPatientSortByDateDescending(appointmentType),HttpStatus.OK);
	}
	
	@GetMapping("/appointment-history/sort-by-date-ascending")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllPreviousAppointmentsForPatientSortByDateAscending(@RequestParam AppointmentType appointmentType) {

		return new ResponseEntity<>(appointmentService.findAllPreviousAppointmentsForPatientSortByDateAscending(appointmentType),HttpStatus.OK);
	}
	
	@GetMapping("/appointment-history/sort-by-time-ascending")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllPreviousAppointmentsForPatientSortByTimeAscending(@RequestParam AppointmentType appointmentType) {

		return new ResponseEntity<>(appointmentService.findAllPreviousAppointmentsForPatientSortByTimeAscending(appointmentType),HttpStatus.OK);
	}
	
	@GetMapping("/appointment-history/sort-by-time-descending")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<IdentifiableDTO<DermatologistAppointmentDTO>>> findAllPreviousAppointmentsForPatientSortByTimeDescending(@RequestParam AppointmentType appointmentType) {

		return new ResponseEntity<>(appointmentService.findAllPreviousAppointmentsForPatientSortByTimeDescending(appointmentType),HttpStatus.OK);
	}
	
	@GetMapping("/dermatologist/{dermatologistId}")
	public ResponseEntity<List<IdentifiableDTO<AppointmentDTO>>> getAllAppointmentsByDermatologist(@PathVariable UUID dermatologistId) {
		return new ResponseEntity<>(appointmentService.getCreatedAppointmentsByDermatologist(dermatologistId),HttpStatus.OK);
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
	
	@PostMapping("/reserve-dermatologist-appointment")
	@PreAuthorize("hasRole('PATIENT')")
	@CrossOrigin
	public ResponseEntity<?> reserveAppointment(@RequestBody EntityIdDTO appointmentId) {
		boolean isSuccesfull = appointmentService.reserveAppointment(appointmentId.getId());
		
		if(isSuccesfull) return new ResponseEntity<>(appointmentId,HttpStatus.CREATED);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/reserve-appointment")
	@PreAuthorize("hasRole('PATIENT')")
	@CrossOrigin
	public ResponseEntity<UUID> reserveConsultationAppointment(@RequestBody ConsultationRequestDTO requestDTO) {
		try {
			UUID appointmentId = appointmentService.createConsultation(requestDTO);
			return new ResponseEntity<>(appointmentId, HttpStatus.CREATED);
		} catch (Exception e) {
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@PutMapping("/cancel-appointment")
	@PreAuthorize("hasRole('PATIENT')")
	@CrossOrigin
	public ResponseEntity<?> cancelAppointment(@RequestBody EntityIdDTO appointmentId) {
		
		
		//TODO : PROVERA JEL PACIJENTOV APPOINTMENT
		boolean isSuccesfull = appointmentService.cancelAppointment(appointmentId.getId());
		
		if(isSuccesfull) return new ResponseEntity<>(appointmentId.getId(),HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<List<IdentifiableDTO<AppointmentDTO>>> getAppointmentsByPatient(@PathVariable UUID patientId) {
		return new ResponseEntity<>(appointmentService.getDermatologistAppointmentsByPatient(patientId),HttpStatus.OK);
	}
	
	@GetMapping("/getFreePeriod")
	@CrossOrigin
	public ResponseEntity<List<AppointmentPeriodResponseDTO>> getFreePeriods(@RequestParam UUID dermatologistId,@RequestParam UUID pharmacyId,@RequestParam Date date,@RequestParam int duration) {
		AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO(dermatologistId,pharmacyId,date,duration, false);
		return new ResponseEntity<>(appointmentService.getFreePeriods(appointmentRequestDTO),HttpStatus.OK);
	}
}
