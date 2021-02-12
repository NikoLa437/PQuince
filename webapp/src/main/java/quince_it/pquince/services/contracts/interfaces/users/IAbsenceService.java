package quince_it.pquince.services.contracts.interfaces.users;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import quince_it.pquince.services.contracts.dto.EntityIdDTO;
import quince_it.pquince.services.contracts.dto.users.AbsenceDTO;
import quince_it.pquince.services.contracts.dto.users.AbsenceResponseDTO;
import quince_it.pquince.services.contracts.dto.users.RejectAbsenceDTO;
import quince_it.pquince.services.contracts.dto.users.RequestAbsenceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IAbsenceService {
	UUID createAbsence(RequestAbsenceDTO requestAbsenceDTO);
	List<IdentifiableDTO<AbsenceDTO>> getAbsencesForStaff(UUID staffId);
	void approveAbsence(UUID absenceId);
	void rejectAbsence(UUID absenceId);
	List<IdentifiableDTO<AbsenceResponseDTO>> getAbsencesForPharmacy(UUID pharmacyId);
	boolean approveAbsence(EntityIdDTO pharmacyIdDTO) throws MessagingException;
	void rejectAbsence(RejectAbsenceDTO rejectAbsenceDTO) throws MessagingException;
	public List<IdentifiableDTO<AbsenceDTO>> getAbsencesAsStaff();
}
