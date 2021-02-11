package quince_it.pquince.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static quince_it.pquince.constants.Constants.DERMATHOLOGIST_ID;
import static quince_it.pquince.constants.Constants.PATIENT_NAME;
import static quince_it.pquince.constants.Constants.PATIENT_SURNAME;
import static quince_it.pquince.constants.Constants.PATIENT_EMAIL;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Address;
import quince_it.pquince.entities.users.ComplaintStaff;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.ComplaintStaffDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.util.pharmacy.PharmacyMapper;
import quince_it.pquince.services.implementation.util.users.ComplaintStaffMapper;

@SpringBootTest
public class ComplaintStaffMapperTests {

	@Mock
	private Patient patientMock;

	@Mock
	private Staff staffMock;
	@Test
	public void testMapPharmacyPersistenceToPharmacyDTOWhenNullSent() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ComplaintStaffMapper.MapComplaintStaffPersistenceToComplaintStaffIdentifiableDTO(null);
		});
	}
	
	@Test
	public void testMapComplaintStaffPersistenceToComplaintStaffIdentifiableDTO() {
		ComplaintStaff complaintStaff = new ComplaintStaff(staffMock, patientMock, "Test",PATIENT_NAME,PATIENT_SURNAME, "dermathologist", PATIENT_EMAIL);
		
		IdentifiableDTO<ComplaintStaffDTO> identifiableComplaintStaffDTO = ComplaintStaffMapper.MapComplaintStaffPersistenceToComplaintStaffIdentifiableDTO(complaintStaff);
		
		assertThat(identifiableComplaintStaffDTO.Id).isEqualTo(complaintStaff.getId());
		assertThat(identifiableComplaintStaffDTO.EntityDTO.getDate()).isEqualTo(complaintStaff.getDate());
		assertThat(identifiableComplaintStaffDTO.EntityDTO.getEmail()).isEqualTo(complaintStaff.getEmail());
		assertThat(identifiableComplaintStaffDTO.EntityDTO.getProfession()).isEqualTo(complaintStaff.getProfession());
		assertThat(identifiableComplaintStaffDTO.EntityDTO.getStaffId()).isEqualTo(complaintStaff.getStaff().getId());
		assertThat(identifiableComplaintStaffDTO.EntityDTO.getText()).isEqualTo(complaintStaff.getText());
		assertThat(identifiableComplaintStaffDTO.EntityDTO.getReply()).isEqualTo(complaintStaff.getReply());
		assertThat(identifiableComplaintStaffDTO.EntityDTO.getStaffName()).isEqualTo(complaintStaff.getStaffName());
		assertThat(identifiableComplaintStaffDTO.EntityDTO.getStaffSurname()).isEqualTo(complaintStaff.getStaffSurname());


	}
}
