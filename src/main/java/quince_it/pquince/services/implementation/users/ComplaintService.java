package quince_it.pquince.services.implementation.users;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.users.ComplaintStaff;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffFeedbackId;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.users.ComplaintRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.services.contracts.dto.users.ComplaintStaffDTO;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;
import quince_it.pquince.services.contracts.interfaces.users.IComplaintService;
import quince_it.pquince.services.implementation.util.users.ComplaintStaffMapper;

@Service
public class ComplaintService implements IComplaintService{

	@Autowired
	private ComplaintRepository complaintRepository;
		
	@Autowired
	private StaffRepository	staffRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Override
	public void create(ComplaintStaffDTO entityDTO) {
		
		//TODO : get logged patient
		
		try {
			
			Patient patient = patientRepository.findById(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")).get();
			
			if(!CanPatientGiveComplaint(patient.getId(), entityDTO.getStaffId())) throw new FeedbackNotAllowedException();
			
			Staff staff = staffRepository.findById(entityDTO.getStaffId()).get();
			
			ComplaintStaff complaintStaff = new ComplaintStaff(staff,patient, entityDTO.getText());
			complaintRepository.save(complaintStaff);
		}catch (Exception e) {
		}
		
	}

	@Override
	public boolean CanPatientGiveComplaint(UUID patientId, UUID staffId) {
		
		return appointmentRepository.findAllPreviousAppointmentsForPatientForStaff(patientId, staffId).size() > 0;
	}

	@Override
	public void update(ComplaintStaffDTO entityDTO) {
		//TODO : get logged patient
		
		try {
			Patient patient = patientRepository.findById(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")).get();
			Staff staff = staffRepository.findById(entityDTO.getStaffId()).get();
			
			ComplaintStaff complaintStaff = complaintRepository.findById(new StaffFeedbackId(staff, patient)).get();
			complaintStaff.setDate(new Date());
			complaintStaff.setText(entityDTO.getText());
			
			complaintRepository.save(complaintStaff);
			
		}catch (Exception e) {
		}
	}

	@Override
	public ComplaintStaffDTO findByStaffIdAndPatientId(UUID staffId, UUID patientId) {
		return ComplaintStaffMapper.MapStaffComplaintPersistenceToStaffComplaintDTO(complaintRepository.findByStaffIdAndPatientId(staffId, patientId));
	}

}
