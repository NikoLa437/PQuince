package quince_it.pquince.services.implementation.util.appointment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentDTO;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentWithPharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.IdentifiableStaffGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class AppointmentMapper {

	
	public static IdentifiableDTO<DermatologistAppointmentDTO> MapAppointmentPersistenceToAppointmentIdentifiableDTO(Appointment appointment, IdentifiableStaffGradeDTO staff){
		if(appointment == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<DermatologistAppointmentDTO>(appointment.getId(), new DermatologistAppointmentDTO(staff, appointment.getStartDateTime(), appointment.getEndDateTime(), appointment.getPrice()));
	}
	
	public static List<IdentifiableDTO<DermatologistAppointmentDTO>> MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(List<Appointment> appointments, List<IdentifiableStaffGradeDTO> staffs){
		
		List<IdentifiableDTO<DermatologistAppointmentDTO>> appointmentListDTO = new ArrayList<IdentifiableDTO<DermatologistAppointmentDTO>>();
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
	
	public static IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO> MapAppointmentPersistenceToAppointmentWithPharmacyIdentifiableDTO
					(Appointment appointment, IdentifiableStaffGradeDTO staff){
		if(appointment == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>(appointment.getId(),
																			new DermatologistAppointmentWithPharmacyDTO(staff,
																			appointment.getStartDateTime(),
																			appointment.getEndDateTime(),
																			appointment.getPrice(),
																			new IdentifiableDTO<PharmacyDTO>(appointment.getPharmacy().getId(),
																					new PharmacyDTO(appointment.getPharmacy().getName(),
																									appointment.getPharmacy().getAddress(),
																									appointment.getPharmacy().getDescription()))));
	}

	public static List<IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>> MapAppointmentPersistenceListToAppointmentWithPharmacyIdentifiableDTOList(
			List<Appointment> appointments, List<IdentifiableStaffGradeDTO> staffWithGrades) {

		List<IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>> appointmentListDTO = new ArrayList<IdentifiableDTO<DermatologistAppointmentWithPharmacyDTO>>();
		appointments.forEach((a) -> appointmentListDTO.add(MapAppointmentPersistenceToAppointmentWithPharmacyIdentifiableDTO(a, findAppropriateStaff(a.getStaff().getId(), staffWithGrades))));

		return appointmentListDTO;
	}

	public static List<IdentifiableDTO<AppointmentDTO>> MapAppointmentPersistenceListToAppointmentIdentifiableDTOList(
			List<Appointment> appointments) {
		
		List<IdentifiableDTO<AppointmentDTO>> appointmentDTOList = new ArrayList<IdentifiableDTO<AppointmentDTO>>();
		appointments.forEach((a) -> appointmentDTOList.add(MapAppointmentPersistenceToAppointmentIdentifiableDTO(a)));

		return appointmentDTOList;
	}
	
	public static IdentifiableDTO<AppointmentDTO> MapAppointmentPersistenceToAppointmentIdentifiableDTO(Appointment appointment){
		if(appointment == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<AppointmentDTO>(appointment.getId(), new AppointmentDTO(appointment.getStartDateTime(), appointment.getEndDateTime(), appointment.getPrice()));
	}
}
