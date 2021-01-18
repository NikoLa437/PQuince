package quince_it.pquince.services.implementation.users;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.users.Absence;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.repository.users.AbsenceRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.services.contracts.dto.users.AbsenceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IAbsenceService;

@Service
public class AbsenceService implements IAbsenceService{

	@Autowired
	private AbsenceRepository absenceRepository;
	@Autowired
	private StaffRepository staffRepository;
	
	@Override
	public UUID createAbsence(AbsenceDTO absenceDTO) {
		Staff forStaff = staffRepository.getOne(absenceDTO.getForStaff());
		Absence newAbsence = new Absence(forStaff,absenceDTO.getStartDate(),absenceDTO.getEndDate());
		absenceRepository.save(newAbsence);
		return newAbsence.getId();

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
