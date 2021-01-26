package quince_it.pquince.services.contracts.interfaces.appointment;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IAppointmentService extends IService<DermatologistAppointmentDTO, IdentifiableDTO<DermatologistAppointmentDTO>> {

	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentType(UUID pharmacyId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(UUID pharmacyId, AppointmentType appointmentType);

	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(UUID pharmacyId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeAscending(UUID pharmacyId, AppointmentType appointmentType);

	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeDescending(UUID pharmacyId, AppointmentType appointmentType);

	boolean reserveAppointment(UUID appointmentId, UUID patientId);
}
