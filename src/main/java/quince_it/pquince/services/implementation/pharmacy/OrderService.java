package quince_it.pquince.services.implementation.pharmacy;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugOrder;
import quince_it.pquince.entities.drugs.Order;
import quince_it.pquince.entities.drugs.OrderStatus;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.PharmacyAdmin;
import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.DrugOrderRepository;
import quince_it.pquince.repository.drugs.OrderRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PharmacyAdminRepository;
import quince_it.pquince.services.contracts.dto.drugs.CreateOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OfferDTO;
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
	public  List<IdentifiableDTO<OrderForProviderDTO>> findAll() {
		
		List<IdentifiableDTO<OrderForProviderDTO>> orders = new ArrayList<IdentifiableDTO<OrderForProviderDTO>>();
		orderRepository.findAll().forEach((d) -> orders.add(OrderMapper.MapOrderInstancePersistenceToOrderInstanceIdentifiableDTO(d)));
		
		return orders;
	}


}
