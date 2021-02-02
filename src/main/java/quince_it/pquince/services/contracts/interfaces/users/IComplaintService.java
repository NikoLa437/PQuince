package quince_it.pquince.services.contracts.interfaces.users;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.users.ComplaintStaffDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IComplaintService {
	
	void create(ComplaintStaffDTO entityDTO);
	
	void update(ComplaintStaffDTO complaintStaffDTO);
	
	boolean CanPatientGiveComplaint(UUID patientId, UUID staffId);
	
	ComplaintStaffDTO findByStaffIdAndPatientId(UUID staffId, UUID patientId);

	List<IdentifiableDTO<ComplaintStaffDTO>> findAll();

}
