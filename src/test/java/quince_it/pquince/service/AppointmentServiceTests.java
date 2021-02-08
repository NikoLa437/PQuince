package quince_it.pquince.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static quince_it.pquince.constants.Constants.ABSENCE_END_DATE;
import static quince_it.pquince.constants.Constants.ABSENCE_START_DATE;
import static quince_it.pquince.constants.Constants.ACTION_ID;
import static quince_it.pquince.constants.Constants.APPOINTMENT_END_DATE_TIME;
import static quince_it.pquince.constants.Constants.APPOINTMENT_START_DATE_TIME;
import static quince_it.pquince.constants.Constants.PATIENT_ID;
import static quince_it.pquince.constants.Constants.PHARMACY_ID;
import static quince_it.pquince.constants.Constants.STAFF_ID;
import static quince_it.pquince.constants.Constants.WORKTIME_END_DATE;
import static quince_it.pquince.constants.Constants.WORKTIME_START_DATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.appointment.AppointmentStatus;
import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.pharmacy.ActionAndPromotionType;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Absence;
import quince_it.pquince.entities.users.Address;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.WorkTime;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.pharmacy.ActionAndPromotionsRepository;
import quince_it.pquince.repository.users.AbsenceRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.PharmacistRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.repository.users.WorkTimeRepository;
import quince_it.pquince.services.contracts.exceptions.AppointmentNotScheduledException;
import quince_it.pquince.services.contracts.exceptions.AppointmentTimeOverlappingWithOtherAppointmentException;
import quince_it.pquince.services.contracts.interfaces.users.ILoyaltyProgramService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;
import quince_it.pquince.services.implementation.appointment.AppointmentService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.util.TestUtil;

@SpringBootTest
public class AppointmentServiceTests {

	@Mock
	private AppointmentRepository appointmentRepository;
	
	@Mock
	private WorkTimeRepository workTimeRepository;
	
	@Mock
	private AbsenceRepository absenceRepository;
	
	@Mock
	private StaffRepository staffRepository;
	
	@Mock
	private PatientRepository patientRepository;
	
	@Mock
	private PharmacistRepository pharmacistRepository;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private ILoyaltyProgramService loyalityProgramService;
	
	@Mock
	private ActionAndPromotionsRepository actionAndPromotionsRepository;
	
	@Mock
	private IUserService userService;
	
	@Mock
	private Pharmacy pharmacyMock;
	
	@Mock
	private Staff staffMock;
	
	@Mock
	private Patient patientMock;
	
	@Mock
	private Address addressMock;
	
	@Mock
	private Environment env;
	
