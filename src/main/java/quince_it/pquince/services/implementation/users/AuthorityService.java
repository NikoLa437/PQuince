package quince_it.pquince.services.implementation.users;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.users.Authority;
import quince_it.pquince.repository.users.AuthorityRepository;
import quince_it.pquince.services.contracts.dto.users.AuthorityDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.users.IAuthorityService;
import quince_it.pquince.services.implementation.util.users.AuthorityMapper;

@Service
public class AuthorityService implements IAuthorityService{

	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public List<IdentifiableDTO<AuthorityDTO>> findAll() {
		return null;
	}

	@Override
	public IdentifiableDTO<AuthorityDTO> findById(UUID id) {
		return null;
	}

	@Override
	public UUID create(AuthorityDTO entityDTO) {
		return null;
	}

	@Override
	public void update(AuthorityDTO entityDTO, UUID id) {
		
	}

	@Override
	public boolean delete(UUID id) {
		return false;
	}

	@Override
	public IdentifiableDTO<AuthorityDTO> findByName(String name) {
		Authority authority = authorityRepository.findByName(name);
		System.out.println(authority.getName() + "NAMEEEE");
		if (authority == null)
			return null;
		return AuthorityMapper.MapAuthorityPersistenceToAuthorityIdentifiableDTO(authority);
	}

	@Override
	public String FindRoleByUserId(UUID userId) {
		return null;
	}
	

}
