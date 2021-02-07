package quince_it.pquince.services.contracts.interfaces.users;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.users.ComplaintStaffDTO;
import quince_it.pquince.services.contracts.exceptions.ComplaintsNotAllowedException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IComplaintService {
	
	void create(ComplaintStaffDTO entityDTO) throws ComplaintsNotAllowedException;
	
	void update(ComplaintStaffDTO complaintStaffDTO);
	
	ComplaintStaffDTO findByStaffIdAndPatientId(UUID staffId, UUID patientId);

	List<IdentifiableDTO<ComplaintStaffDTO>> findAll();

	void replyComplaint(UUID id, String reply, String em);

	void CanPatientGiveFeedback(UUID patientId, UUID staffId) throws ComplaintsNotAllowedException;

}
