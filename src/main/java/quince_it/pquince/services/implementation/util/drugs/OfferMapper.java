package quince_it.pquince.services.implementation.util.drugs;

import java.util.ArrayList;
import java.util.List;

import quince_it.pquince.entities.drugs.Offers;
import quince_it.pquince.services.contracts.dto.drugs.OfferDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class OfferMapper {
	
	public static IdentifiableDTO<OfferDTO> MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(Offers offer){
		if(offer == null) throw new IllegalArgumentException();
				
		return new IdentifiableDTO<OfferDTO>(offer.getId(), new OfferDTO(offer.getDateToDelivery(), offer.getPrice(), offer.getOrderStatus()));
	}
	
	public static List<IdentifiableDTO<OfferDTO>> MapListDrugOrderPersistenceToListDrugOrderIdentifiableDTO(List<Offers> offers){
		List<IdentifiableDTO<OfferDTO>> offer = new ArrayList<IdentifiableDTO<OfferDTO>>();
		
		offers.forEach((o) -> offer.add(MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(o)));
		return offer;
	}
	
}
