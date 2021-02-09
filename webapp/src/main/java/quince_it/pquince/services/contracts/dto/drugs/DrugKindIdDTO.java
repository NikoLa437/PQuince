package quince_it.pquince.services.contracts.dto.drugs;

public class DrugKindIdDTO {
	
	
	private String type;
	
	public DrugKindIdDTO() {}
	
	public DrugKindIdDTO(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	public void setName(String type) {
		this.type = type;
	}
}
