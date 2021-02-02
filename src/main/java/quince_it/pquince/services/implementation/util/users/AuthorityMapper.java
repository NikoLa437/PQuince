package quince_it.pquince.services.implementation.util.users;

import quince_it.pquince.entities.users.Authority;
import quince_it.pquince.services.contracts.dto.users.AuthorityDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class AuthorityMapper {
	
	public static IdentifiableDTO<AuthorityDTO> MapAuthorityPersistenceToAuthorityIdentifiableDTO(Authority authority){
		if(authority == null) throw new IllegalArgumentException();

		
		return new IdentifiableDTO<AuthorityDTO>(authority.getId(), new AuthorityDTO(authority.getName()));
		
	}
}
