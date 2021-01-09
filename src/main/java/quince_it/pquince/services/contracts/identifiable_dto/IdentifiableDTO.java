package quince_it.pquince.services.contracts.identifiable_dto;

import java.util.UUID;

public class IdentifiableDTO<T> {
	
	public UUID Id;
	public T EntityDTO;
	
	public IdentifiableDTO(UUID id, T entityDTO) {
		super();
		Id = id;
		EntityDTO = entityDTO;
	}
	
}
