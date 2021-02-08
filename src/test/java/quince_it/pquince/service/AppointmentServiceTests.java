package quince_it.pquince.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
		
		Date startDateTime = new Date(2021, 2, 15, 14, 2);
		Date endDateTime = new Date(2021, 2, 15, 14, 17);
		UUID staffId = UUID.fromString("25345278-52d3-11eb-ae93-0242ac130002");

		List<WorkTime> wt = new ArrayList<WorkTime>();
		wt.add(new WorkTime(pharmacyMock,  TestUtil.getPharmacistAsStaff(staffId, addressMock), new Date(2021, 2, 3, 14, 2), new Date(2021, 4, 15, 14, 2), 8, 17));
		
		
		when(absenceRepository.findAbsenceByStaffIdAndDate(staffId,startDateTime)).thenReturn(new ArrayList<Absence>());
		when(workTimeRepository.findWorkTimesByDesiredConsultationTime(TestUtil.getEndDate(endDateTime), TestUtil.getHours(endDateTime), startDateTime.getHours())).thenReturn(wt);
		when(appointmentRepository.findAllConsultationsByAppointmentTime(startDateTime, endDateTime)).thenReturn(new ArrayList<Appointment>());

		List<Pharmacy> pharmacies = appointmentService.findAllDistinctPharmaciesForAppointmentTime(startDateTime, endDateTime);
		
		assertThat(pharmacies).hasSize(1);
		
		verify(appointmentRepository, times(1)).findAllConsultationsByAppointmentTime(startDateTime, endDateTime);
        verifyNoMoreInteractions(appointmentRepository);
        
        verify(workTimeRepository, times(1)).findWorkTimesByDesiredConsultationTime(TestUtil.getEndDate(endDateTime), TestUtil.getHours(endDateTime), startDateTime.getHours());
        verifyNoMoreInteractions(workTimeRepository);
        
        verify(absenceRepository, times(1)).findAbsenceByStaffIdAndDate(staffId,startDateTime);
        verifyNoMoreInteractions(absenceRepository);
	}
	

	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testFindAllDistinctPharmaciesForAppointmentTimeWhenPhramacistOnAbsence() {
		
		Date startDateTime = new Date(2021, 2, 15, 14, 2);
		Date endDateTime = new Date(2021, 2, 15, 14, 17);
		UUID staffId = UUID.fromString("25345278-52d3-11eb-ae93-0242ac130002");

		List<WorkTime> wt = new ArrayList<WorkTime>();
		wt.add(new WorkTime(pharmacyMock, TestUtil.getPharmacistAsStaff(staffId, addressMock) , new Date(2021, 2, 3, 14, 2), new Date(2021, 4, 15, 14, 2), 8, 17));
		
		List<Absence> ab = new ArrayList<Absence>();
		ab.add(new Absence( TestUtil.getPharmacistAsStaff(staffId, addressMock), new Date(2021, 2, 13, 0, 0), new Date(2021, 2, 18, 0, 0)));
		
		when(absenceRepository.findAbsenceByStaffIdAndDate(staffId,startDateTime)).thenReturn(ab);
		when(workTimeRepository.findWorkTimesByDesiredConsultationTime(TestUtil.getEndDate(endDateTime), TestUtil.getHours(endDateTime), startDateTime.getHours())).thenReturn(wt);
		when(appointmentRepository.findAllConsultationsByAppointmentTime(startDateTime, endDateTime)).thenReturn(new ArrayList<Appointment>());

		List<Pharmacy> pharmacies = appointmentService.findAllDistinctPharmaciesForAppointmentTime(startDateTime, endDateTime);
		
		assertThat(pharmacies).hasSize(0);
		
		verify(appointmentRepository, times(1)).findAllConsultationsByAppointmentTime(startDateTime, endDateTime);
        verifyNoMoreInteractions(appointmentRepository);
        
        verify(workTimeRepository, times(1)).findWorkTimesByDesiredConsultationTime(TestUtil.getEndDate(endDateTime), TestUtil.getHours(endDateTime), startDateTime.getHours());
        verifyNoMoreInteractions(workTimeRepository);
        
        verify(absenceRepository, times(1)).findAbsenceByStaffIdAndDate(staffId,startDateTime);
        verifyNoMoreInteractions(absenceRepository);

	}

	
	@SuppressWarnings("deprecation")
	@Test
	@Transactional
	public void testCreateConsultation() throws AppointmentNotScheduledException, AppointmentTimeOverlappingWithOtherAppointmentException {
		
		Date startDateTime = new Date(2021, 2, 15, 14, 2);
		Date endDateTime = new Date(2021, 2, 15, 14, 17);
		UUID patientId = UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002");
		UUID staffId = UUID.fromString("25345278-52d3-11eb-ae93-0242ac130002");
		UUID pharmacyId = UUID.fromString("cafeddee-56cb-11eb-ae93-0242ac130002");
		UUID actionId = UUID.fromString("cafeddee-56cb-11eb-ae93-0242ac130002");

		int dbSizeBeforeAdd = ((List<Appointment>) appointmentRepository.findAll()).size();
		
		when(appointmentRepository.findAllAppointmentsByAppointmentTimeAndPatient(startDateTime,endDateTime, patientId)).thenReturn(new ArrayList<Appointment>());
		when(env.getProperty("max_penalty_count")).thenReturn("3");
		when(env.getProperty("consultation_time")).thenReturn("15");
		when(appointmentRepository.findAllConsultationsByAppointmentTime(startDateTime, endDateTime)).thenReturn(new ArrayList<Appointment>());
		when(workTimeRepository.findWorkTimeByDesiredConsultationTimeAndPharmacistId(TestUtil.getEndDate(endDateTime), TestUtil.getHours(endDateTime), staffId)).thenReturn(new WorkTime());
		when(absenceRepository.findAbsenceByStaffIdAndDate(staffId,startDateTime)).thenReturn(new ArrayList<Absence>());
		when(staffRepository.findById(staffId)).thenReturn(Optional.of(TestUtil.getPharmacistAsStaff(staffId, addressMock)));
		when(pharmacistRepository.findPharmacyByPharmacistId(staffId)).thenReturn(TestUtil.getPharmacy(pharmacyId, addressMock));
		when(patientRepository.findById(patientId)).thenReturn(Optional.of(TestUtil.getPatient(patientId, addressMock)));
		when(userService.getLoggedUserId()).thenReturn(patientId);
		when(loyalityProgramService.getDiscountAppointmentPriceForPatient(420, AppointmentType.CONSULTATION, patientId)).thenReturn(420.0);
		when(actionAndPromotionsRepository.findCurrentActionAndPromotionForPharmacyForActionType(pharmacyId, ActionAndPromotionType.CONSULTATIONDISCOUNT))
									.thenReturn(TestUtil.getActionAndPromotions(actionId));
		when(appointmentRepository.findAll()).thenReturn(
									TestUtil.getAppointment(pharmacyMock, staffMock, patientMock, startDateTime, endDateTime, AppointmentStatus.SCHEDULED, AppointmentType.CONSULTATION, 378.0));

		appointmentService.createConsultation(TestUtil.getConsultationRequestDTO(staffId, startDateTime));
		
		List<Appointment> appointments = (List<Appointment>) appointmentRepository.findAll();
        assertThat(appointments).hasSize(dbSizeBeforeAdd + 1); 
        
        Appointment appointment = appointments.get(appointments.size() - 1);
        
        assertThat(appointment.getAppointmentStatus()).isEqualTo(AppointmentStatus.SCHEDULED);
        assertThat(appointment.getAppointmentType()).isEqualTo(AppointmentType.CONSULTATION);
        assertThat(appointment.getStartDateTime()).isEqualTo(startDateTime);
        assertThat(appointment.getEndDateTime()).isEqualTo(endDateTime);
        assertThat(appointment.getPrice()).isEqualTo(378.0);
        assertThat(appointment.getPriceToPay()).isEqualTo(378.0);

	}
	

	
}
