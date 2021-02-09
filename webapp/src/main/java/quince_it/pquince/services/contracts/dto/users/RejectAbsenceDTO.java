package quince_it.pquince.services.contracts.dto.users;

import java.util.UUID;

public class RejectAbsenceDTO {
	private UUID id;
	private String reason;
	
	public RejectAbsenceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RejectAbsenceDTO(UUID id, String reason) {
		super();
		this.id = id;
		this.reason = reason;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
