package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.entities.drugs.DrugPriceForPharmacy;
import quince_it.pquince.entities.drugs.DrugStorage;
import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.DrugPriceForPharmacyRepository;
import quince_it.pquince.repository.drugs.DrugStorageRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugStorageDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceAmountDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugInstanceService;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugStorageService;
import quince_it.pquince.services.implementation.util.drugs.DrugInstanceMapper;

@Service
public class DrugInstanceService implements IDrugInstanceService{

	@Autowired
	private DrugInstanceRepository drugInstanceRepository;
	
	@Autowired
	private DrugPriceForPharmacyRepository drugPriceForPharmacyRepository;
	
	@Autowired
	private DrugStorageService drugStorageService;
	
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
		// TODO Auto-generated method stub
		return null;
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
