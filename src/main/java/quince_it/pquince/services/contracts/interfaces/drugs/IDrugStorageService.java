package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.DrugStorageDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IDrugStorageService extends IService<DrugStorageDTO, IdentifiableDTO<DrugStorageDTO>>{

	int getDrugCountForDrugAndPharmacy(UUID drugId, UUID pharmacyId);
	
}
