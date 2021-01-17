package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.DrugReservationDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationRequestDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IDrugReservationService{

	public List<IdentifiableDTO<DrugReservationDTO>> findAll();
	
	public IdentifiableDTO<DrugReservationDTO> findById(UUID id);

	public UUID create(DrugReservationRequestDTO entityDTO);

	public void update(DrugReservationDTO entityDTO, UUID id);

	public boolean delete(UUID id) ;
}
