package quince_it.pquince.services.implementation.util.users;

import quince_it.pquince.entities.users.ComplaintStaff;
import quince_it.pquince.services.contracts.dto.users.ComplaintStaffDTO;

public class ComplaintStaffMapper {

	
	public static ComplaintStaffDTO MapStaffComplaintPersistenceToStaffComplaintDTO(ComplaintStaff complaintStaff) {
		if(complaintStaff == null) throw new IllegalArgumentException();
		
		return new ComplaintStaffDTO(complaintStaff.getStaff().getId(), complaintStaff.getDate(), complaintStaff.getText());
	}
}
