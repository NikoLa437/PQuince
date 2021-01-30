package quince_it.pquince.services.contracts.interfaces.users;

import java.util.UUID;

import quince_it.pquince.services.contracts.dto.users.AuthorityDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IAuthorityService extends IService<AuthorityDTO, IdentifiableDTO<AuthorityDTO>> {

	IdentifiableDTO<AuthorityDTO> findByName ( String name );

	String FindRoleByUserId(UUID userId);
}
