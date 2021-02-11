package quince_it.pquince.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static quince_it.pquince.constants.Constants.ADMIN_EMAIL;
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

import quince_it.pquince.entities.users.Address;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.LoyaltyProgramDTO;
import quince_it.pquince.util.TestUtil;

@SpringBootTest
public class LoyaltyProgramControllerTests {
	private static final String URL_PREFIX = "/api/loyaltyProgram";

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
	public void testUpdateLoyaltyProgram() throws Exception {
		
		LoyaltyProgramDTO loyaltyProgramDTO = new LoyaltyProgramDTO();
		loyaltyProgramDTO.setPointsForAppointment(5);
		loyaltyProgramDTO.setPointsForConsulting(5);
		loyaltyProgramDTO.setPointsToEnterRegularCathegory(5);
		loyaltyProgramDTO.setPointsToEnterSilverCathegory(5);
		loyaltyProgramDTO.setPointsToEnterGoldCathegory(4);
		loyaltyProgramDTO.setAppointmentDiscountRegular(5);
		loyaltyProgramDTO.setDrugDiscountRegular(4);
		loyaltyProgramDTO.setConsultationDiscountRegular(5);
		loyaltyProgramDTO.setAppointmentDiscountSilver(5);
		loyaltyProgramDTO.setDrugDiscountSilver(4);
		loyaltyProgramDTO.setConsultationDiscountSilver(5);
		loyaltyProgramDTO.setAppointmentDiscountGold(17);
		loyaltyProgramDTO.setDrugDiscountGold(16);
		loyaltyProgramDTO.setConsultationDiscountGold(15);
		String json = TestUtil.json(loyaltyProgramDTO);
		this.mockMvc.perform(put(URL_PREFIX + "/update").contentType(contentType).content(json)).andExpect(status().isNoContent());
	}
}
