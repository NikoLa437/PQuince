package quince_it.pquince.services.implementation.users;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.WorkTime;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.repository.users.WorkTimeRepository;
import quince_it.pquince.services.contracts.dto.users.WorkTimeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.users.IWorkTimeService;

@Service
public class WorkTimeService implements IWorkTimeService{

	@Autowired
	private WorkTimeRepository workTimeRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Override
	public IdentifiableDTO<WorkTimeDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(WorkTimeDTO workTimeDTO) {
		Staff forStaff = staffRepository.getOne(workTimeDTO.getForStaff());
		Pharmacy pharmacy = pharmacyRepository.getOne(workTimeDTO.getPharmacyId());
		WorkTime newWorkTime = new WorkTime(forStaff,pharmacy,workTimeDTO.getStartDate(),workTimeDTO.getEndDate(),workTimeDTO.getStartTime(),workTimeDTO.getEndTime());
		workTimeRepository.save(newWorkTime);
		return newWorkTime.getId();
	}

	@Override
	public void update(WorkTimeDTO workTimeDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IdentifiableDTO<WorkTimeDTO>> findWorkTimeForStaff(UUID staffId) {
		// TODO Auto-generated method stub
		return null;
	}

}
