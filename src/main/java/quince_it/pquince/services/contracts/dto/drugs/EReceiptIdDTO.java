package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class EReceiptIdDTO {
	
	private UUID id;

	public EReceiptIdDTO(UUID id) {
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
