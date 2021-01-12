package quince_it.pquince.services.contracts.interfaces;

import java.util.UUID;

import quince_it.pquince.services.contracts.dto.users.UserDTO;
import quince_it.pquince.services.contracts.dto.users.UserRequestDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IUserService extends IService<UserDTO, IdentifiableDTO<UserDTO>> {

	IdentifiableDTO<UserDTO> findByEmail ( String email );
	UUID createPatient(UserRequestDTO entityDTO);
	boolean activatePatientAccount(UUID id);
}
