package quince_it.pquince.services.contracts.interfaces.appointment;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentWithPharmacyDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IAppointmentService extends IService<DermatologistAppointmentDTO, IdentifiableDTO<DermatologistAppointmentDTO>> {

	List<IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>> findAllFutureAppointmentsForPatient(UUID patientId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentType(UUID pharmacyId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(UUID pharmacyId, AppointmentType appointmentType);

	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(UUID pharmacyId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeAscending(UUID pharmacyId, AppointmentType appointmentType);

	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeDescending(UUID pharmacyId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatient(UUID patientId, AppointmentType appointmentType);

	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByDateAscending(UUID patientId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByDateDescending(UUID patientId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByPriceAscending(UUID patientId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByPriceDescending(UUID patientId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByTimeAscending(UUID patientId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllPreviousAppointmentsForPatientSortByTimeDescending(UUID patientId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<AppointmentDTO>> getCreatedAppointmentsByDermatologist(UUID dermatologistId);
	
	boolean reserveAppointment(UUID appointmentId, UUID patientId);
	
	boolean cancelAppointment(UUID appointmentId);

	List<IdentifiableDTO<DermatologistAppointmentDTO>> getDermatologistAppointmentsByPatient(UUID patientId);

}
