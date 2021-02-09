package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.entities.drugs.DrugOrder;
import quince_it.pquince.entities.drugs.Offers;
import quince_it.pquince.entities.drugs.Order;
import quince_it.pquince.entities.drugs.SupplierDrugStorage;
import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.OfferRepository;
import quince_it.pquince.repository.drugs.OrderRepository;
import quince_it.pquince.repository.drugs.SupplierDrugStorageRepository;
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
	
	@Autowired
	private SupplierDrugStorageRepository supplierDrugStorageRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
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
	public boolean checkIfCanUpdate(UUID id) {
		Date todayDate = new Date();
		List<Order> orders = orderRepository.findAll();
		for(Order o: orders){
			if(o.getOffers() == null)
				continue;
			
			if(findIfThereIsOffer(o.getOffers(), id)) {
				if( todayDate.before(o.getDate()) )
					return true;
				else 
					return false;
			}
		}
		
		return false;
	}
	
	private boolean findIfThereIsOffer(List<Offers> offers, UUID id) {
		for(Offers o: offers) {
			if(o.getId().equals(id))
				return true;
		}
		
		return false;
		
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
		if(entityDTO.getDateToDelivery() != null)
			offer.setDateToDelivery(entityDTO.getDateToDelivery());
		offerRepository.save(offer);
	}

	@Override
	public boolean delete(UUID id) {
		return false;
	}

	@Override
	public boolean checkIfHasDrugs(UUID id) {
		
		Order order = orderRepository.getOne(id);
		
		List<DrugOrder> drugOrders = order.getOrder();
		for(DrugOrder o: drugOrders) {
			if(checkIfExist(o)) {
				continue;
			}else
				return false;
		}
		
		return true;
	}

	private boolean checkIfExist(DrugOrder o) {

		for(SupplierDrugStorage storage: supplierDrugStorageRepository.findAll()){
			if(storage.getDrugInstance().getId().equals(o.getDrugInstance().getId())) {
				if(storage.getCount() >= o.getAmount())
					return true;
			}
		}
		
		return false;
	}

}
