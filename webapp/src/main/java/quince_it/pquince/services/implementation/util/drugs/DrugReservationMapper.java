package quince_it.pquince.services.implementation.util.drugs;

import java.util.ArrayList;
import java.util.List;

import quince_it.pquince.entities.drugs.DrugReservation;
import quince_it.pquince.services.contracts.dto.drugs.DrugReservationDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.util.pharmacy.PharmacyMapper;

public class DrugReservationMapper {

	
	public static IdentifiableDTO<DrugReservationDTO> MapDrugReservationPersistenceToDrugReservationIdentifiableDTO(DrugReservation drugReservation){
		if(drugReservation == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<DrugReservationDTO>(drugReservation.getId(),
													   new DrugReservationDTO(PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(drugReservation.getPharmacy()),
													   DrugInstanceMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(drugReservation.getDrugInstance()),
													   drugReservation.getAmount(), drugReservation.getStartDate(), drugReservation.getEndDate(),
													   drugReservation.getDrugPeacePrice(), drugReservation.getReservationStatus()));
	}
	
	public static List<IdentifiableDTO<DrugReservationDTO>> MapDrugReservationPersistenceListToDrugReservationIdentifiableDTOList(List<DrugReservation> drugReservations){
		
		List<IdentifiableDTO<DrugReservationDTO>> drugReservationsDTO = new ArrayList<IdentifiableDTO<DrugReservationDTO>>();
		drugReservations.forEach((d) -> drugReservationsDTO.add(MapDrugReservationPersistenceToDrugReservationIdentifiableDTO(d)));
		return drugReservationsDTO;
	}
}
