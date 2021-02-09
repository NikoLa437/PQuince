package quince_it.pquince.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static quince_it.pquince.constants.Constants.PATIENT_EMAIL;
import static quince_it.pquince.constants.Constants.PATIENT_ID;
import static quince_it.pquince.constants.Constants.PATIENT_NAME;
import static quince_it.pquince.constants.Constants.PATIENT_SURNAME;

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

import quince_it.pquince.services.contracts.dto.users.UserInfoChangeDTO;
import quince_it.pquince.util.TestUtil;

@SpringBootTest
public class UserControllerTests {

	
	private static final String URL_PREFIX = "/api/users";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MediaType contentTypeGet = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype());
	
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
	public void testPatientInfoChange() throws Exception {
		
		UserInfoChangeDTO userInfoChangeDTO = new UserInfoChangeDTO();
		userInfoChangeDTO.setName("Propa");
		userInfoChangeDTO.setPhoneNumber("060000000");
		userInfoChangeDTO.setSurname("Proba");
		
		String json = TestUtil.json(userInfoChangeDTO);
		this.mockMvc.perform(put(URL_PREFIX + "/patient").contentType(contentType).content(json)).andExpect(status().isNoContent());
	}
	
	
	@Test
    @WithMockUser(username = PATIENT_EMAIL,authorities = {"ROLE_PATIENT"})
	@Transactional
	@Rollback(true)
	public void testPatientGetLoggedPatientProfile() throws Exception {

		mockMvc.perform(get(URL_PREFIX + "/patient"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentTypeGet))
		.andExpect(jsonPath("$.Id").value(PATIENT_ID.toString()))
		.andExpect(jsonPath("$.EntityDTO.name").value(PATIENT_NAME))
		.andExpect(jsonPath("$.EntityDTO.email").value(PATIENT_EMAIL))
		.andExpect(jsonPath("$.EntityDTO.surname").value(PATIENT_SURNAME));
		
	}
}
