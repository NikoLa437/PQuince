package quince_it.pquince.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static quince_it.pquince.constants.Constants.PHARMACYADMIN_EMAIL;
import static quince_it.pquince.constants.Constants.ABSENCE_ID_FOR_APPROVE;
import static quince_it.pquince.constants.Constants.ABSENCE_ID_FOR_REJECT;

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

import quince_it.pquince.services.contracts.dto.EntityIdDTO;
import quince_it.pquince.services.contracts.dto.users.RejectAbsenceDTO;
import quince_it.pquince.util.TestUtil;
@SpringBootTest
public class AbsenceControllerTests {
	
	
	private static final String URL_PREFIX = "/api/absence";

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
    @WithMockUser(username = PHARMACYADMIN_EMAIL,authorities = {"ROLE_PHARMACYADMIN"})
	@Transactional
	@Rollback(true)
	public void testApproveAbsence() throws Exception{		
		EntityIdDTO absenceDTO = new EntityIdDTO(ABSENCE_ID_FOR_APPROVE);
		String json = TestUtil.json(absenceDTO);
		this.mockMvc.perform(put(URL_PREFIX + "/accept-absence").contentType(contentType).content(json)).andExpect(status().isNoContent());
	}
	
	@Test
    @WithMockUser(username = PHARMACYADMIN_EMAIL,authorities = {"ROLE_PHARMACYADMIN"})
	@Transactional
	@Rollback(true)
	public void testRejectAbsence() throws Exception{	
		RejectAbsenceDTO rejectAbsenceDTO = new RejectAbsenceDTO(ABSENCE_ID_FOR_REJECT,"Some reason");
		String json = TestUtil.json(rejectAbsenceDTO);
		this.mockMvc.perform(put(URL_PREFIX + "/reject-absence").contentType(contentType).content(json)).andExpect(status().isNoContent());
	}
}
