package quince_it.pquince.services.implementation.users;

import java.util.ArrayList;
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
import quince_it.pquince.services.contracts.dto.users.RemoveDermatologistFromPharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.WorkTimeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.users.IWorkTimeService;
import quince_it.pquince.services.implementation.util.users.WorkTimeMapper;

@Service
public class WorkTimeService implements IWorkTimeService{

	@Autowired
	private WorkTimeRepository workTimeRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;

	@Override
	public UUID create(WorkTimeDTO workTimeDTO) {
		// TODO WORKTIME Service:  Implement try catch 

		if(!checkIfStaffHasWorkTimeAtThatTime(workTimeDTO)) {
			return null;
		}
			
		WorkTime newWorkTime = createWorkTimeFromDTO(workTimeDTO);
		
		if(newWorkTime.IsCorrectWorkTimeFormat()) {
			workTimeRepository.save(newWorkTime);
			return newWorkTime.getId();
		}
		
		return null;
	}


	

	@Override
	public List<IdentifiableDTO<WorkTimeDTO>> findWorkTimeForStaff(UUID staffId) {
		// TODO WORKTIME Service: Implement try catch 
		List<IdentifiableDTO<WorkTimeDTO>> retWorkTimes = new ArrayList<IdentifiableDTO<WorkTimeDTO>>();
		
		for(WorkTime workTime : workTimeRepository.findAll()) {
			if(workTime.getStaff().getId().equals(staffId))
				retWorkTimes.add(WorkTimeMapper.MapWorkTimePersistenceToWorkTimeIdentifiableDTO(workTime));
		}
		
		return retWorkTimes;
	}

	private WorkTime createWorkTimeFromDTO(WorkTimeDTO workTimeDTO) {
		Staff forStaff = staffRepository.getOne(workTimeDTO.getForStaff());
		Pharmacy forPharmacy = pharmacyRepository.getOne(workTimeDTO.getForPharmacy());
		return new WorkTime(forPharmacy,forStaff,workTimeDTO.getStartDate(),workTimeDTO.getEndDate(),workTimeDTO.getStartTime(),workTimeDTO.getEndTime());
	}

	private boolean checkIfStaffHasWorkTimeAtThatTime(WorkTimeDTO workTimeDTO) {
		List<WorkTime> workTimes= workTimeRepository.findAll();
		
		for(WorkTime workTime : workTimes) {
			if(workTime.getStaff().getId().equals(workTimeDTO.getForStaff()) && isDateOverlap(workTime,workTimeDTO))
				return false;
		}
		
		return true;
	}
	
	private boolean isDateOverlap(WorkTime workTime, WorkTimeDTO workTimeDTO){
		System.out.println("WORKTIME STARTDATE: "+workTime.getStartDate());
		System.out.println("WORKTIME ENDDATE: "+workTime.getEndDate());
		System.out.println("WORKTIME STARTTIME: "+workTime.getStartTime());
		System.out.println("WORKTIME ENDTIME: "+workTime.getEndTime());
		System.out.println("WORKTIMEDTO STARTDATE: "+workTimeDTO.getStartDate());
		System.out.println("WORKTIMEDTO ENDDATE: "+workTimeDTO.getEndDate());
		System.out.println("WORKTIMEDTO STARTTIME: "+workTimeDTO.getStartTime());
		System.out.println("WORKTIMEDTO ENDTIME: "+workTimeDTO.getEndTime());


	    if(workTime.getStartDate().before(workTimeDTO.getEndDate()) && workTimeDTO.getStartDate().before(workTime.getEndDate())) {
			System.out.println("TESTTTT");

	    	if(workTime.getStartTime()<=workTimeDTO.getEndTime() && workTimeDTO.getStartTime()<=workTime.getEndTime()) {
	    		return true;
	    	}
	    }
	    return false;
	}  
	
	@Override
	public IdentifiableDTO<WorkTimeDTO> findById(UUID id) {
		return null;
	}
	
	@Override
	public void update(WorkTimeDTO workTimeDTO, UUID id) {
		
	}

	@Override
	public boolean delete(UUID id) {
		return false;
	}

	@Override
	public void removeWorkTimeForDermatologistForPharmacy(
			RemoveDermatologistFromPharmacyDTO removeDermatologistFromPharmacyDTO) {

		try {
			List<WorkTime> workTimes = workTimeRepository.findWorkTimesForDermatologistForPharmacy(removeDermatologistFromPharmacyDTO.getDermatologistId(),removeDermatologistFromPharmacyDTO.getPharmacyId());
			workTimeRepository.deleteAll(workTimes);
		}catch(Exception e) {
			
		}
	}

}
