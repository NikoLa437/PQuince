package quince_it.pquince.services.contracts.interfaces.users;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.users.ComplaintPharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.ComplaintStaffDTO;
import quince_it.pquince.services.contracts.exceptions.ComplaintsNotAllowedException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IComplaintService {
	
	UUID create(ComplaintStaffDTO entityDTO) throws ComplaintsNotAllowedException;
	
	void update(ComplaintStaffDTO complaintStaffDTO);
	
	ComplaintStaffDTO findByStaffIdAndPatientId(UUID staffId, UUID patientId);

	List<IdentifiableDTO<ComplaintStaffDTO>> findAll();

	void CanPatientGiveFeedback(UUID patientId, UUID staffId) throws ComplaintsNotAllowedException;

	List<IdentifiableDTO<ComplaintPharmacyDTO>> findAllPharmacy();

	void replyComplaint(UUID id, String reply, String email);

	void replyComplaintPharmacy(UUID id, String reply);

}
