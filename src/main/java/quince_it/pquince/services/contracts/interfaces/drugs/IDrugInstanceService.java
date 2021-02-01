package quince_it.pquince.services.contracts.interfaces.drugs;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.dto.drugs.IngredientDTO;
import quince_it.pquince.services.contracts.dto.drugs.ManufacturerDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IDrugInstanceService extends IService<DrugInstanceDTO, IdentifiableDTO<DrugInstanceDTO>>{
	List<IdentifiablePharmacyDrugPriceAmountDTO> findByDrugId(UUID drugId);
	IdentifiablePharmacyDrugPriceAmountDTO findByDrugInPharmacy(UUID drugId, UUID pharmacyId);
	List<IdentifiableDTO<DrugInstanceDTO>> findDrugsByPharmacy(UUID pharmacyId);
	UUID addDrugIngredients(UUID id, IngredientDTO entityDTO);
	UUID addDrugReplacement(UUID id, DrugInstanceDTO entityDTO);
	List<IdentifiableDTO<ManufacturerDTO>> findDrugManufacturers();
	UUID addDrugManufacturer(UUID id, UUID manufacturerId);
}
