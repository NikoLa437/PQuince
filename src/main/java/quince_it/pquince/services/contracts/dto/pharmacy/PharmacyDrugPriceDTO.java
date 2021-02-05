package quince_it.pquince.services.contracts.dto.pharmacy;

import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class PharmacyDrugPriceDTO {
	
	private IdentifiableDTO<PharmacyDTO> pharmacy;
	
	private int price;

	public PharmacyDrugPriceDTO() {}
	
	public PharmacyDrugPriceDTO(IdentifiableDTO<PharmacyDTO> pharmacy, int price) {
		super();
		this.pharmacy = pharmacy;
		this.price = price;
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
	
}
