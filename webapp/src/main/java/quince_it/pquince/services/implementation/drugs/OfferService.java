package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugOrder;
import quince_it.pquince.entities.drugs.DrugStorage;
import quince_it.pquince.entities.drugs.OfferStatus;
import quince_it.pquince.entities.drugs.Offers;
import quince_it.pquince.entities.drugs.Order;
import quince_it.pquince.entities.drugs.OrderStatus;
import quince_it.pquince.repository.drugs.DrugStorageRepository;
import quince_it.pquince.repository.drugs.OfferRepository;
import quince_it.pquince.repository.drugs.OrderRepository;
import quince_it.pquince.services.contracts.dto.drugs.AcceptOfferForOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OfferDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IOfferService;
import quince_it.pquince.services.implementation.util.drugs.OfferMapper;

@Service
public class OfferService implements IOfferService{


	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private DrugStorageRepository drugStorageRepository;
	
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

	@Override
	public List<IdentifiableDTO<OfferDTO>> findOffersForOrder(UUID orderId) {
		Order order = orderRepository.getOne(orderId);
		
		if(order.getOrderStatus()== OrderStatus.PROCESSED)
			return null;
		
		List<IdentifiableDTO<OfferDTO>> retOffers = new ArrayList<IdentifiableDTO<OfferDTO>>();

		order.getOffers().forEach((d) -> retOffers.add(OfferMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(d)));

		return retOffers;
	}

	@Override
	public void acceptOffer(AcceptOfferForOrderDTO acceptOfferForOrderDTO) {
		Order order = orderRepository.getOne(acceptOfferForOrderDTO.getOrderId());
		
		for(Offers offer : order.getOffers()) {
			if(offer.getId().equals(acceptOfferForOrderDTO.getOfferId())) {
				offer.setOrderStatus(OfferStatus.ACCEPTED);
				offerRepository.save(offer);

				this.updateDrugsForOrder(order);
			}
			else{
				offer.setOrderStatus(OfferStatus.REJECTED);
				offerRepository.save(offer);
			}
		}
		
		order.setOrderStatus(OrderStatus.PROCESSED);
		orderRepository.save(order);

	}

	private void updateDrugsForOrder(Order order) {
		
		for(DrugOrder drugOrder : order.getOrder()) {
			DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(drugOrder.getDrugInstance().getId(), order.getPharmacy().getId());
			drugStorage.addAmount(drugOrder.getAmount());
			drugStorageRepository.save(drugStorage);
		}
	}

}
