package quince_it.pquince.services.implementation.util.appointment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentDTO;
import quince_it.pquince.services.contracts.dto.users.IdentifiableStaffGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class AppointmentMapper {

	
	public static IdentifiableDTO<AppointmentDTO> MapAppointmentPersistenceToAppointmentIdentifiableDTO(Appointment appointment, IdentifiableStaffGradeDTO staff){
		if(appointment == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<AppointmentDTO>(appointment.getId(), new AppointmentDTO(staff, appointment.getStartDateTime(), appointment.getEndDateTime(), appointment.getPrice()));
	}
	
	public static List<IdentifiableDTO<AppointmentDTO>> MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(List<Appointment> appointments, List<IdentifiableStaffGradeDTO> staffs){
		
		List<IdentifiableDTO<AppointmentDTO>> appointmentListDTO = new ArrayList<IdentifiableDTO<AppointmentDTO>>();
		appointments.forEach((a) -> appointmentListDTO.add(MapAppointmentPersistenceToAppointmentIdentifiableDTO(a, findAppropriateStaff(a.getStaff().getId(), staffs))));
		return appointmentListDTO;
	}
	
	public static IdentifiableStaffGradeDTO findAppropriateStaff(UUID staffId, List<IdentifiableStaffGradeDTO> staffs) {
		for(IdentifiableStaffGradeDTO staff : staffs) {
			if(staff.Id.equals(staffId)) 
				return staff;
		}
		
		return null;
	}
}
