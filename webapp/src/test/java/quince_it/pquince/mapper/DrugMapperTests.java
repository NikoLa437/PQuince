package quince_it.pquince.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.entities.drugs.DrugReservation;
import quince_it.pquince.entities.drugs.Manufacturer;
import quince_it.pquince.entities.drugs.ReservationStatus;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.util.drugs.DrugReservationMapper;

import static quince_it.pquince.constants.Constants.DRUG_RESERVATION_ID;
import static quince_it.pquince.constants.Constants.DRUG_RESERVATION_START_DATE_TIME;
import static quince_it.pquince.constants.Constants.DRUG_RESERVATION_END_DATE_TIME;


@SpringBootTest
public class DrugMapperTests {
	
	@Mock
	private Pharmacy pharmacyMock;
	@Mock
	private DrugInstance drugInstanceMock;
	@Mock
	private Patient patientMock;
	@Mock
	private Manufacturer manufacturer;
	
	@Test
	public void testMapDrugReservationPersistenceToDrugReservationIdentifiableDTO() {
		when(drugInstanceMock.getManufacturer()).thenReturn(manufacturer);
		
		DrugReservation drugReservation = new DrugReservation(DRUG_RESERVATION_ID, pharmacyMock, drugInstanceMock, patientMock, 5, DRUG_RESERVATION_START_DATE_TIME, DRUG_RESERVATION_END_DATE_TIME, 200, ReservationStatus.ACTIVE);
		
		IdentifiableDTO<DrugReservationDTO> identifiableDrugReservationDTO = DrugReservationMapper.MapDrugReservationPersistenceToDrugReservationIdentifiableDTO(drugReservation);
		
		assertThat(identifiableDrugReservationDTO.Id).isEqualTo(drugReservation.getId());
		assertThat(identifiableDrugReservationDTO.EntityDTO.getAmount()).isEqualTo(drugReservation.getAmount());
		assertThat(identifiableDrugReservationDTO.EntityDTO.getStartDate()).isEqualTo(drugReservation.getStartDate());
		assertThat(identifiableDrugReservationDTO.EntityDTO.getEndDate()).isEqualTo(drugReservation.getEndDate());
		assertThat(identifiableDrugReservationDTO.EntityDTO.getDrugPeacePrice()).isEqualTo(drugReservation.getDrugPeacePrice());
		assertThat(identifiableDrugReservationDTO.EntityDTO.getReservationStatus()).isEqualTo(drugReservation.getReservationStatus());
	}
	
	@Test
	public void testMapDrugReservationPersistenceToDrugReservationIdentifiableDTOWhenNullSent() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			DrugReservationMapper.MapDrugReservationPersistenceToDrugReservationIdentifiableDTO(null);
		});
	}
}
