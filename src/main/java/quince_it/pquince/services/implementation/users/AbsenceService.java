package quince_it.pquince.services.implementation.users;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.users.Absence;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.repository.users.AbsenceRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.services.contracts.dto.users.AbsenceDTO;
import quince_it.pquince.services.contracts.dto.users.RequestAbsenceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.users.IAbsenceService;

@Service
public class AbsenceService implements IAbsenceService{

	@Autowired
	private AbsenceRepository absenceRepository;
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private UserService userService;
	
	@Override
	public UUID createAbsence(RequestAbsenceDTO requestAbsenceDTO) {
		UUID staffId = userService.getLoggedUserId();
		Staff staff = staffRepository.getOne(staffId);		
		Absence absence = new Absence(staff,getDateWithoutTime(requestAbsenceDTO.getStartDate()),getDateWithoutTime(requestAbsenceDTO.getEndDate()));
		absenceRepository.save(absence);
		return absence.getId();

	}
	
	private Date getDateWithoutTime(Date date) {
		return new Date(date.getYear(), date.getMonth(), date.getDate(),0,0,0);
	}

	@Override
	public List<IdentifiableDTO<AbsenceDTO>> getAbsencesForStaff(UUID staffId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void approveAbsence(UUID absenceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rejectAbsence(UUID absenceId) {
		// TODO Auto-generated method stub
		
	}
	
}
