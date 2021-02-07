package quince_it.pquince.services.contracts.dto.pharmacy;

import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class PharmacyDrugPriceDTO {
	
	private IdentifiableDTO<PharmacyDTO> pharmacy;
	
	private int price;
	
	private double grade;

	public PharmacyDrugPriceDTO() {}
	
	public PharmacyDrugPriceDTO(IdentifiableDTO<PharmacyDTO> pharmacy, double grade, int price) {
		super();
		this.pharmacy = pharmacy;
		this.price = price;
		this.grade = grade;
	}

	public IdentifiableDTO<PharmacyDTO> getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(IdentifiableDTO<PharmacyDTO> pharmacy) {
		this.pharmacy = pharmacy;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public double getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
}
