package quince_it.pquince.services.implementation.util.users;

import quince_it.pquince.entities.users.Offer;
import quince_it.pquince.services.contracts.dto.users.OfferDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class OfferMapper {

	public static IdentifiableDTO<OfferDTO> MapOfferPersistenceToOfferIdentifiableDTO(Offer offer){
		if(offer == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<OfferDTO>(offer.getId(), new OfferDTO(offer.getPrice(), offer.getDueToDate()));
	}
	
}
