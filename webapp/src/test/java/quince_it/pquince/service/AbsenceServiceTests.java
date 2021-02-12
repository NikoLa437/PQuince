package quince_it.pquince.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static quince_it.pquince.constants.Constants.ABSENCE_ID_FOR_APPROVE;
import static quince_it.pquince.constants.Constants.ABSENCE_ID_FOR_REJECT;
import static quince_it.pquince.constants.Constants.DERMATOLOGIST_ABSENCE_START_DATE_TIME;
import static quince_it.pquince.constants.Constants.DERMATOLOGIST_ABSENCE_END_DATE_TIME;
import static quince_it.pquince.constants.Constants.PHARMACY_ID;
import static quince_it.pquince.constants.Constants.DERMATOLOGIST_ID;


import java.util.ArrayList;
import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.users.Absence;
import quince_it.pquince.entities.users.AbsenceStatus;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.users.AbsenceRepository;
import quince_it.pquince.services.contracts.dto.EntityIdDTO;
import quince_it.pquince.services.contracts.dto.users.RejectAbsenceDTO;
import quince_it.pquince.services.implementation.users.AbsenceService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.util.TestUtil;


@SpringBootTest
public class AbsenceServiceTests {
	@Mock
	private AbsenceRepository absenceRepository;
	
	@Mock
	private AppointmentRepository appointmentRepository;
	
	@InjectMocks
	private AbsenceService absenceService;
	
	@Mock
	private EmailService emailService;
	
	@Test
	public void testAcceptAbsence() throws MessagingException {
		Absence absence = TestUtil.GetAbsenceForApprove();
		when(absenceRepository.getOne(ABSENCE_ID_FOR_APPROVE)).thenReturn(absence);
		when(appointmentRepository.findAllAppointmentByPharmacyAndDoctorForDateRange(DERMATOLOGIST_ABSENCE_START_DATE_TIME, DERMATOLOGIST_ABSENCE_END_DATE_TIME, PHARMACY_ID, DERMATOLOGIST_ID)).thenReturn(new ArrayList<Appointment>());
		
		boolean retVal = absenceService.approveAbsence(new EntityIdDTO(ABSENCE_ID_FOR_APPROVE));
		
        assertThat(retVal).isEqualTo(true);
        assertThat(absence.getAbsenceStatus()).isEqualTo(AbsenceStatus.ACCEPTED);
	}
	
	@Test
	public void testRejectAbsence() throws MessagingException {
		Absence absence = TestUtil.GetAbsenceForApprove();
		when(absenceRepository.getOne(ABSENCE_ID_FOR_REJECT)).thenReturn(absence);
		when(appointmentRepository.findAllAppointmentByPharmacyAndDoctorForDateRange(DERMATOLOGIST_ABSENCE_START_DATE_TIME, DERMATOLOGIST_ABSENCE_END_DATE_TIME, PHARMACY_ID, DERMATOLOGIST_ID)).thenReturn(TestUtil.getAppointmentsForAbsenceReject());
		
		absenceService.rejectAbsence(new RejectAbsenceDTO(ABSENCE_ID_FOR_REJECT,"Any reason"));
		
        assertThat(absence.getAbsenceStatus()).isEqualTo(AbsenceStatus.REJECTED);
        assertThat(absence.getRejectReason()).isEqualTo("Any reason");
	}
}
