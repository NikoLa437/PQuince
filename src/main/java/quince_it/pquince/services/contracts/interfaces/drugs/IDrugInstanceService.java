package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IDrugInstanceService extends IService<DrugInstanceDTO, IdentifiableDTO<DrugInstanceDTO>>{
	List<IdentifiablePharmacyDrugPriceAmountDTO> findByDrugId(UUID drugId);
	IdentifiablePharmacyDrugPriceAmountDTO findByDrugInPharmacy(UUID drugId, UUID pharmacyId);
}
