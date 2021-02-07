package quince_it.pquince.services.implementation.drugs;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.entities.drugs.DrugPriceForPharmacy;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.DrugPriceForPharmacyRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.services.contracts.dto.drugs.EditPriceForDrugDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugPriceInPharmacyService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;

@Service
public class DrugPriceInPharmacyService implements IDrugPriceInPharmacyService {

	
	@Autowired
	private DrugPriceForPharmacyRepository drugPriceForPharmacyRepository;
	
	@Autowired
	private DrugInstanceRepository drugInstanceRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public boolean editPriceForDrug(EditPriceForDrugDTO editPriceForDrugDTO) {
		
		if(!isValidDTO(editPriceForDrugDTO))
			return false;
		
		UUID pharmacyId= this.userService.getPharmacyIdForPharmacyAdmin();
		
		if(hasDrugPriceInThisPeriod(editPriceForDrugDTO,pharmacyId)) {
			return editPriceWhenDrugHasPriceInPeriod(editPriceForDrugDTO,pharmacyId);
		}else {
			DrugInstance drugInstance= drugInstanceRepository.getOne(editPriceForDrugDTO.getDrugInstanceId());
			Pharmacy pharmacy = pharmacyRepository.getOne(pharmacyId);
			DrugPriceForPharmacy drugPriceForPharmacy = new DrugPriceForPharmacy(drugInstance, pharmacy, editPriceForDrugDTO.getStartDate(), editPriceForDrugDTO.getEndDate(),
					editPriceForDrugDTO.getPrice());
			drugPriceForPharmacyRepository.save(drugPriceForPharmacy);
		}
		
		return true;
	}

	private boolean editPriceWhenDrugHasPriceInPeriod(EditPriceForDrugDTO editPriceForDrugDTO, UUID pharmacyId) {
		DrugPriceForPharmacy drugPriceForPharmacy = drugPriceForPharmacyRepository.findDrugPriceForPharmacyByDateRange(editPriceForDrugDTO.getDrugInstanceId(),pharmacyId, editPriceForDrugDTO.getStartDate(), editPriceForDrugDTO.getEndDate());

		//System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
		drugPriceForPharmacy.setDateTo(subtractDays(editPriceForDrugDTO.getEndDate(),1));
		drugPriceForPharmacyRepository.save(drugPriceForPharmacy);
		
		DrugInstance drugInstance= drugInstanceRepository.getOne(editPriceForDrugDTO.getDrugInstanceId());
		Pharmacy pharmacy = pharmacyRepository.getOne(pharmacyId);
		DrugPriceForPharmacy newDrugPriceForPharmacy = new DrugPriceForPharmacy(drugInstance, pharmacy, editPriceForDrugDTO.getStartDate(), editPriceForDrugDTO.getEndDate(),
				editPriceForDrugDTO.getPrice());
		drugPriceForPharmacyRepository.save(newDrugPriceForPharmacy);
		
		return true;
	}

	private boolean hasDrugPriceInThisPeriod(EditPriceForDrugDTO editPriceForDrugDTO, UUID pharmacyId) {
		DrugPriceForPharmacy drugPriceForPharmacy = drugPriceForPharmacyRepository.findDrugPriceForPharmacyByDateRange(editPriceForDrugDTO.getDrugInstanceId(),pharmacyId, editPriceForDrugDTO.getStartDate(), editPriceForDrugDTO.getEndDate());
		
		if(drugPriceForPharmacy!=null)
			return true;
		
		return false;
	}

	private boolean isValidDTO(EditPriceForDrugDTO editPriceForDrugDTO) {
		if(editPriceForDrugDTO.getStartDate().after(editPriceForDrugDTO.getEndDate()) || editPriceForDrugDTO.getStartDate().equals(new Date()))
			return false;
		
		if(editPriceForDrugDTO.getPrice()<1)
			return false;
		
		return true;
	}
	
	private Date subtractDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        return new Date(c.getTimeInMillis());
    }

}
