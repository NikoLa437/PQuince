package quince_it.pquince.services.implementation.users;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffFeedback;
import quince_it.pquince.entities.users.StaffFeedbackId;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.StaffFeedbackRepository;
import quince_it.pquince.repository.users.StaffRepository;
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
	public void create(StaffFeedbackDTO entityDTO) {
		
		//TODO : get logged patient
		
		try {
			
			Patient patient = patientRepository.findById(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")).get();
			
			if(!CanPatientGiveFeedback(patient.getId(), entityDTO.getStaffId())) throw new FeedbackNotAllowedException();
			
			Staff staff = staffRepository.findById(entityDTO.getStaffId()).get();
			
			StaffFeedback staffFeedback = new StaffFeedback(staff,patient, entityDTO.getGrade());
			staffFeedbackRepository.save(staffFeedback);
		}catch (Exception e) {
		}
		
	}

	@Override
	public boolean CanPatientGiveFeedback(UUID patientId, UUID staffId) {
		
		return appointmentRepository.findAllPreviousAppointmentsForPatientForStaff(patientId, staffId).size() > 0;
	}

	@Override
	public void update(StaffFeedbackDTO entityDTO) {
		//TODO : get logged patient
		
		try {
			Patient patient = patientRepository.findById(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")).get();
			Staff staff = staffRepository.findById(entityDTO.getStaffId()).get();
			
			StaffFeedback staffFeedback = staffFeedbackRepository.findById(new StaffFeedbackId(staff, patient)).get();
			staffFeedback.setDate(new Date());
			staffFeedback.setGrade(entityDTO.getGrade());
			
			staffFeedbackRepository.save(staffFeedback);
			
		}catch (Exception e) {
		}
	}

	@Override
	public StaffFeedbackDTO findByStaffIdAndPatientId(UUID staffId, UUID patientId) {
		return StaffFeedbackMapper.MapStaffFeedbackPersistenceToStaffFeedbackDTO(staffFeedbackRepository.findByStaffIdAndPatientId(staffId, patientId));
	}

}
