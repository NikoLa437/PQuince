package quince_it.pquince.entities.drugs;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class DrugKindId {

	@Id
	private UUID id;
	
	@Column(name = "type", nullable = false, unique = true)
	private String type;
	
	public DrugKindId() {}
	
	public DrugKindId(String type) {
		this(UUID.randomUUID(), type);
	}
	
	public DrugKindId(UUID id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setName(String type) {
		this.type = type;
	}
}
