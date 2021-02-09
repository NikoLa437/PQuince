package quince_it.pquince.services.contracts.dto.users;

public class PatientAllergicDTO {
	private boolean isAllergic;

	public PatientAllergicDTO(boolean isAllergic) {
		super();
		this.isAllergic = isAllergic;
	}

	public PatientAllergicDTO() {}
	
	public boolean isAllergic() {
		return isAllergic;
	}

	public void setAllergic(boolean isAllergic) {
		this.isAllergic = isAllergic;
	}
	
	
}
