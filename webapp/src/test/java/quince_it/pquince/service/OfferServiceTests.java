package quince_it.pquince.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static quince_it.pquince.constants.Constants.SUPPLIER_ID;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import quince_it.pquince.entities.drugs.Offers;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.repository.drugs.OfferRepository;
import quince_it.pquince.repository.users.UserRepository;
import quince_it.pquince.repository.users.WorkTimeRepository;
import quince_it.pquince.services.contracts.dto.drugs.OfferDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.drugs.OfferService;
import quince_it.pquince.services.implementation.users.UserService;
import quince_it.pquince.util.TestUtil;

@SpringBootTest
public class OfferServiceTests {

	@Mock
	OfferRepository offerRepository;
	@Mock
	WorkTimeRepository workTimeRepository;
	@InjectMocks
	@Spy
	private OfferService offerService;

	@Mock
	UserService userService;
	
	@Test
	public void testfindAllSupplierOffers() {
		Mockito.doReturn(SUPPLIER_ID).when(userService).getLoggedUserId();
		when(offerRepository.findBySupplier(SUPPLIER_ID)).thenReturn(TestUtil.getOffers());
		List<IdentifiableDTO<OfferDTO>> offers = offerService.findAll();
		assertThat(offers.get(0).EntityDTO.getId() == TestUtil.getOffer().getId());
		assertThat(offers.get(0).EntityDTO.getDateToDelivery() == TestUtil.getOffer().getDateToDelivery());
		assertThat(offers.get(0).EntityDTO.getOfferStatus() == TestUtil.getOffer().getOfferStatus());
		assertThat(offers.get(0).EntityDTO.getPrice() == TestUtil.getOffer().getPrice());
	}
}
