package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quince_it.pquince.entities.drugs.DrugOrder;
import quince_it.pquince.entities.drugs.DrugStorage;
import quince_it.pquince.entities.drugs.OfferStatus;
import quince_it.pquince.entities.drugs.Offers;
import quince_it.pquince.entities.drugs.Order;
import quince_it.pquince.entities.drugs.OrderStatus;
import quince_it.pquince.entities.drugs.SupplierDrugStorage;
import quince_it.pquince.repository.drugs.DrugStorageRepository;
import quince_it.pquince.repository.drugs.OfferRepository;
import quince_it.pquince.repository.drugs.OrderRepository;
import quince_it.pquince.repository.drugs.SupplierDrugStorageRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.services.contracts.dto.drugs.AcceptOfferForOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OfferDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IOfferService;
import quince_it.pquince.services.implementation.users.UserService;
import quince_it.pquince.services.implementation.util.drugs.OfferMapper;

@Service
public class OfferService implements IOfferService{


	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private DrugStorageRepository drugStorageRepository;

	@Autowired
	private SupplierDrugStorageRepository supplierDrugStorageRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private UserService userService;

	
	@Override
	public List<IdentifiableDTO<OfferDTO>> findAll() {
		
		List<IdentifiableDTO<OfferDTO>> offers = new ArrayList<IdentifiableDTO<OfferDTO>>();
		offerRepository.findBySupplier(userService.getLoggedUserId()).forEach((d) -> offers.add(OfferMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(d)));
		
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
	
	//ovde mogu konflikti mozda
	@Override
	public UUID create(OfferDTO entityDTO) {
		Offers offer = CreateOfferInstanceFromDTO(entityDTO);
		offer.setSupplier(staffRepository.getOne(userService.getLoggedUserId()));
		offer.setOfferStatus(OfferStatus.WAITING);
		Order order = orderRepository.getOne(entityDTO.getId());
		offerRepository.save(offer);
		order.addOffer(offer);
		orderRepository.save(order);
		return offer.getId();
	}
	
	private Offers CreateOfferInstanceFromDTO(OfferDTO offerDTO) {
		return new Offers(offerDTO.getDateToDelivery(), offerDTO.getPrice(), offerDTO.getOfferStatus());
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

	public List<IdentifiableDTO<OfferDTO>> findOffersForOrder(UUID orderId) {
		Order order = orderRepository.getOne(orderId);
		
		if(order.getOrderStatus()== OrderStatus.PROCESSED)
			return null;
		
		List<IdentifiableDTO<OfferDTO>> retOffers = new ArrayList<IdentifiableDTO<OfferDTO>>();

		order.getOffers().forEach((d) -> retOffers.add(OfferMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(d)));

		return retOffers;
	}


	@Override
	@Transactional
	public boolean acceptOffer(AcceptOfferForOrderDTO acceptOfferForOrderDTO) {

		Order order = orderRepository.getOne(acceptOfferForOrderDTO.getOrderId());
		
		if(!CanBeAccepted(order))
			return false;
		
		for(Offers offer : order.getOffers()) {
			if(offer.getId().equals(acceptOfferForOrderDTO.getOfferId())) {
				offer.setOfferStatus(OfferStatus.ACCEPTED);
				offerRepository.save(offer);

				this.updateDrugsForOrder(order);
			}
			else{
				offer.setOfferStatus(OfferStatus.REJECTED);
				offerRepository.save(offer);
			}
		}
		
		order.setOrderStatus(OrderStatus.PROCESSED);
		orderRepository.save(order);
		return true;
	}


	private boolean CanBeAccepted(Order order) {
		if(order.getDate().after(new Date()))
			return false;
		
		if(!order.getPharmacyAdmin().getId().equals(userService.getLoggedUserId()))
			return false;
		
		return true;
	}

	private void updateDrugsForOrder(Order order) {
		
		for(DrugOrder drugOrder : order.getOrder()) {
			DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(drugOrder.getDrugInstance().getId(), order.getPharmacy().getId());
			drugStorage.addAmount(drugOrder.getAmount());
			drugStorageRepository.save(drugStorage);
		}
	}
	
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

	@Override
	public List<IdentifiableDTO<OfferDTO>> findAllAccepted() {
		List<IdentifiableDTO<OfferDTO>> offers = new ArrayList<IdentifiableDTO<OfferDTO>>();
		
		for(Offers o: offerRepository.findBySupplier(userService.getLoggedUserId())) {
			if(o.getOfferStatus().equals(OfferStatus.ACCEPTED))
				offers.add(OfferMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(o));
		}
		
		
		return offers;
	}

	@Override
	public List<IdentifiableDTO<OfferDTO>> findAllRejected() {
		List<IdentifiableDTO<OfferDTO>> offers = new ArrayList<IdentifiableDTO<OfferDTO>>();
		
		for(Offers o: offerRepository.findBySupplier(userService.getLoggedUserId())) {
			if(o.getOfferStatus().equals(OfferStatus.REJECTED))
				offers.add(OfferMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(o));
		}
		
		
		return offers;	
	}

	@Override
	public List<IdentifiableDTO<OfferDTO>> findAllWaiting() {
		List<IdentifiableDTO<OfferDTO>> offers = new ArrayList<IdentifiableDTO<OfferDTO>>();
		
		for(Offers o: offerRepository.findBySupplier(userService.getLoggedUserId())) {
			if(o.getOfferStatus().equals(OfferStatus.WAITING))
				offers.add(OfferMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(o));
		}
		
		
		return offers;
	}

}
