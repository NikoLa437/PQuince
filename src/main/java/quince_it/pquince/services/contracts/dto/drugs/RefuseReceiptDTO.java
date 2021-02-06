package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class RefuseReceiptDTO {
	
	private UUID id;
	
	public RefuseReceiptDTO() {}
	
	public RefuseReceiptDTO(UUID id) {
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
