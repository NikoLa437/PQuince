package quince_it.pquince.services.contracts.interfaces.pharmacy;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.CreateOrderDTO;
import quince_it.pquince.services.contracts.dto.drugs.OrderForProviderDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IOrderService {

	UUID create(CreateOrderDTO createOrderDTO);

	List<IdentifiableDTO<OrderForProviderDTO>> findAll();

}
