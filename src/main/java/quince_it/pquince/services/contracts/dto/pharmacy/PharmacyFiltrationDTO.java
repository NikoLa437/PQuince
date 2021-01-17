package quince_it.pquince.services.contracts.dto.pharmacy;

public class PharmacyFiltrationDTO {

	private String name;
	
	private double gradeFrom;
	
	private double gradeTo;
	
	private double distanceFrom;
	
	private double distanceTo;

	public PharmacyFiltrationDTO() {}
		
	public PharmacyFiltrationDTO(String name, double gradeFrom, double gradeTo, double distanceFrom,
			double distanceTo) {
		super();
		this.name = name;
		this.gradeFrom = gradeFrom;
		this.gradeTo = gradeTo;
		this.distanceFrom = distanceFrom;
		this.distanceTo = distanceTo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getGradeFrom() {
		return gradeFrom;
	}

	public void setGradeFrom(double gradeFrom) {
		this.gradeFrom = gradeFrom;
	}

	public double getGradeTo() {
		return gradeTo;
	}

	public void setGradeTo(double gradeTo) {
		this.gradeTo = gradeTo;
	}

	public double getDistanceFrom() {
		return distanceFrom;
	}

	public void setDistanceFrom(double distanceFrom) {
		this.distanceFrom = distanceFrom;
	}

	public double getDistanceTo() {
		return distanceTo;
	}

	public void setDistanceTo(double distanceTo) {
		this.distanceTo = distanceTo;
	}
	
	
	
	

}
