package quince_it.pquince.services.contracts.dto.drugs;

import java.util.Date;

import quince_it.pquince.entities.drugs.ReservationStatus;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class DrugReservationDTO {
	
    private IdentifiableDTO<PharmacyDTO> pharmacy;
    
    private IdentifiableDTO<DrugInstanceDTO> drugInstance;
    
    private int amount;
	
    private Date startDate;
	
    private Date endDate;
	
	private double drugPeacePrice;

	private ReservationStatus reservationStatus;

	public DrugReservationDTO() {}
	
	public DrugReservationDTO(IdentifiableDTO<PharmacyDTO> pharmacy, IdentifiableDTO<DrugInstanceDTO> drugInstance, int amount, Date startDate,
			Date endDate, double drugPeacePrice, ReservationStatus reservationStatus) {
		super();
		this.pharmacy = pharmacy;
		this.drugInstance = drugInstance;
		this.amount = amount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.drugPeacePrice = drugPeacePrice;
		this.reservationStatus = reservationStatus;
	}

	public IdentifiableDTO<PharmacyDTO> getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(IdentifiableDTO<PharmacyDTO> pharmacy) {
		this.pharmacy = pharmacy;
	}

	public IdentifiableDTO<DrugInstanceDTO> getDrugInstance() {
		return drugInstance;
	}

	public void setDrugInstance(IdentifiableDTO<DrugInstanceDTO> drugInstance) {
		this.drugInstance = drugInstance;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getDrugPeacePrice() {
		return drugPeacePrice;
	}

	public void setDrugPeacePrice(double drugPeacePrice) {
		this.drugPeacePrice = drugPeacePrice;
	}

	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	
}
