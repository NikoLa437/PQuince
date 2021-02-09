package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.entities.drugs.Offers;
import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.OfferRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.dto.drugs.OfferDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IOfferService;
import quince_it.pquince.services.implementation.util.drugs.DrugInstanceMapper;
import quince_it.pquince.services.implementation.util.drugs.OfferMapper;

@Service
public class OfferService implements IOfferService{


	@Autowired
	private OfferRepository offerRepository;
	
	@Override
	public List<IdentifiableDTO<OfferDTO>> findAll() {
		
		List<IdentifiableDTO<OfferDTO>> offers = new ArrayList<IdentifiableDTO<OfferDTO>>();
		offerRepository.findAll().forEach((d) -> offers.add(OfferMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(d)));
		
		return offers;
	}

	@Override
	public IdentifiableDTO<OfferDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(OfferDTO entityDTO) {
		Offers offer = CreateOfferInstanceFromDTO(entityDTO);
		offerRepository.save(offer);
		return offer.getId();
	}
	
	private Offers CreateOfferInstanceFromDTO(OfferDTO offerDTO) {
		return new Offers(offerDTO.getDateToDelivery(), offerDTO.getPrice(), offerDTO.getOrderStatus());
	}

	@Override
	public void update(OfferDTO entityDTO, UUID id) {
		Offers offer = offerRepository.getOne(id);
		offer.setPrice(entityDTO.getPrice());
		offer.setDateToDelivery(entityDTO.getDateToDelivery());
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

}