	@InjectMocks
	private AppointmentService appointmentService;
	

	
	@SuppressWarnings("deprecation")
	@Test
	public void testFindAllDistinctPharmaciesForAppointmentTime() {
		
		List<WorkTime> wt = new ArrayList<WorkTime>();
		wt.add(new WorkTime(pharmacyMock,  TestUtil.getPharmacistAsStaff(STAFF_ID, addressMock), WORKTIME_START_DATE, WORKTIME_END_DATE, 8, 17));
		
		
		when(absenceRepository.findAbsenceByStaffIdAndDate(STAFF_ID,APPOINTMENT_START_DATE_TIME)).thenReturn(new ArrayList<Absence>());
		when(workTimeRepository.findWorkTimesByDesiredConsultationTime(TestUtil.getEndDate(APPOINTMENT_END_DATE_TIME), TestUtil.getHours(APPOINTMENT_END_DATE_TIME), APPOINTMENT_START_DATE_TIME.getHours())).thenReturn(wt);
		when(appointmentRepository.findAllConsultationsByAppointmentTime(APPOINTMENT_START_DATE_TIME, APPOINTMENT_END_DATE_TIME)).thenReturn(new ArrayList<Appointment>());

		List<Pharmacy> pharmacies = appointmentService.findAllDistinctPharmaciesForAppointmentTime(APPOINTMENT_START_DATE_TIME, APPOINTMENT_END_DATE_TIME);
		
		assertThat(pharmacies).hasSize(1);
		
		verify(appointmentRepository, times(1)).findAllConsultationsByAppointmentTime(APPOINTMENT_START_DATE_TIME, APPOINTMENT_END_DATE_TIME);
        verifyNoMoreInteractions(appointmentRepository);
        
        verify(workTimeRepository, times(1)).findWorkTimesByDesiredConsultationTime(TestUtil.getEndDate(APPOINTMENT_END_DATE_TIME), TestUtil.getHours(APPOINTMENT_END_DATE_TIME), APPOINTMENT_START_DATE_TIME.getHours());
        verifyNoMoreInteractions(workTimeRepository);
        
        verify(absenceRepository, times(1)).findAbsenceByStaffIdAndDate(STAFF_ID,APPOINTMENT_START_DATE_TIME);
        verifyNoMoreInteractions(absenceRepository);
	}
	

	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testFindAllDistinctPharmaciesForAppointmentTimeWhenPhramacistOnAbsence() {
		
		List<WorkTime> wt = new ArrayList<WorkTime>();
		wt.add(new WorkTime(pharmacyMock, TestUtil.getPharmacistAsStaff(STAFF_ID, addressMock) , WORKTIME_START_DATE, WORKTIME_END_DATE, 8, 17));
		
		List<Absence> ab = new ArrayList<Absence>();
		ab.add(new Absence( TestUtil.getPharmacistAsStaff(STAFF_ID, addressMock), ABSENCE_START_DATE, ABSENCE_END_DATE));
		
		when(absenceRepository.findAbsenceByStaffIdAndDate(STAFF_ID,APPOINTMENT_START_DATE_TIME)).thenReturn(ab);
		when(workTimeRepository.findWorkTimesByDesiredConsultationTime(TestUtil.getEndDate(APPOINTMENT_END_DATE_TIME), TestUtil.getHours(APPOINTMENT_END_DATE_TIME), APPOINTMENT_START_DATE_TIME.getHours())).thenReturn(wt);
		when(appointmentRepository.findAllConsultationsByAppointmentTime(APPOINTMENT_START_DATE_TIME, APPOINTMENT_END_DATE_TIME)).thenReturn(new ArrayList<Appointment>());

		List<Pharmacy> pharmacies = appointmentService.findAllDistinctPharmaciesForAppointmentTime(APPOINTMENT_START_DATE_TIME, APPOINTMENT_END_DATE_TIME);
		
		assertThat(pharmacies).hasSize(0);
		
		verify(appointmentRepository, times(1)).findAllConsultationsByAppointmentTime(APPOINTMENT_START_DATE_TIME, APPOINTMENT_END_DATE_TIME);
        verifyNoMoreInteractions(appointmentRepository);
        
        verify(workTimeRepository, times(1)).findWorkTimesByDesiredConsultationTime(TestUtil.getEndDate(APPOINTMENT_END_DATE_TIME), TestUtil.getHours(APPOINTMENT_END_DATE_TIME), APPOINTMENT_START_DATE_TIME.getHours());
        verifyNoMoreInteractions(workTimeRepository);
        
        verify(absenceRepository, times(1)).findAbsenceByStaffIdAndDate(STAFF_ID,APPOINTMENT_START_DATE_TIME);
        verifyNoMoreInteractions(absenceRepository);

	}

	
	@Test
	@Transactional
	public void testCreateConsultation() throws AppointmentNotScheduledException, AppointmentTimeOverlappingWithOtherAppointmentException {
		
		int dbSizeBeforeAdd = ((List<Appointment>) appointmentRepository.findAll()).size();
		
		when(appointmentRepository.findAllAppointmentsByAppointmentTimeAndPatient(APPOINTMENT_START_DATE_TIME,APPOINTMENT_END_DATE_TIME, PATIENT_ID)).thenReturn(new ArrayList<Appointment>());
		when(env.getProperty("max_penalty_count")).thenReturn("3");
		when(env.getProperty("consultation_time")).thenReturn("15");
		when(appointmentRepository.findAllConsultationsByAppointmentTime(APPOINTMENT_START_DATE_TIME, APPOINTMENT_END_DATE_TIME)).thenReturn(new ArrayList<Appointment>());
		when(workTimeRepository.findWorkTimeByDesiredConsultationTimeAndPharmacistId(TestUtil.getEndDate(APPOINTMENT_END_DATE_TIME), TestUtil.getHours(APPOINTMENT_END_DATE_TIME), STAFF_ID)).thenReturn(new WorkTime());
		when(absenceRepository.findAbsenceByStaffIdAndDate(STAFF_ID,APPOINTMENT_START_DATE_TIME)).thenReturn(new ArrayList<Absence>());
		when(staffRepository.findById(STAFF_ID)).thenReturn(Optional.of(TestUtil.getPharmacistAsStaff(STAFF_ID, addressMock)));
		when(pharmacistRepository.findPharmacyByPharmacistId(STAFF_ID)).thenReturn(TestUtil.getPharmacy(PHARMACY_ID, addressMock));
		when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.of(TestUtil.getPatient(PATIENT_ID, addressMock)));
		when(userService.getLoggedUserId()).thenReturn(PATIENT_ID);
		when(loyalityProgramService.getDiscountAppointmentPriceForPatient(420, AppointmentType.CONSULTATION, PATIENT_ID)).thenReturn(420.0);
		when(actionAndPromotionsRepository.findCurrentActionAndPromotionForPharmacyForActionType(PHARMACY_ID, ActionAndPromotionType.CONSULTATIONDISCOUNT))
									.thenReturn(TestUtil.getActionAndPromotions(ACTION_ID));
		when(appointmentRepository.findAll()).thenReturn(
									TestUtil.getAppointment(pharmacyMock, staffMock, patientMock, APPOINTMENT_START_DATE_TIME, APPOINTMENT_END_DATE_TIME, AppointmentStatus.SCHEDULED, AppointmentType.CONSULTATION, 378.0));

		appointmentService.createConsultation(TestUtil.getConsultationRequestDTO(STAFF_ID, APPOINTMENT_START_DATE_TIME));
		
		List<Appointment> appointments = (List<Appointment>) appointmentRepository.findAll();
        assertThat(appointments).hasSize(dbSizeBeforeAdd + 1); 
        
        Appointment appointment = appointments.get(appointments.size() - 1);
        
        assertThat(appointment.getAppointmentStatus()).isEqualTo(AppointmentStatus.SCHEDULED);
        assertThat(appointment.getAppointmentType()).isEqualTo(AppointmentType.CONSULTATION);
        assertThat(appointment.getStartDateTime()).isEqualTo(APPOINTMENT_START_DATE_TIME);
        assertThat(appointment.getEndDateTime()).isEqualTo(APPOINTMENT_END_DATE_TIME);
        assertThat(appointment.getPrice()).isEqualTo(378.0);
        assertThat(appointment.getPriceToPay()).isEqualTo(378.0);

	}
	

	
}
