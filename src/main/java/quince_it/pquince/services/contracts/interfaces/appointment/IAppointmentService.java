package quince_it.pquince.services.contracts.interfaces.appointment;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IAppointmentService extends IService<AppointmentDTO, IdentifiableDTO<AppointmentDTO>> {

	List<IdentifiableDTO<AppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentType(UUID pharmacyId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<AppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(UUID pharmacyId, AppointmentType appointmentType);

	List<IdentifiableDTO<AppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(UUID pharmacyId, AppointmentType appointmentType);
	
	List<IdentifiableDTO<AppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeAscending(UUID pharmacyId, AppointmentType appointmentType);

	List<IdentifiableDTO<AppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByGradeDescending(UUID pharmacyId, AppointmentType appointmentType);

	boolean reserveAppointment(UUID appointmentId, UUID patientId);
}
