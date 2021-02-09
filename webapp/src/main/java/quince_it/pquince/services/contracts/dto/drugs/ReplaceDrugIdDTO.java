package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class ReplaceDrugIdDTO {
	
	private UUID id;

	private UUID replacement_id;
	
	public ReplaceDrugIdDTO() {}

	public ReplaceDrugIdDTO(UUID id, UUID replacement_id) {
		super();
		this.id = id;
		this.replacement_id = replacement_id;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getReplacement_id() {
		return replacement_id;
	}

	public void setReplacement_id(UUID replacement_id) {
		this.replacement_id = replacement_id;
	}
	
}
