package quince_it.pquince.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static quince_it.pquince.constants.Constants.DERMATOLOGIST_ID;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Absence;
import quince_it.pquince.entities.users.ComplaintStaff;
import quince_it.pquince.repository.users.ComplaintRepository;
import quince_it.pquince.repository.users.UserRepository;
import quince_it.pquince.repository.users.WorkTimeRepository;
import quince_it.pquince.services.implementation.users.ComplaintService;
import quince_it.pquince.services.implementation.users.UserService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.util.TestUtil;

@SpringBootTest
public class ComplaintServiceTests {

	@Mock
	EmailService emailService;
	
	@Mock
	ComplaintRepository complaintRepository;
	
	@Mock
	WorkTimeRepository workTimeRepository;
	
	@InjectMocks
	@Spy
	private ComplaintService complaintService;
	
	@Test
	public void testReplyComplaint() {
		Mockito.doReturn(TestUtil.getComplaintStaff()).when(complaintRepository).getOne(DERMATOLOGIST_ID);
		when(complaintRepository.save(TestUtil.getComplaintStaffWithReply())).thenReturn(TestUtil.getSavedComplaint());
		
		ComplaintStaff complaintStaff = complaintService.replyComplaint(DERMATOLOGIST_ID,"TEST", "mail@example.com");
		
		assertThat(complaintStaff.getReply() == TestUtil.getSavedComplaint().getReply());
	}
	
}
