package quince_it.pquince.services.implementation.drugs;

import java.util.Calendar;
import java.util.List;
import java.sql.Date;
import java.util.UUID;

import javax.transaction.Transactional;

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
	
	@Transactional
	@Override
	public boolean editPriceForDrug(EditPriceForDrugDTO editPriceForDrugDTO) {
		
		if(!isValidDTO(editPriceForDrugDTO))
			return false;
		
		editPriceForDrugDTO.setEndDate(addDays(editPriceForDrugDTO.getStartDate(),3650));

		UUID pharmacyId= this.userService.getPharmacyIdForPharmacyAdmin();
		
		this.removeAllPriceAfterStartDate(editPriceForDrugDTO,pharmacyId);

		if(hasDrugPriceInThisPeriod(editPriceForDrugDTO,pharmacyId)) {
			return editPriceWhenDrugHasPriceInPeriod(editPriceForDrugDTO,pharmacyId);
		}else {
			DrugPriceForPharmacy drugPriceForPharmacy = new DrugPriceForPharmacy(drugInstanceRepository.getOne(editPriceForDrugDTO.getDrugInstanceId()), pharmacyRepository.getOne(pharmacyId), editPriceForDrugDTO.getStartDate(), editPriceForDrugDTO.getEndDate(),editPriceForDrugDTO.getPrice());
			drugPriceForPharmacyRepository.save(drugPriceForPharmacy);
			return true;
		}
	}

	private void removeAllPriceAfterStartDate(EditPriceForDrugDTO editPriceForDrugDTO, UUID pharmacyId) {
		List<DrugPriceForPharmacy> drugPricesForPharmacy = drugPriceForPharmacyRepository.findDrugPriceForPharmacyByPharmacyAndDugId(editPriceForDrugDTO.getDrugInstanceId(), pharmacyId);
		
		for(DrugPriceForPharmacy drugPrice : drugPricesForPharmacy) {
			if(drugPrice.getDateFrom().after(editPriceForDrugDTO.getStartDate())) {
				drugPriceForPharmacyRepository.delete(drugPrice);		
			}
		}
	}

	private boolean editPriceWhenDrugHasPriceInPeriod(EditPriceForDrugDTO editPriceForDrugDTO, UUID pharmacyId) {
		DrugPriceForPharmacy drugPriceForPharmacy = drugPriceForPharmacyRepository.findDrugPriceForPharmacyByDateRange(editPriceForDrugDTO.getDrugInstanceId(),pharmacyId, editPriceForDrugDTO.getStartDate(), editPriceForDrugDTO.getEndDate());

		drugPriceForPharmacy.setDateTo(subtractDays(editPriceForDrugDTO.getStartDate(),1));
		drugPriceForPharmacyRepository.save(drugPriceForPharmacy);

		DrugPriceForPharmacy newDrugPriceForPharmacy = new DrugPriceForPharmacy(drugInstanceRepository.getOne(editPriceForDrugDTO.getDrugInstanceId()), pharmacyRepository.getOne(pharmacyId), editPriceForDrugDTO.getStartDate(), editPriceForDrugDTO.getEndDate(),editPriceForDrugDTO.getPrice());
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
		if(editPriceForDrugDTO.getStartDate().before(new Date(new java.util.Date().getTime())))
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
	
    private Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }

}
