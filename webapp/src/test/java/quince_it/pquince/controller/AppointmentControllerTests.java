package quince_it.pquince.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static quince_it.pquince.constants.Constants.APPOINTMENT_ID;
import static quince_it.pquince.constants.Constants.CREATED_APPOINTMENT_ID;
import static quince_it.pquince.constants.Constants.SCHEDULED_APPOINTMENT_ID;
import static quince_it.pquince.constants.Constants.APPOINTMENT_START;
import static quince_it.pquince.constants.Constants.APPOINTMENT_END;
import static quince_it.pquince.constants.Constants.PATIENT_EMAIL;
import static quince_it.pquince.constants.Constants.PHARMACIST_EMAIL;
import static quince_it.pquince.constants.Constants.DERMATOLOGIST_EMAIL;
import static quince_it.pquince.constants.Constants.DRUG_ID;
import static quince_it.pquince.constants.Constants.PHARMACIST_ID;
import static quince_it.pquince.constants.Constants.PATIENT_ID;
import static quince_it.pquince.constants.Constants.PHARMACYADMIN_EMAIL;
import static quince_it.pquince.constants.Constants.PHARMACY_ID;
import static quince_it.pquince.constants.Constants.DERMATHOLOGIST_ID;



import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.appointment.AppointmentStatus;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentCreateDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugFeedbackDTO;
import quince_it.pquince.util.TestUtil;


@SpringBootTest
public class AppointmentControllerTests {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	private static final String URL_PREFIX = "/api/appointment";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
    @WithMockUser(username = PATIENT_EMAIL ,authorities = {"ROLE_PATIENT"})
	@Transactional
	@Rollback(true)
	public void testCreatePharmacistAppointment() throws Exception {		
				
		String json = "{\"pharmacistId\":\"" + PHARMACIST_ID + "\",\"startDateTime\":" + APPOINTMENT_START + "}";
		this.mockMvc.perform(post(URL_PREFIX + "/reserve-appointment").contentType(contentType).content(json)).andExpect(status().isCreated());
	}
	
	@Test
    @WithMockUser(username = DERMATOLOGIST_EMAIL ,authorities = {"ROLE_DERMATHOLOGIST"})
	@Transactional
	@Rollback(true)
	public void testFinishAppointment() throws Exception {
	String json = "{\"id\":\"" + SCHEDULED_APPOINTMENT_ID + "\"}";
	this.mockMvc.perform(put(URL_PREFIX + "/finish").contentType(contentType).content(json)).andExpect(status().isOk());
	}
	
	@Test
    @WithMockUser(username = DERMATOLOGIST_EMAIL ,authorities = {"ROLE_DERMATHOLOGIST"})
	@Transactional
	@Rollback(true)
	public void testDidNotShowUpToAppointment() throws Exception {
	String json = "{\"id\":\"" + SCHEDULED_APPOINTMENT_ID + "\"}";
	this.mockMvc.perform(put(URL_PREFIX + "/did-not-show-up").contentType(contentType).content(json)).andExpect(status().isOk());
	}
	
	@Test
    @WithMockUser(username = DERMATOLOGIST_EMAIL ,authorities = {"ROLE_DERMATHOLOGIST"})
	@Transactional
	@Rollback(true)
	public void testSchuduleAppointment() throws Exception {
	String json = "{\"patientId\":\"" + PATIENT_ID + "\",\"appointmentId\":\"" + CREATED_APPOINTMENT_ID + "\"}";
	this.mockMvc.perform(post(URL_PREFIX + "/schedule-appointment").contentType(contentType).content(json)).andExpect(status().isCreated());
	}
	
	@Test
    @WithMockUser(username = DERMATOLOGIST_EMAIL ,authorities = {"ROLE_DERMATHOLOGIST"})
	@Transactional
	@Rollback(true)
	public void testCreateAndSchuduleAppointment() throws Exception {
	String json = "{\"patientId\":\"" + PATIENT_ID + "\",\"startDateTime\":" + APPOINTMENT_START + ",\"endDateTime\":" + APPOINTMENT_END  + ",\"price\":" + 1000 +"}";
	this.mockMvc.perform(post(URL_PREFIX + "/create-and-schedule-appointment").contentType(contentType).content(json)).andExpect(status().isOk());
	}
	
	@Test
    @WithMockUser(username = PHARMACIST_EMAIL ,authorities = {"ROLE_PHARMACIST"})
	@Transactional
	@Rollback(true)
	public void testNewConsultation() throws Exception {
	String json = "{\"patientId\":\"" + PATIENT_ID + "\",\"startDateTime\":" + APPOINTMENT_START + "}";
	this.mockMvc.perform(post(URL_PREFIX + "/pharmacist/new").contentType(contentType).content(json)).andExpect(status().isCreated());
	}
	
	@Test
    @WithMockUser(username = PATIENT_EMAIL,authorities = {"ROLE_PATIENT"})
	@Transactional
	@Rollback(true)
	public void testCancelAppointment() throws Exception{		
		Appointment app = appointmentRepository.findById(APPOINTMENT_ID).get();
		Appointment newApp = TestUtil.CreateAppointment(app);
		appointmentRepository.save(newApp);
		String json = "{\"id\":\"" + newApp.getId() + "\"}";
		this.mockMvc.perform(put(URL_PREFIX + "/cancel-appointment").contentType(contentType).content(json)).andExpect(status().isNoContent());
	}
	
	@Test
    @WithMockUser(username = PHARMACYADMIN_EMAIL,authorities = {"ROLE_PHARMACYADMIN"})
	@Transactional
	@Rollback(true)
	public void testAddPeriodForDermatologistAppointment() throws Exception {
		@SuppressWarnings("deprecation")
		AppointmentCreateDTO appointmentCreateDTO = new AppointmentCreateDTO(DERMATHOLOGIST_ID, PHARMACY_ID, PATIENT_ID,AppointmentStatus.CREATED,new Date(2000,1,5,16,00),new Date(2000,1,5,16,30),1500);
		String json = TestUtil.json(appointmentCreateDTO);
		this.mockMvc.perform(post(URL_PREFIX + "/create-appointment-for-dermatologist").contentType(contentType).content(json)).andExpect(status().isOk());
	}
}
