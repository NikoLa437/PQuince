package quince_it.pquince.services.contracts.dto.users;


public class LoyaltyProgramDTO {

	private int pointsForAppointment;

	private int pointsForConsulting;

	private int pointsToEnterRegularCathegory;

	private int pointsToEnterSilverCathegory;

	private int pointsToEnterGoldCathegory;

	public LoyaltyProgramDTO() {}
	
	public LoyaltyProgramDTO(int pointsForAppointment, int pointsForConsulting, int pointsToEnterRegularCathegory,
			int pointsToEnterSilverCathegory, int pointsToEnterGoldCathegory) {
		super();
		this.pointsForAppointment = pointsForAppointment;
		this.pointsForConsulting = pointsForConsulting;
		this.pointsToEnterRegularCathegory = pointsToEnterRegularCathegory;
		this.pointsToEnterSilverCathegory = pointsToEnterSilverCathegory;
		this.pointsToEnterGoldCathegory = pointsToEnterGoldCathegory;
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

}
