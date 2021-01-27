package quince_it.pquince.services.contracts.interfaces.users;

import java.util.UUID;

import quince_it.pquince.services.contracts.dto.users.StaffFeedbackDTO;

public interface IStaffFeedbackService {
		
	double findAvgGradeForStaff(UUID staffId);
	
	void create(StaffFeedbackDTO entityDTO);
	
	void update(StaffFeedbackDTO entityDTO);

	boolean CanPatientGiveFeedback(UUID patientId, UUID staffId);

	StaffFeedbackDTO findByStaffIdAndPatientId(UUID staffId, UUID patientId);

}
