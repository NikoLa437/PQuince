package quince_it.pquince.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static quince_it.pquince.constants.Constants.ADMIN_EMAIL;
import static quince_it.pquince.constants.Constants.DERMATHOLOGIST_ID;
import static quince_it.pquince.constants.Constants.PATIENT_NAME;
import static quince_it.pquince.constants.Constants.PATIENT_SURNAME;

import java.nio.charset.Charset;
import java.util.Date;

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

import static quince_it.pquince.constants.Constants.PATIENT_EMAIL;
import quince_it.pquince.entities.users.Address;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.ComplaintStaffDTO;
import quince_it.pquince.util.TestUtil;

@SpringBootTest
public class ComplaintControllerTests {
	private static final String URL_PREFIX = "/api/staff/complaint";

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
    @WithMockUser(username = PATIENT_EMAIL,authorities = {"ROLE_PATIENT"})
	@Transactional
	@Rollback(true)
	public void testAddComplaintForStaff() throws Exception {
		Date date = new Date();
		ComplaintStaffDTO complaintStaffDTO = new ComplaintStaffDTO(DERMATHOLOGIST_ID, date , "Test", PATIENT_NAME, PATIENT_SURNAME, "pharmacist", "",PATIENT_EMAIL);
		String json = TestUtil.json(complaintStaffDTO);
		this.mockMvc.perform(post(URL_PREFIX).contentType(contentType).content(json)).andExpect(status().isCreated());
	}
}
