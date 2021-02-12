package quince_it.pquince.entities.users;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;


@Entity
@Table(name="loyaltyprogram")
public class LoyaltyProgram {
	
	@Id
    @Column(name = "id")
	private UUID id;
	
	@Column(name = "pointsForAppointment")
	private int pointsForAppointment;

	@Column(name = "pointsForConsulting")
	private int pointsForConsulting;

	@Column(name = "pointsToEnterRegularCathegory")
	private int pointsToEnterRegularCathegory;

	@Column(name = "pointsToEnterSilverCathegory")
	private int pointsToEnterSilverCathegory;

	@Column(name = "pointsToEnterGoldCathegory")
	private int pointsToEnterGoldCathegory;
	
	@Column(name = "appointmentDiscountRegular")
	private int appointmentDiscountRegular;
	
	@Column(name = "drugDiscountRegular")
	private int drugDiscountRegular;
	
	@Column(name = "consultationDiscountRegular")
	private int consultationDiscountRegular;
	
	@Column(name = "appointmentDiscountSilver")
	private int appointmentDiscountSilver;
	
	@Column(name = "drugDiscountSilver")
	private int drugDiscountSilver;
	
	@Column(name = "consultationDiscountSilver")
	private int consultationDiscountSilver;
	
	@Column(name = "appointmentDiscountGold")
	private int appointmentDiscountGold;
	
	@Column(name = "drugDiscountGold")
	private int drugDiscountGold;
	
	@Column(name = "consultationDiscountGold")
	private int consultationDiscountGold;
	
	@Version
	private Long version;
	
	public LoyaltyProgram() {}
	
	public LoyaltyProgram(int pointsForAppointment, int pointsForConsulting, int pointsToEnterRegularCathegory,
			int pointsToEnterSilverCathegory, int pointsToEnterGoldCathegory, int appointmentDiscountRegular,
			int drugDiscountRegular, int consultationDiscountRegular, int appointmentDiscountSilver,
			int drugDiscountSilver, int consultationDiscountSilver, int appointmentDiscountGold, int drugDiscountGold,
			int consultationDiscountGold)  {
		this(UUID.randomUUID(), pointsForAppointment, pointsForConsulting, pointsToEnterRegularCathegory,pointsToEnterSilverCathegory , pointsToEnterGoldCathegory
				 ,appointmentDiscountRegular, drugDiscountRegular, consultationDiscountRegular, appointmentDiscountSilver,
					drugDiscountSilver, consultationDiscountSilver, appointmentDiscountGold,  drugDiscountGold, consultationDiscountGold);
	}
	

	public LoyaltyProgram(UUID id, int pointsForAppointment, int pointsForConsulting, int pointsToEnterRegularCathegory,
			int pointsToEnterSilverCathegory, int pointsToEnterGoldCathegory, int appointmentDiscountRegular,
			int drugDiscountRegular, int consultationDiscountRegular, int appointmentDiscountSilver,
			int drugDiscountSilver, int consultationDiscountSilver, int appointmentDiscountGold, int drugDiscountGold,
			int consultationDiscountGold) {
		super();
		this.id = id;
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
	
	public UUID getId() {
		return id;
	}
	

	public Long getVersion() {
		return version;
	}

	public int getPointsForAppointment() {
		return this.pointsForAppointment;
	}

	public void setPointsForAppointment(int pointsForAppointment) {
		this.pointsForAppointment = pointsForAppointment;
	}

	public int getPointsForConsulting() {
		return this.pointsForConsulting;
	}

	public void setPointsForConsulting(int pointsForConsulting) {
		this.pointsForConsulting = pointsForConsulting;
	}

	public int getPointsToEnterRegularCathegory() {
		return this.pointsToEnterRegularCathegory;
	}

	public void setPointsToEnterRegularCathegory(int pointsToEnterRegularCathegory) {
		this.pointsToEnterRegularCathegory = pointsToEnterRegularCathegory;
	}

	public int getPointsToEnterSilverCathegory() {
		return this.pointsToEnterSilverCathegory;
	}

	public void setPointsToEnterSilverCathegory(int pointsToEnterSilverCathegory) {
		this.pointsToEnterSilverCathegory = pointsToEnterSilverCathegory;
	}

	public int getPointsToEnterGoldCathegory() {
		return this.pointsToEnterGoldCathegory;
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
