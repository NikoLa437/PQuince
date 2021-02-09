package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import quince_it.pquince.services.contracts.dto.drugs.DrugReservationDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationRequestDTO;
import quince_it.pquince.services.contracts.dto.drugs.StaffDrugReservationDTO;
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

	UUID reserveDrugAsStaff(StaffDrugReservationDTO staffDrugReservationDTO);

	IdentifiableDTO<DrugReservationDTO> getDrugReservation(UUID reservationId);

	void processReservation(UUID drugReservationId) throws MailException, InterruptedException, MessagingException;

}
