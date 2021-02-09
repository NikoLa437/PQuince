package quince_it.pquince.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static quince_it.pquince.constants.Constants.APPOINTMENT_ID;
import static quince_it.pquince.constants.Constants.APPOINTMENT_START;
import static quince_it.pquince.constants.Constants.PATIENT_EMAIL;
import static quince_it.pquince.constants.Constants.PHARMACIST_ID;

import java.nio.charset.Charset;

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
import quince_it.pquince.repository.appointment.AppointmentRepository;
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
}
