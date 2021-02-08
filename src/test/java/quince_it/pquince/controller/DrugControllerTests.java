package quince_it.pquince.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static quince_it.pquince.constants.Constants.DRUG_ID;
import static quince_it.pquince.constants.Constants.PATIENT_EMAIL;

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

import quince_it.pquince.services.contracts.dto.drugs.DrugFeedbackDTO;
import quince_it.pquince.util.TestUtil;

@SpringBootTest
public class DrugControllerTests {

	private static final String URL_PREFIX = "/api/drug";

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
	public void testAddDrugFeedback() throws Exception {
		
		DrugFeedbackDTO drugFeedbackDTO = new DrugFeedbackDTO(DRUG_ID, new Date(), 3);
		String json = TestUtil.json(drugFeedbackDTO);
		this.mockMvc.perform(post(URL_PREFIX + "/feedback").contentType(contentType).content(json)).andExpect(status().isCreated());
	}
	
}
