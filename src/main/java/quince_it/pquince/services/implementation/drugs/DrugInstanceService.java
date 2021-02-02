package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.entities.drugs.DrugPriceForPharmacy;
import quince_it.pquince.entities.drugs.DrugStorage;
import quince_it.pquince.entities.drugs.Ingredient;
import quince_it.pquince.entities.drugs.Manufacturer;
import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.DrugPriceForPharmacyRepository;
import quince_it.pquince.repository.drugs.DrugStorageRepository;
import quince_it.pquince.repository.drugs.IngredientRepository;
import quince_it.pquince.repository.drugs.ManufacturerRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugStorageDTO;
import quince_it.pquince.services.contracts.dto.drugs.IngredientDTO;
import quince_it.pquince.services.contracts.dto.drugs.ManufacturerDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugInstanceService;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugStorageService;
import quince_it.pquince.services.contracts.interfaces.users.ILoyaltyProgramService;
import quince_it.pquince.services.implementation.util.drugs.DrugInstanceMapper;
import quince_it.pquince.services.implementation.util.drugs.ManufacturerMapper;

@Service
public class DrugInstanceService implements IDrugInstanceService{

	@Autowired
	private DrugInstanceRepository drugInstanceRepository;
	
	@Autowired
	private ManufacturerRepository manufacturerRepository;

	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private DrugPriceForPharmacyRepository drugPriceForPharmacyRepository;
	
	@Autowired
	private DrugStorageService drugStorageService;
	
	@Autowired
	private ILoyaltyProgramService loyalityProgramService;
	
	@Override
	public List<IdentifiableDTO<DrugInstanceDTO>> findAll() {
		
		List<IdentifiableDTO<DrugInstanceDTO>> drugs = new ArrayList<IdentifiableDTO<DrugInstanceDTO>>();
		drugInstanceRepository.findAll().forEach((d) -> drugs.add(DrugInstanceMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(d)));
		
		return drugs;
	}

	@Override
	public IdentifiableDTO<DrugInstanceDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(DrugInstanceDTO entityDTO) {
		DrugInstance drugInstance = CreateDrugInstanceFromDTO(entityDTO);
		drugInstanceRepository.save(drugInstance);
		return drugInstance.getId();
	}
	
	private DrugInstance CreateDrugInstanceFromDTO(DrugInstanceDTO drugInstanceDTO) {
		return new DrugInstance(drugInstanceDTO.getName(), drugInstanceDTO.getCode(), drugInstanceDTO.getDrugInstanceName(), 
				drugInstanceDTO.getDrugFormat(), drugInstanceDTO.getQuantity(),  drugInstanceDTO.getSideEffects(), drugInstanceDTO.getRecommendedAmount(),
				drugInstanceDTO.getLoyalityPoints(), drugInstanceDTO.isOnReciept(), drugInstanceDTO.getDrugKind());
	}
	
	@Override
	public List<IdentifiableDTO<ManufacturerDTO>>  findDrugManufacturers() {
			
			List<IdentifiableDTO<ManufacturerDTO>> manufacturers = new ArrayList<IdentifiableDTO<ManufacturerDTO>>();
			manufacturerRepository.findAll().forEach((d) -> manufacturers.add(ManufacturerMapper.MapManufacturerPersistenceToManufacturerIdentifiableDTO(d)));
			
			return manufacturers;
	}
	
	@Override
	public UUID addDrugManufacturer(UUID id, UUID manufacturerId) {
		DrugInstance drugInstance = drugInstanceRepository.getOne(id);
		Manufacturer manufacturer = manufacturerRepository.getOne(manufacturerId);
		
		drugInstance.setManufacturer(manufacturer);
		
		drugInstanceRepository.save(drugInstance);
		
		return id;
	}
	

	@Override
	public UUID addDrugReplacement(UUID id, UUID replacement_id) {
		
		DrugInstance drugInstance = drugInstanceRepository.getOne(id);
		DrugInstance drugReplacementInstance = drugInstanceRepository.getOne(replacement_id);
		
		drugInstance.addReplaceDrug(drugReplacementInstance);
		
		drugInstanceRepository.save(drugInstance);
		
		return id;
	}
	
	private Manufacturer CreateManufacturerFromDTO(ManufacturerDTO manufacturerDTO) {
		return new Manufacturer(manufacturerDTO.getName());
	}
	
	@Override
	public UUID addDrugIngredients(UUID id, IngredientDTO entityDTO) {
		DrugInstance drugInstance = drugInstanceRepository.getOne(id);
		Ingredient ingredient = ingredientRepository.findByName(entityDTO.getName());
		drugInstance.addIngredient(ingredient);
		
		drugInstanceRepository.save(drugInstance);
		
		return id;
	}
	
	@Override
	public UUID addDrugReplacement(UUID id, DrugInstanceDTO entityDTO) {
		DrugInstance drugInstance = drugInstanceRepository.getOne(id);
		DrugInstance drugReplace = drugInstanceRepository.getOne(id);
		
		drugInstance.addReplaceDrug(drugReplace);
		
		drugInstanceRepository.save(drugReplace);
		
		return id;
	}

	@Override
	public void update(DrugInstanceDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IdentifiablePharmacyDrugPriceAmountDTO> findByDrugId(UUID drugId) {
		
		List<IdentifiablePharmacyDrugPriceAmountDTO> retVal = new ArrayList<IdentifiablePharmacyDrugPriceAmountDTO>();
		for (IdentifiablePharmacyDrugPriceAmountDTO pharmacy : drugPriceForPharmacyRepository.findByDrugId(drugId)) {
			int countDrug = drugStorageService.getDrugCountForDrugAndPharmacy(drugId, pharmacy.Id);
			if(countDrug > 0) {
				pharmacy.setPriceWithDiscount(loyalityProgramService.getDiscountDrugPriceForPatient(pharmacy.getPrice()));
				pharmacy.setCount(countDrug);
				retVal.add(pharmacy);
			}
		}

		return retVal;
	}

	@Override
	public IdentifiablePharmacyDrugPriceAmountDTO findByDrugInPharmacy(UUID drugId, UUID pharmacyId) {
		for (IdentifiablePharmacyDrugPriceAmountDTO pharmacy : drugPriceForPharmacyRepository.findByDrugId(drugId)) {
			if(pharmacy.Id.equals(pharmacyId)) {
				int countDrug = drugStorageService.getDrugCountForDrugAndPharmacy(drugId, pharmacyId);
				if(countDrug > 0) {
					pharmacy.setCount(countDrug);
					return pharmacy;
				}
			}
		}

		return null;
	}

	@Override
	public List<IdentifiableDTO<DrugInstanceDTO>> findDrugsByPharmacy(UUID pharmacyId) {
		List<IdentifiableDTO<DrugInstanceDTO>> drugs = new ArrayList<IdentifiableDTO<DrugInstanceDTO>>();
		for(DrugInstance drugInstance : drugInstanceRepository.findAll()) {
			if(drugStorageService.hasDrugInPharmacy(drugInstance.getId(), pharmacyId))
				drugs.add(DrugInstanceMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(drugInstance));
		}
		
		return drugs;
	}


}
