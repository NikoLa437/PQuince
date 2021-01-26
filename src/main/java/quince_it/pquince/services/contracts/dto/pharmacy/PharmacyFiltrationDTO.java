package quince_it.pquince.services.contracts.dto.pharmacy;

public class PharmacyFiltrationDTO {

	private String name;
	
	private String city;
	
	private double gradeFrom;
	
	private double gradeTo;
	
	private double distanceFrom;
	
	private double distanceTo;
	
	private double latitude;
	
	private double longitude;

	public PharmacyFiltrationDTO() {}

	public PharmacyFiltrationDTO(String name, String city, double gradeFrom, double gradeTo, double distanceFrom,
			double distanceTo, double latitude, double longitude) {
		super();
		this.name = name;
		this.city = city;
		this.gradeFrom = gradeFrom;
		this.gradeTo = gradeTo;
		this.distanceFrom = distanceFrom;
		this.distanceTo = distanceTo;
		this.latitude = latitude;
		this.longitude = longitude;
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}
	
	
	
	

}
