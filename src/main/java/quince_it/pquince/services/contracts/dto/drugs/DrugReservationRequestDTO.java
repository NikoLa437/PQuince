package quince_it.pquince.services.contracts.dto.drugs;

import java.util.Date;
import java.util.UUID;

public class DrugReservationRequestDTO {
	
	private UUID drugId;
	
	private UUID pharmacyId;
	
	private int drugAmount;
	
	private double drugPrice;
	
	private Date endDate;
	
	public DrugReservationRequestDTO() {}

	public DrugReservationRequestDTO(UUID drugId, UUID pharmacyId, int drugAmount, double drugPrice, Date endDate) {
		super();
		this.drugId = drugId;
		this.pharmacyId = pharmacyId;
		this.drugAmount = drugAmount;
		this.drugPrice = drugPrice;
		this.endDate = endDate;
	}

	public UUID getDrugId() {
		return drugId;
	}

	public void setDrugId(UUID drugId) {
		this.drugId = drugId;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public int getDrugAmount() {
		return drugAmount;
	}

	public void setDrugAmount(int drugAmount) {
		this.drugAmount = drugAmount;
	}

	public double getDrugPrice() {
		return drugPrice;
	}

	public void setDrugPrice(double drugPrice) {
		this.drugPrice = drugPrice;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
