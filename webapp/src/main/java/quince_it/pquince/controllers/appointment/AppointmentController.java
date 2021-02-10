package quince_it.pquince.controllers.appointment;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
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
import quince_it.pquince.services.contracts.dto.appointment.AppointmentCreateDTO;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentPeriodResponseDTO;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentRequestDTO;
import quince_it.pquince.services.contracts.dto.appointment.ConsultationRequestDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentWithPharmacyDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistCreateAppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.NewConsultationDTO;
import quince_it.pquince.services.contracts.dto.appointment.ScheduleAppointmentDTO;
import quince_it.pquince.services.contracts.exceptions.AppointmentNotScheduledException;
import quince_it.pquince.services.contracts.exceptions.AppointmentTimeOverlappingWithOtherAppointmentException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.appointment.IAppointmentService;

@RestController
@RequestMapping(value = "api/appointment")
public class AppointmentController {

	@Autowired
	private IAppointmentService appointmentService;
	
	@PostMapping("/create-appointment-for-dermatologist")
	@CrossOrigin
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> createAppointment(@RequestBody AppointmentCreateDTO appointmentDTO ) {
		try {
			if(appointmentService.createTerminForDermatologist(appointmentDTO)!=null)
				return new ResponseEntity<>(HttpStatus.OK);
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/create-and-schedule-appointment")
	@CrossOrigin
	@PreAuthorize("hasRole('DERMATHOLOGIST')")
	public ResponseEntity<?> createAppointment(@RequestBody DermatologistCreateAppointmentDTO appointmentDTO ) {
		try {
			if(appointmentService.createAndSchuduleAppointment(appointmentDTO)!=null)
				return new ResponseEntity<>(HttpStatus.OK);
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
	
	@GetMapping("/dermatologist/created")
	@PreAuthorize("hasRole('DERMATHOLOGIST')")
	public ResponseEntity<List<IdentifiableDTO<AppointmentDTO>>> getAllAppointmentsByDermatologist() {
		return new ResponseEntity<>(appointmentService.getCreatedAppointmentsByDermatologist(),HttpStatus.OK);
	}
	
	@GetMapping("/dermatologist/calendar-for-pharmacy/{pharmacyId}")
	@PreAuthorize("hasRole('DERMATHOLOGIST')")
	public ResponseEntity<List<IdentifiableDTO<AppointmentDTO>>> getCalendarAppointmentsByDermatologist(@PathVariable UUID pharmacyId) {
		return new ResponseEntity<>(appointmentService.getCalendarAppointmentsByDermatologist(pharmacyId),HttpStatus.OK);
	}
	
	@GetMapping("/pharmacist/calendar/{pharmacyId}")
	@PreAuthorize("hasRole('PHARMACIST')")
	public ResponseEntity<List<IdentifiableDTO<AppointmentDTO>>> getCalendarAppointmentsByPharmacist(@PathVariable UUID pharmacyId) {
		return new ResponseEntity<>(appointmentService.getCalendarAppointmentsByPharmacist(pharmacyId),HttpStatus.OK);
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
	
	@PostMapping("/schedule-appointment")
	@PreAuthorize("hasRole('DERMATHOLOGIST')")
	@CrossOrigin
	public ResponseEntity<?> scheduleAppointment(@RequestBody ScheduleAppointmentDTO scheduleAppointmentDTO) {
		boolean isSuccesfull = appointmentService.scheduleAppointment(scheduleAppointmentDTO.getPatientId(), scheduleAppointmentDTO.getAppointmentId());
		
		if(isSuccesfull) return new ResponseEntity<>(scheduleAppointmentDTO,HttpStatus.CREATED);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/reserve-dermatologist-appointment")
	@PreAuthorize("hasRole('PATIENT')")
	@CrossOrigin
	public ResponseEntity<?> reserveAppointment(@RequestBody EntityIdDTO appointmentId) {
		try {
			appointmentService.reserveAppointment(appointmentId.getId());
			return new ResponseEntity<>(appointmentId,HttpStatus.CREATED);
		} catch (AppointmentTimeOverlappingWithOtherAppointmentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (AuthorizationServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PostMapping("/reserve-appointment")
	@PreAuthorize("hasRole('PATIENT')")
	@CrossOrigin
	public ResponseEntity<?> reserveConsultationAppointment(@RequestBody ConsultationRequestDTO requestDTO) {
		try {
			UUID appointmentId = appointmentService.createConsultation(requestDTO);
			return new ResponseEntity<>(appointmentId, HttpStatus.CREATED);
		} catch (AppointmentTimeOverlappingWithOtherAppointmentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (AuthorizationServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(AppointmentNotScheduledException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
	}
	
	@PostMapping("/pharmacist/new")
	@PreAuthorize("hasRole('PHARMACIST')")
	@CrossOrigin
	public ResponseEntity<?> newConsultationAppointment(@RequestBody NewConsultationDTO newConsultationDTO) {
		try {
			UUID appointmentId = appointmentService.newConsultation(newConsultationDTO);
			return new ResponseEntity<>(appointmentId, HttpStatus.CREATED);
		} catch (AppointmentTimeOverlappingWithOtherAppointmentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (AuthorizationServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
	}
	
	@PutMapping("/cancel-appointment")
	@PreAuthorize("hasRole('PATIENT')")
	@CrossOrigin
	public ResponseEntity<?> cancelAppointment(@RequestBody EntityIdDTO appointmentId) {
		
		try {
			appointmentService.cancelAppointment(appointmentId.getId());
			 return new ResponseEntity<>(appointmentId.getId(),HttpStatus.NO_CONTENT);
		} catch (AuthorizationServiceException e) {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (IllegalArgumentException e) {
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			 return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/patient/{patientId}")
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	public ResponseEntity<List<IdentifiableDTO<AppointmentDTO>>> getAppointmentsByPatientAsStaff(@PathVariable UUID patientId) {
		return new ResponseEntity<>(appointmentService.getAppointmentsByPatientAsStaff(patientId),HttpStatus.OK);
	}
	
	@GetMapping("/{appointmentId}")
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	public ResponseEntity<?> getAppointment(@PathVariable UUID appointmentId) {
		try {
			return new ResponseEntity<>(appointmentService.getAppointment(appointmentId),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/finish")
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	@CrossOrigin
	public ResponseEntity<?> finishAppointment(@RequestBody EntityIdDTO appointmentId) {
		try {
			appointmentService.finishAppointment(appointmentId.getId());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/did-not-show-up")
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	@CrossOrigin
	public ResponseEntity<?> didNotShowUpToAppointment(@RequestBody EntityIdDTO appointmentId) {
		try {
			appointmentService.didNotShowUpToAppointment(appointmentId.getId());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getFreePeriod")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	@CrossOrigin
	public ResponseEntity<List<AppointmentPeriodResponseDTO>> getFreePeriods(@RequestParam UUID dermatologistId,@RequestParam UUID pharmacyId,@RequestParam Date date,@RequestParam int duration) {
		try {
			AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO(dermatologistId,pharmacyId,date,duration, false);
			return new ResponseEntity<>(appointmentService.getFreePeriods(appointmentRequestDTO),HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/free-periods-dermatologist")
	@PreAuthorize("hasRole('DERMATHOLOGIST')")
	@CrossOrigin
	public ResponseEntity<List<AppointmentPeriodResponseDTO>> getFreePeriodsDermatologist(@RequestParam long datetime,@RequestParam int duration) {
		return new ResponseEntity<>(appointmentService.getFreePeriodsDermatologist(new Date(datetime), duration),HttpStatus.OK);
	}
}
