package quince_it.pquince.services.contracts.interfaces.pharmacy;

import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.CreateOrderDTO;

public interface IOrderService {

	UUID create(CreateOrderDTO createOrderDTO);

}
