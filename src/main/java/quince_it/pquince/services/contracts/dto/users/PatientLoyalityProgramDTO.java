package quince_it.pquince.services.contracts.dto.users;

import quince_it.pquince.entities.users.LoyalityCategory;

public class PatientLoyalityProgramDTO {

	private LoyalityCategory loyalityCategory;
	
	private int appointmentDiscount;
	
	private int consultationDiscount;

	private int drugDiscount;

	public PatientLoyalityProgramDTO() {}
	
	public PatientLoyalityProgramDTO(LoyalityCategory loyalityCategory, int appointmentDiscount,
			int consultationDiscount, int drugDiscount) {
		super();
		this.loyalityCategory = loyalityCategory;
		this.appointmentDiscount = appointmentDiscount;
		this.consultationDiscount = consultationDiscount;
		this.drugDiscount = drugDiscount;
	}

	public LoyalityCategory getLoyalityCategory() {
		return loyalityCategory;
	}

	public void setLoyalityCategory(LoyalityCategory loyalityCategory) {
		this.loyalityCategory = loyalityCategory;
	}

	public int getAppointmentDiscount() {
		return appointmentDiscount;
	}

	public void setAppointmentDiscount(int appointmentDiscount) {
		this.appointmentDiscount = appointmentDiscount;
	}

	public int getConsultationDiscount() {
		return consultationDiscount;
	}

	public void setConsultationDiscount(int consultationDiscount) {
		this.consultationDiscount = consultationDiscount;
	}

	public int getDrugDiscount() {
		return drugDiscount;
	}

	public void setDrugDiscount(int drugDiscount) {
		this.drugDiscount = drugDiscount;
	}
		
}
