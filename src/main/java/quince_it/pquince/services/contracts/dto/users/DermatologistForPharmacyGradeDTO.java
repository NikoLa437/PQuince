package quince_it.pquince.services.contracts.dto.users;


public class DermatologistForPharmacyGradeDTO {
	private String email;

	private String name;
    
	private String surname;
    	    
	private String phoneNumber;
	    
	private double grade;
   

	public DermatologistForPharmacyGradeDTO(String email, String name, String surname, String phoneNumber,double grade) {
		super();
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.grade=grade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}
}
