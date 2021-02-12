package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.List;

import quince_it.pquince.services.contracts.dto.drugs.DrugRequestDTO;

public interface IDrugRequestService {

	List<DrugRequestDTO> getDrugRequestsForPharmacy();

}
