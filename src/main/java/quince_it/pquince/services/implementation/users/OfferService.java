package quince_it.pquince.services.implementation.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import quince_it.pquince.entities.users.Offer;
import quince_it.pquince.repository.users.OfferRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.services.contracts.dto.users.OfferDTO;
import quince_it.pquince.services.contracts.dto.users.UserDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.users.IOfferService;
import quince_it.pquince.services.implementation.util.users.OfferMapper;
import quince_it.pquince.services.implementation.util.users.UserMapper;

public class OfferService implements IOfferService {

	
	@Autowired
	private OfferRepository offerRepository;
	
	@Override
	public List<IdentifiableDTO<OfferDTO>> findAll() {
		List<IdentifiableDTO<OfferDTO>> offers = new ArrayList<IdentifiableDTO<OfferDTO>>();
		offerRepository.findAll().forEach((u) -> offers.add(OfferMapper.MapOfferPersistenceToOfferIdentifiableDTO(u)));
		return offers;
	}

	@Override
	public IdentifiableDTO<OfferDTO> findById(UUID id) {
		return OfferMapper.MapOfferPersistenceToOfferIdentifiableDTO(offerRepository.getOne(id));
	}

	@Override
	public UUID create(OfferDTO entityDTO) {
		
		Offer offer =  CreateOfferFromDTO(entityDTO);
		
		offerRepository.save(offer);
		
		return offer.getId();
	}
	
	private Offer CreateOfferFromDTO(OfferDTO entityDTO) {
		
		return new Offer(entityDTO.getPrice(), entityDTO.getDueToDate());
	}

	@Override
	public void update(OfferDTO entityDTO, UUID id) {
		Offer offer = offerRepository.getOne(id);
		
		offer.setPrice(entityDTO.getPrice());
		offer.setDueToDate(entityDTO.getDueToDate());
		offerRepository.save(offer);
	}

	@Override
	public boolean delete(UUID id) {
		return false;
	}

}
