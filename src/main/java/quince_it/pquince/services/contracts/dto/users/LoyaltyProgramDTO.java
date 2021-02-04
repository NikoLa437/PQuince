package quince_it.pquince.services.contracts.dto.users;

import javax.persistence.Column;

public class LoyaltyProgramDTO {

	private int pointsForAppointment;

	private int pointsForConsulting;

	private int pointsToEnterRegularCathegory;

	private int pointsToEnterSilverCathegory;

	private int pointsToEnterGoldCathegory;

	private int appointmentDiscountRegular;
	
	private int drugDiscountRegular;
	
	private int consultationDiscountRegular;
	
	private int appointmentDiscountSilver;
	
	private int drugDiscountSilver;
	
	private int consultationDiscountSilver;
	
	private int appointmentDiscountGold;
	
	private int drugDiscountGold;
	
	private int consultationDiscountGold;
	
	public LoyaltyProgramDTO() {}
	
	public LoyaltyProgramDTO(int pointsForAppointment, int pointsForConsulting, int pointsToEnterRegularCathegory,
			int pointsToEnterSilverCathegory, int pointsToEnterGoldCathegory, int appointmentDiscountRegular,
			int drugDiscountRegular, int consultationDiscountRegular, int appointmentDiscountSilver,
			int drugDiscountSilver, int consultationDiscountSilver, int appointmentDiscountGold, int drugDiscountGold,
			int consultationDiscountGold) {
		super();
		this.pointsForAppointment = pointsForAppointment;
		this.pointsForConsulting = pointsForConsulting;
		this.pointsToEnterRegularCathegory = pointsToEnterRegularCathegory;
		this.pointsToEnterSilverCathegory = pointsToEnterSilverCathegory;
		this.pointsToEnterGoldCathegory = pointsToEnterGoldCathegory;
		this.appointmentDiscountRegular = appointmentDiscountRegular;
		this.drugDiscountRegular = drugDiscountRegular;
		this.consultationDiscountRegular = consultationDiscountRegular;
		this.appointmentDiscountSilver = appointmentDiscountSilver;
		this.drugDiscountSilver = drugDiscountSilver;
		this.consultationDiscountSilver = consultationDiscountSilver;
		this.appointmentDiscountGold = appointmentDiscountGold;
		this.drugDiscountGold = drugDiscountGold;
		this.consultationDiscountGold = consultationDiscountGold;
	}
	
	public int getPointsForAppointment() {
		return pointsForAppointment;
	}

	public void setPointsForAppointment(int pointsForAppointment) {
		this.pointsForAppointment = pointsForAppointment;
	}

	public int getPointsForConsulting() {
		return pointsForConsulting;
	}

	public void setPointsForConsulting(int pointsForConsulting) {
		this.pointsForConsulting = pointsForConsulting;
	}

	public int getPointsToEnterRegularCathegory() {
		return pointsToEnterRegularCathegory;
	}

	public void setPointsToEnterRegularCathegory(int pointsToEnterRegularCathegory) {
		this.pointsToEnterRegularCathegory = pointsToEnterRegularCathegory;
	}

	public int getPointsToEnterSilverCathegory() {
		return pointsToEnterSilverCathegory;
	}

	public void setPointsToEnterSilverCathegory(int pointsToEnterSilverCathegory) {
		this.pointsToEnterSilverCathegory = pointsToEnterSilverCathegory;
	}

	public int getPointsToEnterGoldCathegory() {
		return pointsToEnterGoldCathegory;
	}

	public void setPointsToEnterGoldCathegory(int pointsToEnterGoldCathegory) {
		this.pointsToEnterGoldCathegory = pointsToEnterGoldCathegory;
	}

	public int getAppointmentDiscountRegular() {
		return appointmentDiscountRegular;
	}

	public void setAppointmentDiscountRegular(int appointmentDiscountRegular) {
		this.appointmentDiscountRegular = appointmentDiscountRegular;
	}

	public int getDrugDiscountRegular() {
		return drugDiscountRegular;
	}

	public void setDrugDiscountRegular(int drugDiscountRegular) {
		this.drugDiscountRegular = drugDiscountRegular;
	}

	public int getConsultationDiscountRegular() {
		return consultationDiscountRegular;
	}

	public void setConsultationDiscountRegular(int consultationDiscountRegular) {
		this.consultationDiscountRegular = consultationDiscountRegular;
	}

	public int getAppointmentDiscountSilver() {
		return appointmentDiscountSilver;
	}

	public void setAppointmentDiscountSilver(int appointmentDiscountSilver) {
		this.appointmentDiscountSilver = appointmentDiscountSilver;
	}

	public int getDrugDiscountSilver() {
		return drugDiscountSilver;
	}

	public void setDrugDiscountSilver(int drugDiscountSilver) {
		this.drugDiscountSilver = drugDiscountSilver;
	}

	public int getConsultationDiscountSilver() {
		return consultationDiscountSilver;
	}

	public void setConsultationDiscountSilver(int consultationDiscountSilver) {
		this.consultationDiscountSilver = consultationDiscountSilver;
	}

	public int getAppointmentDiscountGold() {
		return appointmentDiscountGold;
	}

	public void setAppointmentDiscountGold(int appointmentDiscountGold) {
		this.appointmentDiscountGold = appointmentDiscountGold;
	}

	public int getDrugDiscountGold() {
		return drugDiscountGold;
	}

	public void setDrugDiscountGold(int drugDiscountGold) {
		this.drugDiscountGold = drugDiscountGold;
	}

	public int getConsultationDiscountGold() {
		return consultationDiscountGold;
	}

	public void setConsultationDiscountGold(int consultationDiscountGold) {
		this.consultationDiscountGold = consultationDiscountGold;
	}

}
