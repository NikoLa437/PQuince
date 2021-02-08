package quince_it.pquince.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static quince_it.pquince.constants.Constants.PHARMACY_ID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Address;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.util.pharmacy.PharmacyMapper;

@SpringBootTest
public class PharmacyMapperTests {

	@Mock
	private Address addressMock;
	
	@Test
	public void testMapPharmacyPersistenceToPharmacyDTOWhenNullSent() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(null);
		});
	}
	
	@Test
	public void testMapPharmacyPersistenceToPharmacyDTO() {
		Pharmacy pharmacy = new Pharmacy(PHARMACY_ID, "Benu", "Apoteka", addressMock, 100);
		
		IdentifiableDTO<PharmacyDTO> identifiablePharmacyDTO = PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(pharmacy);
		
		assertThat(identifiablePharmacyDTO.Id).isEqualTo(pharmacy.getId());
		assertThat(identifiablePharmacyDTO.EntityDTO.getDescription()).isEqualTo(pharmacy.getDescription());
		assertThat(identifiablePharmacyDTO.EntityDTO.getName()).isEqualTo(pharmacy.getName());
		assertThat(identifiablePharmacyDTO.EntityDTO.getConsultationPrice()).isEqualTo(pharmacy.getConsultationPrice());
		assertThat(identifiablePharmacyDTO.EntityDTO.getAddress()).isEqualTo(pharmacy.getAddress());


	}
}
