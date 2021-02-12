package quince_it.pquince.services.contracts.dto.appointment;

import java.util.Date;

import quince_it.pquince.services.contracts.dto.users.StaffGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class DermatologistAppointmentDTO {

	private IdentifiableDTO<StaffGradeDTO> staff;
	
    private Date startDateTime;
    
    private Date endDateTime;
    
    private double price;
    
    private double discountPrice;
    
    private TreatmentReportDTO treatmentRaport;
    
    public DermatologistAppointmentDTO() {}

	public DermatologistAppointmentDTO(IdentifiableDTO<StaffGradeDTO> staff, Date startDateTime, Date endDateTime, double price, double discountPrice) {
		super();
		this.staff = staff;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.price = price;
		this.discountPrice = discountPrice;
	}

	public IdentifiableDTO<StaffGradeDTO> getStaff() {
		return staff;
	}

	public void setStaff(IdentifiableDTO<StaffGradeDTO> staff) {
		this.staff = staff;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public TreatmentReportDTO getTreatmentRaport() {
		return treatmentRaport;
	}

	public void setTreatmentRaport(TreatmentReportDTO treatmentRaport) {
		this.treatmentRaport = treatmentRaport;
	}
}
