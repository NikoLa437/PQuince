package quince_it.pquince.services.contracts.interfaces.appointment;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.AuthorizationServiceException;

import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentCreateDTO;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentPeriodResponseDTO;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentRequestDTO;
import quince_it.pquince.services.contracts.dto.appointment.ConsultationRequestDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentWithPharmacyDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistCreateAppointmentDTO;
import quince_it.pquince.services.contracts.dto.users.RemoveDermatologistFromPharmacyDTO;
import quince_it.pquince.services.contracts.exceptions.AlreadyBeenScheduledConsultationException;
import quince_it.pquince.services.contracts.exceptions.AppointmentNotScheduledException;
import quince_it.pquince.services.contracts.exceptions.AppointmentTimeOverlappingWithOtherAppointmentException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IAppointmentService extends IService<DermatologistAppointmentDTO, IdentifiableDTO<DermatologistAppointmentDTO>> {

	List<IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>> findAllFutureAppointmentsForPatient(AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentType(UUID pharmacyId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(UUID pharmacyId, AppointmentType appointmentType);

	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(UUID pharmacyId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeAscending(UUID pharmacyId, AppointmentType appointmentType);

	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeDescending(UUID pharmacyId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatient(AppointmentType appointmentType);

	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByDateAscending(AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByDateDescending(AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByPriceAscending(AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByPriceDescending(AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByTimeAscending(AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByTimeDescending(AppointmentType appointmentType);
	
	List<IdentifiableDTO<AppointmentDTO>> getCreatedAppointmentsByDermatologist();
	
	void reserveAppointment(UUID appointmentId) throws AppointmentTimeOverlappingWithOtherAppointmentException;
	
	void cancelAppointment(UUID appointmentId) throws AuthorizationServiceException;

	List<IdentifiableDTO<AppointmentDTO>> getDermatologistAppointmentsByPatient(UUID patientId);
	
	List<Pharmacy> findAllDistinctPharmaciesForAppointmentTime(Date startDateTime, Date endDateTime);

	List<Staff> findAllDistinctPharmacistsForAppointmentTimeForPharmacy(Date startDateTime, Date endDateTime, UUID pharmacyId);
	
	UUID createConsultation(ConsultationRequestDTO requestDTO) throws AppointmentNotScheduledException, AlreadyBeenScheduledConsultationException, AppointmentTimeOverlappingWithOtherAppointmentException;

	List<AppointmentPeriodResponseDTO> getFreePeriods(AppointmentRequestDTO appointmentRequestDTO);
	
	List<AppointmentPeriodResponseDTO> getFreePeriodsDermatologist(Date date, int duration);

	UUID createTerminForDermatologist(AppointmentCreateDTO appointmentDTO);

	boolean hasAppointmentInFuture(RemoveDermatologistFromPharmacyDTO removeDermatologistFromPharmacyDTO);

	boolean scheduleAppointment(UUID patientId, UUID appointmentId);

	List<IdentifiableDTO<AppointmentDTO>> getCalendarAppointmentsByDermatologist(UUID pharmacyId);
	
	UUID createAndSchuduleAppointment(DermatologistCreateAppointmentDTO appointmentDTO);

	IdentifiableDTO<AppointmentDTO> getAppointment(UUID appointmentId);

	void finishAppointment(UUID id);
}
