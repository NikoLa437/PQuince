package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.OfferDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IOfferService extends IService<OfferDTO, IdentifiableDTO<OfferDTO>>{

	boolean checkIfCanUpdate(UUID id);

	boolean checkIfHasDrugs(UUID id);

}
