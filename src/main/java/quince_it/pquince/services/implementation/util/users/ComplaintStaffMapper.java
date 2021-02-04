package quince_it.pquince.services.implementation.util.users;

import quince_it.pquince.entities.users.ComplaintStaff;
import quince_it.pquince.services.contracts.dto.users.ComplaintStaffDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class ComplaintStaffMapper {

	public static IdentifiableDTO<ComplaintStaffDTO> MapComplaintStaffPersistenceToComplaintStaffIdentifiableDTO(ComplaintStaff complaintStaff){
		if(complaintStaff == null) throw new IllegalArgumentException();
				
		return new IdentifiableDTO<ComplaintStaffDTO>(complaintStaff.getId(),new ComplaintStaffDTO(complaintStaff.getStaff().getId(), complaintStaff.getDate(), complaintStaff.getText(), complaintStaff.getStaffName(), complaintStaff.getStaffSurname(), complaintStaff.getProfession(), complaintStaff.getReply(), complaintStaff.getEmail()));
	}
	
	public static ComplaintStaffDTO MapStaffComplaintPersistenceToStaffComplaintDTO(ComplaintStaff complaintStaff) {
		if(complaintStaff == null) throw new IllegalArgumentException();
		
		return new ComplaintStaffDTO(complaintStaff.getStaff().getId(), complaintStaff.getDate(), complaintStaff.getText(), complaintStaff.getStaffName(), complaintStaff.getStaffSurname(), complaintStaff.getProfession(), complaintStaff.getReply(), complaintStaff.getEmail());
	}
}
