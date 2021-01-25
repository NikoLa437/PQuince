package quince_it.pquince.services.contracts.interfaces.users;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.users.WorkTimeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IWorkTimeService {
	IdentifiableDTO<WorkTimeDTO> findById(UUID id);
	UUID create(WorkTimeDTO workTimeDTO);
	void update(WorkTimeDTO workTimeDTO, UUID id);
    boolean delete(UUID id);
    List<IdentifiableDTO<WorkTimeDTO>> findWorkTimeForStaff(UUID staffId);
}
