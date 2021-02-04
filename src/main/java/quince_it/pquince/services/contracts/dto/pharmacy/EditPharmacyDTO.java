package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.UUID;

import quince_it.pquince.entities.users.Address;

public class EditPharmacyDTO {
	private UUID pharmacyId;
	
	private String name;
		
	private Address address;
	
	private String description;

    private double consultationPrice;
    
	public EditPharmacyDTO() {}
	
	public EditPharmacyDTO(UUID pharmacyId,String name, Address address, String description, double consultationPrice) {
		super();
		this.pharmacyId=pharmacyId;
		this.name = name;
		this.address = address;
		this.description = description;
		this.consultationPrice = consultationPrice;
	}

	
	
	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getConsultationPrice() {
		return consultationPrice;
	}

	public void setConsultationPrice(double consultationPrice) {
		this.consultationPrice = consultationPrice;
	}
}
