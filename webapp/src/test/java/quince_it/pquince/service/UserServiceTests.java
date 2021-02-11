package quince_it.pquince.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import static quince_it.pquince.constants.Constants.DERMATOLOGIST_ID;

import java.util.ArrayList;

import static quince_it.pquince.constants.Constants.DERMATOLOGIST_EMAIL;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.entities.users.WorkTime;
import quince_it.pquince.repository.users.UserRepository;
import quince_it.pquince.repository.users.WorkTimeRepository;
import quince_it.pquince.services.implementation.users.UserService;
import quince_it.pquince.services.implementation.util.drugs.DrugReservationMapper;
import quince_it.pquince.util.TestUtil;

@SpringBootTest
public class UserServiceTests {
	
	@Mock
	UserRepository userRepository;
	@Mock
	WorkTimeRepository workTimeRepository;
	@InjectMocks
	@Spy
	private UserService userService;
	
	@Test
	public void testGetPharmacyForLoggedDermatologist() {
		Mockito.doReturn(DERMATOLOGIST_ID).when(userService).getLoggedUserId();
		when(workTimeRepository.findWorkTimesForDeramtologistAndCurrentDate(DERMATOLOGIST_ID)).thenReturn(TestUtil.getWorkTimes());
		Pharmacy pharmacy = userService.getPharmacyForLoggedDermatologist();
		assertThat(pharmacy.getId() == TestUtil.getPharmacy().getId());
	}
	
	@Test
	public void testGetPharmacyForLoggedDermatologistWhenNoWorkTimes() {
		Mockito.doReturn(DERMATOLOGIST_ID).when(userService).getLoggedUserId();
		when(workTimeRepository.findWorkTimesForDeramtologistAndCurrentDate(DERMATOLOGIST_ID)).thenReturn(new ArrayList<WorkTime>());
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			userService.getPharmacyForLoggedDermatologist();});
	}
}
