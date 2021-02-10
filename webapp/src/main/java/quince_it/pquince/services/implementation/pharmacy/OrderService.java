package quince_it.pquince.services.implementation.pharmacy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugOrder;
import quince_it.pquince.entities.drugs.Offers;
import quince_it.pquince.entities.drugs.Order;
import quince_it.pquince.entities.drugs.OrderStatus;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.PharmacyAdmin;
import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.DrugOrderRepository;
import quince_it.pquince.repository.drugs.OrderRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PharmacyAdminRepository;
import quince_it.pquince.services.contracts.dto.EntityIdDTO;
import quince_it.pquince.services.contracts.dto.drugs.CreateOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugForOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OfferDTO;
import quince_it.pquince.services.contracts.dto.drugs.OrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OrderForProviderDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IOrderService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;
import quince_it.pquince.services.implementation.util.drugs.OfferMapper;
import quince_it.pquince.services.implementation.util.drugs.OrderMapper;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private PharmacyAdminRepository pharmacyAdminRepository;
	
	@Autowired
	private DrugInstanceRepository drugInstanceRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private DrugOrderRepository drugOrderRepository;
	
	@Override
	public UUID create(CreateOrderDTO createOrderDTO) {
		Pharmacy pharmacy = pharmacyRepository.getOne(userService.getPharmacyIdForPharmacyAdmin());
		PharmacyAdmin pharmacyAdmin = pharmacyAdminRepository.getOne(userService.getLoggedUserId());
		
		Order newOrder = new Order(pharmacy,pharmacyAdmin,this.generateListOfDrugOrder(createOrderDTO.getDrugs()),createOrderDTO.getEndDate(),null, OrderStatus.CREATED);

		orderRepository.save(newOrder);
		return newOrder.getId();
	}


	private List<DrugOrder> generateListOfDrugOrder(List<DrugOrderDTO> drugs) {
		List<DrugOrder> retVal = new ArrayList<DrugOrder>();
		
		for(DrugOrderDTO drugOrderDTO : drugs) {
			DrugOrder newDrugOrder = new DrugOrder(drugInstanceRepository.getOne(drugOrderDTO.getDrugInstanceId()),drugOrderDTO.getAmount());
			drugOrderRepository.save(newDrugOrder);
			retVal.add(newDrugOrder);
		}
		
		return retVal;
	}
	
	@Override
	public  List<IdentifiableDTO<OrderForProviderDTO>> findAllProvider() {
		boolean var = false;
		List<IdentifiableDTO<OrderForProviderDTO>> orders = new ArrayList<IdentifiableDTO<OrderForProviderDTO>>();
		
		for(Order o: orderRepository.findAll()) {
			for(Offers of: o.getOffers()) {
				if(of.getSupplier().getId().equals(userService.getLoggedUserId()))
					var = true;
			}
			if(!var)
				orders.add(OrderMapper.MapOrderInstancePersistenceToOrderInstanceIdentifiableDTO(o));
			
			var = false;
		}
		
		return orders;
	}



	@Override
	public List<IdentifiableDTO<OrderDTO>> findOrdersForPharmacy(UUID pharmacyId) {
		List<Order> drugOrdersForPharmacy = orderRepository.findDrugOrderForPharmacy(pharmacyId);
		
		List<IdentifiableDTO<OrderDTO>> retVal = new ArrayList<IdentifiableDTO<OrderDTO>>();
		
		drugOrdersForPharmacy.forEach(o -> retVal.add(MapOrderPersistanceToIndetifiableOrderDTO(o)));

		return retVal;
	}


	private IdentifiableDTO<OrderDTO> MapOrderPersistanceToIndetifiableOrderDTO(Order o) {
		// TODO Auto-generated method stub
		return new IdentifiableDTO<OrderDTO>(o.getId(),new OrderDTO(this.mapListOfDrugOrderToListOfDrugOrderDTO(o.getOrder()),o.getDate(),o.getOffers().size(),o.getPharmacyAdmin().getName()+ " " + o.getPharmacyAdmin().getSurname()));
	}


	private List<DrugForOrderDTO> mapListOfDrugOrderToListOfDrugOrderDTO(List<DrugOrder> order) {
		// TODO Auto-generated method stub
		 List<DrugForOrderDTO> retVal = new ArrayList<DrugForOrderDTO>();
		 
		 order.forEach(dfo -> retVal.add(MapDrugOrderPersistanceToDrugOrderDTO(dfo)));
		 
		 return retVal;
	}

	private DrugForOrderDTO MapDrugOrderPersistanceToDrugOrderDTO(DrugOrder dfo) {
		// TODO Auto-generated method stub
		return new DrugForOrderDTO(dfo.getDrugInstance().getId(),(int)dfo.getAmount(),dfo.getDrugInstance().getName(),dfo.getDrugInstance().getDrugInstanceName(),dfo.getDrugInstance().getManufacturer().getName());
	}


	@Override
	public boolean removeOrder(EntityIdDTO removeOrderId) {
		// TODO Auto-generated method stub
		
		Order order = orderRepository.getOne(removeOrderId.getId());
		
		if(order.getOffers().size()==0) {
			orderRepository.delete(order);
			return true;
		}
		
		return false;
	}


	@Override
	public List<IdentifiableDTO<OrderDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}



}
