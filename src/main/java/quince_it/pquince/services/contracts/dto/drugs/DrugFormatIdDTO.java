package quince_it.pquince.services.contracts.dto.drugs;

public class DrugFormatIdDTO {
	
	private String type;
	
	public DrugFormatIdDTO() {}
	
	public DrugFormatIdDTO(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	public void setName(String type) {
		this.type = type;
	}
}
