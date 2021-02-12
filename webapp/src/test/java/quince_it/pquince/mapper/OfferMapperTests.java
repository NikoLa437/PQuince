package quince_it.pquince.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static quince_it.pquince.constants.Constants.PHARMACY_ID;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import quince_it.pquince.entities.drugs.DrugOrder;
import quince_it.pquince.entities.drugs.OfferStatus;
import quince_it.pquince.entities.drugs.Offers;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Address;
import quince_it.pquince.services.contracts.dto.drugs.DrugOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OfferDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.util.drugs.OfferMapper;
import quince_it.pquince.services.implementation.util.pharmacy.PharmacyMapper;

@SpringBootTest
public class OfferMapperTests {

	@Mock
	private Address addressMock;
	
	@Test
	public void testMapOfferPersistenceToOfferDTOWhenNullSent() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(null);
		});
	}
	
	@Test
	public void testMapListOfferPersistenceToListOfferIdentifiableDTO() {
		Date dateToDelivery = new Date();
		Offers offer = new Offers(dateToDelivery, 2000.0, OfferStatus.WAITING);
		
		IdentifiableDTO<OfferDTO> identifiableOfferDTO = OfferMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(offer);
		
		assertThat(identifiableOfferDTO.Id).isEqualTo(offer.getId());
		assertThat(identifiableOfferDTO.EntityDTO.getOfferStatus()).isEqualTo(offer.getOfferStatus());
		assertThat(identifiableOfferDTO.EntityDTO.getPrice()).isEqualTo(offer.getPrice());
		assertThat(identifiableOfferDTO.EntityDTO.getDateToDelivery()).isEqualTo(offer.getDateToDelivery());


	}
}
