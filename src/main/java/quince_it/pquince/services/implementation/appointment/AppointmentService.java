package quince_it.pquince.services.implementation.appointment;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentDTO;
import quince_it.pquince.services.contracts.dto.users.IdentifiableStaffGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.appointment.IAppointmentService;
import quince_it.pquince.services.contracts.interfaces.users.IStaffFeedbackService;
import quince_it.pquince.services.implementation.util.appointment.AppointmentMapper;

public class AppointmentService implements IAppointmentService{

	@Autowired
	private IStaffFeedbackService staffFeedbackService;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Override
	public List<IdentifiableDTO<AppointmentDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdentifiableDTO<AppointmentDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(AppointmentDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(AppointmentDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IdentifiableDTO<AppointmentDTO>> findAllFreeAppointmentsByPharmacyAndAppointmentType(UUID pharmacyId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllFreeAppointmentsByPharmacyAndAppointmentType(pharmacyId, appointmentType);
		List<IdentifiableStaffGradeDTO> staffWithGrades = staffFeedbackService.findAllStaffWithAvgGradeByStaffType(StaffType.DERMATOLOGIST);
		
		List<IdentifiableDTO<AppointmentDTO>> returnAppointments = AppointmentMapper.MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(appointments, staffWithGrades);
		
		return returnAppointments;
	}

}
