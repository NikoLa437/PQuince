package quince_it.pquince.services.contracts.interfaces.users;

import java.util.UUID;

import quince_it.pquince.services.contracts.dto.users.StaffFeedbackDTO;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;

public interface IStaffFeedbackService {
		
	double findAvgGradeForStaff(UUID staffId);
	
	void create(StaffFeedbackDTO entityDTO) throws FeedbackNotAllowedException;
	
	void update(StaffFeedbackDTO entityDTO);

	void CanPatientGiveFeedback(UUID patientId, UUID staffId) throws FeedbackNotAllowedException ;

	StaffFeedbackDTO findByStaffIdAndPatientId(UUID staffId);

}
