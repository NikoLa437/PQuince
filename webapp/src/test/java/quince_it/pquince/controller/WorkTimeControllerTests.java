package quince_it.pquince.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

import quince_it.pquince.services.contracts.dto.EntityIdDTO;
import quince_it.pquince.services.contracts.dto.users.WorkTimeDTO;
import quince_it.pquince.util.TestUtil;

@SpringBootTest
public class WorkTimeControllerTests {
	private static final String URL_PREFIX = "/api/worktime";

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
	public void testCreateWorkTimeForStaff() throws Exception{		

		@SuppressWarnings("deprecation")
		WorkTimeDTO workTimeDTO = new WorkTimeDTO(PHARMACY_ID,DERMATHOLOGIST_ID,new Date(2015,5,5),new Date(2015,7,5),8,17,"");
		String json = TestUtil.json(workTimeDTO);
		this.mockMvc.perform(post(URL_PREFIX).contentType(contentType).content(json)).andExpect(status().isOk());
		}
}
