package quince_it.pquince.services.implementation.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.repository.users.StaffFeedbackRepository;
import quince_it.pquince.services.contracts.dto.users.IdentifiableStaffGradeDTO;
import quince_it.pquince.services.contracts.interfaces.users.IStaffFeedbackService;

public class StaffFeedbackService implements IStaffFeedbackService{

	@Autowired
	private StaffFeedbackRepository	staffFeedbackRepository;
	
	@Override
	public List<IdentifiableStaffGradeDTO> findAllStaffWithAvgGradeByStaffType(StaffType staffType) {
		return staffFeedbackRepository.findAllStaffWithAvgGradeByStaffType(staffType);
	}

}
