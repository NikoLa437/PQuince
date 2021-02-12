package quince_it.pquince.mapper;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.WorkTime;
import quince_it.pquince.services.contracts.dto.users.WorkTimeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.util.appointment.AppointmentMapper;
import quince_it.pquince.services.implementation.util.users.WorkTimeMapper;

import static quince_it.pquince.constants.Constants.WORKTIME_ID;
import static quince_it.pquince.constants.Constants.WORKTIME_START;
import static quince_it.pquince.constants.Constants.WORKTIME_END;

@SpringBootTest
public class WorkTimeMapperTests {
	@Mock
	private Staff staffMock;
	@Mock
	private Pharmacy pharmacyMock;
	@Test
	public void testMapWorkTimePersistenceToWorkTimeIdentifiableDTO() {
		WorkTime workTime = new WorkTime(WORKTIME_ID, staffMock, new Date(WORKTIME_START), new Date(WORKTIME_END), 9, 17, pharmacyMock);
		
		IdentifiableDTO<WorkTimeDTO> workTimeDTO = WorkTimeMapper.MapWorkTimePersistenceToWorkTimeIdentifiableDTO(workTime);
		
		assertThat(workTimeDTO.Id).isEqualTo(workTime.getId());
		assertThat(workTimeDTO.EntityDTO.getStartDate()).isEqualTo(workTime.getStartDate());
		assertThat(workTimeDTO.EntityDTO.getEndDate()).isEqualTo(workTime.getEndDate());
		assertThat(workTimeDTO.EntityDTO.getStartTime()).isEqualTo(workTime.getStartTime());
		assertThat(workTimeDTO.EntityDTO.getEndTime()).isEqualTo(workTime.getEndTime());
	}
	
	@Test
	public void testMapAppointmentPersistenceToAppointmentIdentifiableDTOWhenNullSent() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			WorkTimeMapper.MapWorkTimePersistenceToWorkTimeIdentifiableDTO(null);
		});
	}
}
