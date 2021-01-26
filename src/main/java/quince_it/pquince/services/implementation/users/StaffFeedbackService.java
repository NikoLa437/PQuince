package quince_it.pquince.services.implementation.users;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.repository.users.StaffFeedbackRepository;
import quince_it.pquince.services.contracts.dto.users.IdentifiableStaffGradeDTO;
import quince_it.pquince.services.contracts.interfaces.users.IStaffFeedbackService;


@Service
public class StaffFeedbackService implements IStaffFeedbackService{

	@Autowired
	private StaffFeedbackRepository	staffFeedbackRepository;
	
	@Override
	public List<IdentifiableStaffGradeDTO> findAllStaffWithAvgGradeByStaffType(StaffType staffType) {
		return staffFeedbackRepository.findAllStaffWithAvgGradeByStaffType(staffType);
	}

	@Override
	public double findAvgGradeForStaff(UUID staffId) {
		try {
			double retVal = staffFeedbackRepository.findAvgGradeForStaff(staffId);
			return retVal;
		}catch (Exception e) {
			return 0;
		}
	}

}
