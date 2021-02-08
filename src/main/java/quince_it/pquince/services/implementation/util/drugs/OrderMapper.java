package quince_it.pquince.services.implementation.util.drugs;

import java.util.ArrayList;
import java.util.List;

import quince_it.pquince.entities.drugs.DrugOrder;
import quince_it.pquince.entities.drugs.Order;
import quince_it.pquince.services.contracts.dto.drugs.DrugOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OrderForProviderDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.util.pharmacy.PharmacyMapper;
import quince_it.pquince.services.implementation.util.users.UserMapper;

public class OrderMapper {

	public static IdentifiableDTO<OrderForProviderDTO> MapOrderInstancePersistenceToOrderInstanceIdentifiableDTO(Order order){
		if(order == null) throw new IllegalArgumentException();
				
		return new IdentifiableDTO<OrderForProviderDTO>(order.getId(), new OrderForProviderDTO(PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(order.getPharmacy()), UserMapper.MapPharmacyAdminPersistenceToPharmacyAdminIdentifiableDTO(order.getPharmacyAdmin()), MapListDrugOrderPersistenceToListDrugOrderIdentifiableDTO(order.getOrder()) ,order.getDate(), order.getOrderStatus()
											, OfferMapper.MapListDrugOrderPersistenceToListDrugOrderIdentifiableDTO(order.getOffers())));
	}
	
	
	public static List<IdentifiableDTO<DrugOrderDTO>> MapListDrugOrderPersistenceToListDrugOrderIdentifiableDTO(List<DrugOrder> drugs){
		List<IdentifiableDTO<DrugOrderDTO>> returnDrugs = new ArrayList<IdentifiableDTO<DrugOrderDTO>>();
		
		drugs.forEach((drug) -> returnDrugs.add(MapListDrugOrderPersistenceToDrugOrderIdentifiableDTO(drug)));
		return returnDrugs;
	}
	
	public static IdentifiableDTO<DrugOrderDTO> MapListDrugOrderPersistenceToDrugOrderIdentifiableDTO(DrugOrder drugOrder){
		if(drugOrder == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<DrugOrderDTO>(drugOrder.getId(), new DrugOrderDTO(drugOrder.getDrugInstance().getId(), drugOrder.getAmount()));
	}
	
	
}