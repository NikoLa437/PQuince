package quince_it.pquince.services.contracts.interfaces.users;

import java.util.List;

import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.services.contracts.dto.users.IdentifiableStaffGradeDTO;

public interface IStaffFeedbackService {
	
	List<IdentifiableStaffGradeDTO> findAllStaffWithAvgGradeByStaffType(StaffType staffType);
}
