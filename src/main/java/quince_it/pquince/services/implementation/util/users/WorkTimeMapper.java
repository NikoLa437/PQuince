package quince_it.pquince.services.implementation.util.users;

import java.util.ArrayList;
import java.util.List;

import quince_it.pquince.entities.users.WorkTime;
import quince_it.pquince.services.contracts.dto.users.WorkTimeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class WorkTimeMapper {
	
	public static IdentifiableDTO<WorkTimeDTO> MapWorkTimePersistenceToWorkTimeIdentifiableDTO(WorkTime workTime){
		if(workTime == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<WorkTimeDTO>(workTime.getId(), new WorkTimeDTO(workTime.getStaff().getId(),workTime.getPharmacy().getId(), workTime.getStartDate(), workTime.getEndDate(), workTime.getStartTime(),workTime.getEndTime()));
	}
	
	public static List<IdentifiableDTO<WorkTimeDTO>> MapWorkTimePersistenceListToWorkTimeIdentifiableDTOList(List<WorkTime> workTimes){
		
		List<IdentifiableDTO<WorkTimeDTO>> workTimesDTO = new ArrayList<IdentifiableDTO<WorkTimeDTO>>();
		workTimes.forEach((a) -> workTimesDTO.add(MapWorkTimePersistenceToWorkTimeIdentifiableDTO(a)));
		return workTimesDTO;
	}
}
