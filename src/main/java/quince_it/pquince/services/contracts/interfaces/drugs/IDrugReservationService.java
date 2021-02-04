package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.DrugReservationDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationRequestDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IDrugReservationService{
	
	List<IdentifiableDTO<DrugReservationDTO>> findAllByPatientId();
	
	List<IdentifiableDTO<DrugReservationDTO>> findAllFutureReservationsByPatientId();
	
	List<IdentifiableDTO<DrugReservationDTO>> findProcessedDrugReservationsForPatient();

	IdentifiableDTO<DrugReservationDTO> findById(UUID id);

	UUID create(DrugReservationRequestDTO entityDTO);

	void update(DrugReservationDTO entityDTO, UUID id);

	boolean delete(UUID id) ;
	
	void cancelDrugReservation(UUID id);
	
	void givePenaltyForMissedDrugReservation();

}
