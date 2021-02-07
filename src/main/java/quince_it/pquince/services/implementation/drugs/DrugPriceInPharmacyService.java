package quince_it.pquince.services.implementation.drugs;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.entities.drugs.DrugPriceForPharmacy;
import quince_it.pquince.repository.drugs.DrugPriceForPharmacyRepository;
import quince_it.pquince.services.contracts.dto.drugs.EditPriceForDrugDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugPriceInPharmacyService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;

@Service
public class DrugPriceInPharmacyService implements IDrugPriceInPharmacyService {

	
	@Autowired
	private DrugPriceForPharmacyRepository drugPriceForPharmacyRepository;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public boolean editPriceForDrug(EditPriceForDrugDTO editPriceForDrugDTO) {
		UUID pharmacyId= this.userService.getPharmacyIdForPharmacyAdmin();
		
		DrugPriceForPharmacy drugPriceForPharmacy = drugPriceForPharmacyRepository.findDrugPriceForPharmacy(editPriceForDrugDTO.getDrugInstanceId(), pharmacyId);
		
		drugPriceForPharmacy.setPrice(editPriceForDrugDTO.getPrice());
		
		drugPriceForPharmacyRepository.save(drugPriceForPharmacy);
		return true;
	}

}
