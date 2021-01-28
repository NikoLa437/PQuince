package quince_it.pquince.services.contracts.dto.users;

public class PharmacistForPharmacyGradeDTO {

	private String name;
    
	private String surname;
    	    	    
	private double grade;
   
	public PharmacistForPharmacyGradeDTO() {}
	
	public PharmacistForPharmacyGradeDTO(String name, String surname, double grade) {
		super();
		this.name = name;
		this.surname = surname;
		this.grade=grade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}
}
