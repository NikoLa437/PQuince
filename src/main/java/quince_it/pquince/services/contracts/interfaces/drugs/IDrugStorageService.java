package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.AddDrugToPharmacyDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugStorageDTO;
import quince_it.pquince.services.contracts.dto.drugs.RemoveDrugFromPharmacyDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IDrugStorageService extends IService<DrugStorageDTO, IdentifiableDTO<DrugStorageDTO>>{

	int getDrugCountForDrugAndPharmacy(UUID drugId, UUID pharmacyId);
	
	void reduceAmountOfReservedDrug(UUID drugId, UUID pharmacyId, int amount);

	boolean hasDrugInPharmacy(UUID drugId, UUID pharmacyId);	
	
	void addAmountOfCanceledDrug(UUID drugId, UUID pharmacyId, int amount);

	boolean removeDrugFromStorage(RemoveDrugFromPharmacyDTO removeDrugFromPharmacyDTO);

	void addDrugToPharmacy(AddDrugToPharmacyDTO addDrugToPharmacyDTO);	
}
