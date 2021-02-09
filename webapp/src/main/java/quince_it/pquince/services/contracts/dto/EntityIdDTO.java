package quince_it.pquince.services.contracts.dto;

import java.util.UUID;

public class EntityIdDTO {

	private UUID id;
	
	public EntityIdDTO() {}

	public EntityIdDTO(UUID id) {
		super();
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
