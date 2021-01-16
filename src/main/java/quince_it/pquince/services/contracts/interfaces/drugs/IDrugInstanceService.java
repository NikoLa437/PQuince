package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IDrugInstanceService extends IService<DrugInstanceDTO, IdentifiableDTO<DrugInstanceDTO>>{
	List<IdentifiablePharmacyDrugPriceDTO> findByDrugId(UUID id);
}
