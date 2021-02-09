package quince_it.pquince.services.contracts.dto.drugs;

import java.util.UUID;

public class StaffDrugReservationDTO {
	private UUID patientId;
	private UUID drugInstanceId;
	private int amount;
	public StaffDrugReservationDTO(UUID patientId, UUID drugInstanceId, int amount) {
		super();
		this.patientId = patientId;
		this.drugInstanceId = drugInstanceId;
		this.amount = amount;
	}
	public StaffDrugReservationDTO() {
		super();
	}
	public UUID getPatientId() {
		return patientId;
	}
	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}
	public UUID getDrugInstanceId() {
		return drugInstanceId;
	}
	public void setDrugInstanceId(UUID drugInstanceId) {
		this.drugInstanceId = drugInstanceId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
