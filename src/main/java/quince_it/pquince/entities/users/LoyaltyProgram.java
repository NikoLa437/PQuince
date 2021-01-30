package quince_it.pquince.entities.users;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="loyaltyprogram")
public class LoyaltyProgram {

	private static final long serialVersionUID = 1L;
	
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

	public LoyaltyProgram() {}
	
	public LoyaltyProgram(int pointsForAppointment, int pointsForConsulting, int pointsToEnterRegularCathegory,
			int pointsToEnterSilverCathegory, int pointsToEnterGoldCathegory) {
		this(UUID.randomUUID(), pointsForAppointment, pointsForConsulting, pointsToEnterRegularCathegory,pointsToEnterSilverCathegory , pointsToEnterGoldCathegory);
	}
	
	public LoyaltyProgram(UUID id, int pointsForAppointment, int pointsForConsulting, int pointsToEnterRegularCathegory,
			int pointsToEnterSilverCathegory, int pointsToEnterGoldCathegory) {
		super();
		this.id = id;
		this.pointsForAppointment = pointsForAppointment;
		this.pointsForConsulting = pointsForConsulting;
		this.pointsToEnterRegularCathegory = pointsToEnterRegularCathegory;
		this.pointsToEnterSilverCathegory = pointsToEnterSilverCathegory;
		this.pointsToEnterGoldCathegory = pointsToEnterGoldCathegory;
	}

	public UUID getId() {
		return id;
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
	
	

}
