package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.List;

import quince_it.pquince.entities.drugs.EReceiptStatus;
import quince_it.pquince.services.contracts.dto.drugs.DrugWithEReceiptsDTO;
import quince_it.pquince.services.contracts.dto.drugs.EReceiptWithDrugsDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IEReceiptService {

	List<IdentifiableDTO<EReceiptWithDrugsDTO>> findAllForPatient();
	
	List<IdentifiableDTO<EReceiptWithDrugsDTO>> findAllForPatientSortByDateAscending();
	
	List<IdentifiableDTO<EReceiptWithDrugsDTO>> findAllForPatientSortByDateDescending();
	
	List<IdentifiableDTO<EReceiptWithDrugsDTO>> findAllByPatientSearchByStatus(EReceiptStatus status);
	
	List<IdentifiableDTO<EReceiptWithDrugsDTO>> findAllByPatientSearchByStatusSortByDateAscending(EReceiptStatus status);
	
	List<IdentifiableDTO<EReceiptWithDrugsDTO>> findAllByPatientSearchByStatusSortByDateDescending(EReceiptStatus status);
	
	List<IdentifiableDTO<DrugWithEReceiptsDTO>> findAllProcessedDistinctDrugsByPatientId();	

}
