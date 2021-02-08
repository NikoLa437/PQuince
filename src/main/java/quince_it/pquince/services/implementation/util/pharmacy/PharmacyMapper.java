package quince_it.pquince.services.implementation.util.pharmacy;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDrugPriceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class PharmacyMapper {

	public static IdentifiableDTO<PharmacyDTO> MapPharmacyPersistenceToPharmacyIdentifiableDTO(Pharmacy pharmacy){
		if(pharmacy == null) throw new IllegalArgumentException();

		return new IdentifiableDTO<PharmacyDTO>(pharmacy.getId(), new PharmacyDTO(pharmacy.getName(), pharmacy.getAddress(), pharmacy.getDescription(), pharmacy.getConsultationPrice()));

	}
	
	public static IdentifiableDTO<PharmacyDrugPriceDTO> MapPharmacyPersistenceToPharmacyDrugPriceIdentifiableDTO(Pharmacy pharmacy, double grade, double price){
		if(pharmacy == null) throw new IllegalArgumentException();

		return new IdentifiableDTO<PharmacyDrugPriceDTO>(pharmacy.getId(), new PharmacyDrugPriceDTO(MapPharmacyPersistenceToPharmacyIdentifiableDTO(pharmacy),grade, price));

	}
}
