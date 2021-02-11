package quince_it.pquince.mapper;

import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.appointment.AppointmentStatus;
import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.util.appointment.AppointmentMapper;
import java.util.Date;


import static quince_it.pquince.constants.Constants.APPOINTMENT_START;
import static quince_it.pquince.constants.Constants.APPOINTMENT_END;
import static quince_it.pquince.constants.Constants.APPOINTMENT_ID;


@SpringBootTest
public class AppointmentMapperTests {
	
	@Mock
	private Patient patientMock;
	@Mock
	private Staff staffMock;
	@Mock
	private Pharmacy pharmacyMock;
	
	@Test
	public void testMapAppointmentPersistenceToAppointmentIdentifiableDTO() {
		Appointment appointment = new Appointment(APPOINTMENT_ID, pharmacyMock, staffMock, patientMock, new Date(APPOINTMENT_START), new Date(APPOINTMENT_END), 1000, AppointmentType.EXAMINATION, AppointmentStatus.SCHEDULED);
		
		IdentifiableDTO<AppointmentDTO> identifiableAppointmentDTO = AppointmentMapper.MapAppointmentPersistenceToAppointmentIdentifiableDTO(appointment);
		
		assertThat(identifiableAppointmentDTO.Id).isEqualTo(appointment.getId());
		assertThat(identifiableAppointmentDTO.EntityDTO.getStartDateTime()).isEqualTo(appointment.getStartDateTime());
		assertThat(identifiableAppointmentDTO.EntityDTO.getEndDateTime()).isEqualTo(appointment.getEndDateTime());
		assertThat(identifiableAppointmentDTO.EntityDTO.getPrice()).isEqualTo(appointment.getPrice());
		assertThat(identifiableAppointmentDTO.EntityDTO.getAppointmentStatus()).isEqualTo(appointment.getAppointmentStatus());
	}
	
	@Test
	public void testMapAppointmentPersistenceToAppointmentIdentifiableDTOWhenNullSent() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			AppointmentMapper.MapAppointmentPersistenceToAppointmentIdentifiableDTO(null);
		});
	}
}
