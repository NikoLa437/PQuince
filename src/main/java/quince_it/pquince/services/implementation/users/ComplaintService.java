package quince_it.pquince.services.implementation.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.users.ComplaintStaff;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffFeedbackId;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.users.ComplaintRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.repository.users.UserRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.dto.users.ComplaintStaffDTO;
import quince_it.pquince.services.contracts.exceptions.ComplaintsNotAllowedException;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.users.IComplaintService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.services.implementation.util.drugs.DrugInstanceMapper;
import quince_it.pquince.services.implementation.util.users.ComplaintStaffMapper;

@Service
public class ComplaintService implements IComplaintService{

	@Autowired
	private ComplaintRepository complaintRepository;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private StaffRepository	staffRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void create(ComplaintStaffDTO entityDTO) throws ComplaintsNotAllowedException {
		
		
		UUID patientId = getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();
		
		CanPatientGiveFeedback(patient.getId(), entityDTO.getStaffId());
		
		Staff staff = staffRepository.findById(entityDTO.getStaffId()).get();
		
		ComplaintStaff complaintStaff = new ComplaintStaff(staff,patient, entityDTO.getText(), entityDTO.getStaffName(),entityDTO.getStaffSurname(), entityDTO.getProfession(), patient.getEmail());
		complaintRepository.save(complaintStaff);
		
	}
	
	private UUID getLoggedUserId() {
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email = currentUser.getName();
		User user = userRepository.findByEmail(email);
		
		return user.getId();
	}
	
	@Override
	public List<IdentifiableDTO<ComplaintStaffDTO>> findAll() {
		
		List<IdentifiableDTO<ComplaintStaffDTO>> complaints = new ArrayList<IdentifiableDTO<ComplaintStaffDTO>>();
		complaintRepository.findAll().forEach((d) -> complaints.add(ComplaintStaffMapper.MapComplaintStaffPersistenceToComplaintStaffIdentifiableDTO(d)));
		
		return complaints;
	}
	
	@Override
	public void CanPatientGiveFeedback(UUID patientId, UUID staffId) throws ComplaintsNotAllowedException {
		if(!(appointmentRepository.findAllPreviousAppointmentsForPatientForStaff(patientId, staffId).size() > 0))
			throw new ComplaintsNotAllowedException();
	}

	@Override
	public ComplaintStaffDTO findByStaffIdAndPatientId(UUID staffId, UUID patientId) {
		return ComplaintStaffMapper.MapStaffComplaintPersistenceToStaffComplaintDTO(complaintRepository.findByStaffIdAndPatientId(staffId, patientId));
	}

	@Override
	public void replyComplaint(UUID id, String reply, String email) {
		ComplaintStaff complaintStaff = complaintRepository.getOne(id);
		complaintStaff.setReply(reply);
		
		complaintRepository.save(complaintStaff);
		
		try {
			emailService.sendComplaintReplyAsync(complaintStaff.getEmail(), reply);
		} catch (MailException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void update(ComplaintStaffDTO complaintStaffDTO) {
		// TODO Auto-generated method stub
		
	}

}
