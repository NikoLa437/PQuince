package quince_it.pquince.services.implementation.users;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffFeedback;
import quince_it.pquince.entities.users.StaffFeedbackId;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.StaffFeedbackRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.repository.users.UserRepository;
import quince_it.pquince.services.contracts.dto.users.StaffFeedbackDTO;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;
import quince_it.pquince.services.contracts.interfaces.users.IStaffFeedbackService;
import quince_it.pquince.services.implementation.util.users.StaffFeedbackMapper;


@Service
public class StaffFeedbackService implements IStaffFeedbackService{

	@Autowired
	private StaffFeedbackRepository	staffFeedbackRepository;
	
	@Autowired
	private StaffRepository	staffRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public double findAvgGradeForStaff(UUID staffId) {
		try {
			double retVal = staffFeedbackRepository.findAvgGradeForStaff(staffId);
			return retVal;
		}catch (Exception e) {
			return 0;
		}
	}

	@Override
	public void create(StaffFeedbackDTO entityDTO) throws FeedbackNotAllowedException {		
		UUID patientId = getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();
		
		CanPatientGiveFeedback(patient.getId(), entityDTO.getStaffId());
		
		Staff staff = staffRepository.findById(entityDTO.getStaffId()).get();
		
		StaffFeedback staffFeedback = new StaffFeedback(staff,patient, entityDTO.getGrade());
		staffFeedbackRepository.save(staffFeedback);
	}

	@Override
	public void CanPatientGiveFeedback(UUID patientId, UUID staffId) throws FeedbackNotAllowedException {
		if(!(appointmentRepository.findAllPreviousAppointmentsForPatientForStaff(patientId, staffId).size() > 0))
			throw new FeedbackNotAllowedException();
	}

	@Override
	public void update(StaffFeedbackDTO entityDTO) {		
		
		UUID patientId = getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();
		Staff staff = staffRepository.findById(entityDTO.getStaffId()).get();
		
		StaffFeedback staffFeedback = staffFeedbackRepository.findById(new StaffFeedbackId(staff, patient)).get();
		staffFeedback.setDate(new Date());
		staffFeedback.setGrade(entityDTO.getGrade());
		
		staffFeedbackRepository.save(staffFeedback);
	}

	@Override
	public StaffFeedbackDTO findByStaffIdAndPatientId(UUID staffId) {
		UUID patientId = getLoggedUserId();
		return StaffFeedbackMapper.MapStaffFeedbackPersistenceToStaffFeedbackDTO(staffFeedbackRepository.findByStaffIdAndPatientId(staffId, patientId));
	}
	
	private UUID getLoggedUserId() {
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email = currentUser.getName();
		User user = userRepository.findByEmail(email);
		
		return user.getId();
	}

}
