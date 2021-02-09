package quince_it.pquince.services.implementation.util.users;

import quince_it.pquince.entities.users.StaffFeedback;
import quince_it.pquince.services.contracts.dto.users.StaffFeedbackDTO;

public class StaffFeedbackMapper {

	
	public static StaffFeedbackDTO MapStaffFeedbackPersistenceToStaffFeedbackDTO(StaffFeedback staffFeedback) {
		if(staffFeedback == null) throw new IllegalArgumentException();
		
		return new StaffFeedbackDTO(staffFeedback.getStaff().getId(), staffFeedback.getDate(), staffFeedback.getGrade());
	}
}
