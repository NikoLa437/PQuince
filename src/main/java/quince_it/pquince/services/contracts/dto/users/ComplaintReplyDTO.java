package quince_it.pquince.services.contracts.dto.users;

import java.util.UUID;

public class ComplaintReplyDTO {

	UUID id;
	
	String reply;
	
	String email;

	public ComplaintReplyDTO() {}
	
	public ComplaintReplyDTO(UUID id, String reply) {
		this.id = id;
		this.reply = reply;
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
