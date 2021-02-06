package quince_it.pquince.services.contracts.interfaces.users;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.users.AbsenceDTO;
import quince_it.pquince.services.contracts.dto.users.RequestAbsenceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IAbsenceService {
	UUID createAbsence(RequestAbsenceDTO requestAbsenceDTO);
	List<IdentifiableDTO<AbsenceDTO>> getAbsencesForStaff(UUID staffId);
	void approveAbsence(UUID absenceId);
	void rejectAbsence(UUID absenceId);
}
