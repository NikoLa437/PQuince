package quince_it.pquince.services.implementation.users;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Absence;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.repository.users.AbsenceRepository;
import quince_it.pquince.repository.users.PharmacistRepository;
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
	private PharmacistRepository pharmacistRepository;
	@Autowired
	private UserService userService;
	
	@Override
	public UUID createAbsence(RequestAbsenceDTO requestAbsenceDTO) {
		UUID staffId = userService.getLoggedUserId();
		Staff staff = staffRepository.getOne(staffId);	
		Pharmacy pharmacy = null;
		if(staff.getStaffType() == StaffType.PHARMACIST)
			pharmacy = pharmacistRepository.getOne(staffId).getPharmacy();
		else if (staff.getStaffType() == StaffType.DERMATOLOGIST)
			pharmacy = userService.getPharmacyForLoggedDermatologist();
		Absence absence = new Absence(staff,pharmacy,getDateWithoutTime(requestAbsenceDTO.getStartDate()),getDateWithoutTime(requestAbsenceDTO.getEndDate()));
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
