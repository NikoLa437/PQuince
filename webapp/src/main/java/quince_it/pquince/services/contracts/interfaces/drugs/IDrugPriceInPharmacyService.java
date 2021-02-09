package quince_it.pquince.services.contracts.interfaces.drugs;

import quince_it.pquince.services.contracts.dto.drugs.EditPriceForDrugDTO;

public interface IDrugPriceInPharmacyService {

	boolean editPriceForDrug(EditPriceForDrugDTO editPriceForDrugDTO);

}
