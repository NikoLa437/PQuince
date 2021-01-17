package quince_it.pquince.services.implementation.util;

import java.util.ArrayList;
import java.util.List;

import quince_it.pquince.entities.users.Absence;
import quince_it.pquince.services.contracts.dto.users.AbsenceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class AbsenceMapper {
	
	public static IdentifiableDTO<AbsenceDTO> MapAbsencePersistenceToAbsenceIdentifiableDTO(Absence absence){
		if(absence == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<AbsenceDTO>(absence.getId(), new AbsenceDTO(absence.getForStaff().getId(), absence.getStartDate(), absence.getEndDate(), absence.getAbsenceStatus()));
	}
	
	public static List<IdentifiableDTO<AbsenceDTO>> MapAbsencePersistenceListToAbsenceIdentifiableDTOList(List<Absence> absence){
		
		List<IdentifiableDTO<AbsenceDTO>> absenceListDTO = new ArrayList<IdentifiableDTO<AbsenceDTO>>();
		absence.forEach((a) -> absenceListDTO.add(MapAbsencePersistenceToAbsenceIdentifiableDTO(a)));
		return absenceListDTO;
	}
}
