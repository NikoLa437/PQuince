package quince_it.pquince.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static quince_it.pquince.constants.Constants.DRUG_ID;
import static quince_it.pquince.constants.Constants.PATIENT_EMAIL;
import static quince_it.pquince.constants.Constants.PATIENT_NAME;
import static quince_it.pquince.constants.Constants.PATIENT_SURNAME;
import static quince_it.pquince.constants.Constants.ADMIN_EMAIL;
import static quince_it.pquince.constants.Constants.DERMATHOLOGIST_ID;
import static quince_it.pquince.constants.Constants.PHARMACY_ID;
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

import quince_it.pquince.entities.users.Address;
import quince_it.pquince.services.contracts.dto.drugs.DrugFeedbackDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.ComplaintPharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.ComplaintStaffDTO;
import quince_it.pquince.util.TestUtil;

@SpringBootTest
public class PharmacyControllerTest {
	private static final String URL_PREFIX = "/api/pharmacy";

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
    @WithMockUser(username = ADMIN_EMAIL,authorities = {"ROLE_SYSADMIN"})
	@Transactional
	@Rollback(true)
	public void testAddPharmacy() throws Exception {
		
		PharmacyDTO pharmacyDTO = new PharmacyDTO("Benu", new Address(22.2,22.2,"Novi Sad","Srbija", "Bul Oslobodjenja"), "Benu", 999);
		String json = TestUtil.json(pharmacyDTO);
		this.mockMvc.perform(post(URL_PREFIX).contentType(contentType).content(json)).andExpect(status().isCreated());
	}
	
	@Test
    @WithMockUser(username = PATIENT_EMAIL,authorities = {"ROLE_PATIENT"})
	@Transactional
	@Rollback(true)
	public void testAddComplaintForStaff() throws Exception {
		Date date = new Date();
		ComplaintPharmacyDTO complaintPharmacyDTO = new ComplaintPharmacyDTO(PHARMACY_ID,date, "Test","Benu", "");
		String json = TestUtil.json(complaintPharmacyDTO);
		this.mockMvc.perform(post(URL_PREFIX + "/complaint-pharmacy").contentType(contentType).content(json)).andExpect(status().isCreated());
	}
}
