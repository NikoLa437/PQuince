package quince_it.pquince.services.implementation.drugs;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.entities.drugs.DrugPriceForPharmacy;
import quince_it.pquince.entities.drugs.DrugReservation;
import quince_it.pquince.entities.drugs.DrugStorage;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.DrugPriceForPharmacyRepository;
import quince_it.pquince.repository.drugs.DrugReservationRepository;
import quince_it.pquince.repository.drugs.DrugStorageRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PharmacistRepository;
import quince_it.pquince.repository.users.PharmacyAdminRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.services.contracts.dto.drugs.AddDrugToPharmacyDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugStorageDTO;
import quince_it.pquince.services.contracts.dto.drugs.EditStorageAmountForDrugDTO;
import quince_it.pquince.services.contracts.dto.drugs.RemoveDrugFromPharmacyDTO;
import quince_it.pquince.services.contracts.exceptions.DrugStorageCountException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugStorageService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;

@Service
public class DrugStorageService implements IDrugStorageService {

	@Autowired
	private DrugStorageRepository drugStorageRepository;
	
	@Autowired
	private DrugPriceForPharmacyRepository drugPriceForPharmacyRepository;
	
	@Autowired
	private DrugReservationRepository drugReservationRepository;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private PharmacistRepository pharamacistRepository;
	
	@Autowired
	private DrugInstanceRepository drugInstanceRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private PharmacyAdminRepository pharmacyAdminRepository;
	
	@Override
	public List<IdentifiableDTO<DrugStorageDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdentifiableDTO<DrugStorageDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(DrugStorageDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(DrugStorageDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getDrugCountForDrugAndPharmacy(UUID drugId, UUID pharmacyId) {
		try {
			int count = drugStorageRepository.getDrugCountForDrug(drugId, pharmacyId);
			System.out.println("COUNT" + count);
			return count;
		} catch (Exception e) {
			System.out.println("ZERO");
			return 0;
		}		
	}
	
	@Override
	public boolean hasDrugInPharmacy(UUID drugId, UUID pharmacyId) {
		try {
			for(DrugStorage drugStorage : drugStorageRepository.findAll()) {
				if(drugStorage.getPharmacy().getId().equals(pharmacyId) && drugStorage.getDrugInstance().getId().equals(drugId)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			System.out.println("ZERO");
			return false;
		}		
	}

	@Override
	public void reduceAmountOfReservedDrug(UUID drugId, UUID pharmacyId, int amount) {
		DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(drugId, pharmacyId);
		drugStorage.reduceAmount(amount);
		drugStorageRepository.save(drugStorage);	
	}

	@Override
	public void addAmountOfCanceledDrug(UUID drugId, UUID pharmacyId, int amount) {
		
		DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(drugId, pharmacyId);
		drugStorage.addAmount(amount);

		
		drugStorageRepository.save(drugStorage);
	}

	@Override
	public boolean removeDrugFromStorage(RemoveDrugFromPharmacyDTO removeDrugFromPharmacyDTO) {
		if(isDrugReserved(removeDrugFromPharmacyDTO))
			return false;
		
		DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(removeDrugFromPharmacyDTO.getDrugId(),removeDrugFromPharmacyDTO.getPharmacyId());
		DrugPriceForPharmacy drugPriceForPharmacy = drugPriceForPharmacyRepository.findDrugPriceForPharmacy(removeDrugFromPharmacyDTO.getDrugId(), removeDrugFromPharmacyDTO.getPharmacyId());

		drugStorageRepository.delete(drugStorage);
		drugPriceForPharmacyRepository.delete(drugPriceForPharmacy);
		
		return true;
	}

	private boolean isDrugReserved(RemoveDrugFromPharmacyDTO removeDrugFromPharmacyDTO) {
		// TODO Auto-generated method stub
		List<DrugReservation>  drugs = drugReservationRepository.findAllFutureReservationsByDrugAndPharmacyId(removeDrugFromPharmacyDTO.getDrugId(),removeDrugFromPharmacyDTO.getPharmacyId());
		
		if(drugs.size()>0)
			return true;
		
		return false;
	}

	@Override
	public void addDrugToPharmacy(AddDrugToPharmacyDTO addDrugToPharmacyDTO) {
		// TODO Auto-generated method stub
		UUID pharmacyId= this.userService.getPharmacyIdForPharmacyAdmin();
		
		if(!hasDrugInPharmacy(addDrugToPharmacyDTO.getDrugId(),pharmacyId)) {
			DrugInstance drugInstance = drugInstanceRepository.getOne(addDrugToPharmacyDTO.getDrugId());
			Pharmacy pharmacy = pharmacyRepository.getOne(pharmacyId);
			DrugStorage newDrugStorage= new DrugStorage(drugInstance, pharmacy , addDrugToPharmacyDTO.getAmount());
			DrugPriceForPharmacy newDrugPriceInPharmacy = new DrugPriceForPharmacy(drugInstance,pharmacy,new Date(), addDrugToPharmacyDTO.getDateTo(),addDrugToPharmacyDTO.getPrice());
			
			drugStorageRepository.save(newDrugStorage);
			drugPriceForPharmacyRepository.save(newDrugPriceInPharmacy);
		}

	}

	@Override
	public boolean editPriceForDrug(EditStorageAmountForDrugDTO editStorageAmountForDrugDTO) {
		// TODO Auto-generated method stub
		UUID pharmacyId= this.userService.getPharmacyIdForPharmacyAdmin();
		
		DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(editStorageAmountForDrugDTO.getDrugInstanceId(), pharmacyId);
		
		drugStorage.setCount(editStorageAmountForDrugDTO.getAmount());
				
		drugStorageRepository.save(drugStorage);
		return true;
	}

	@Override
	public void isDrugAmountAvailableInPharamcy(UUID drugId, int amount) throws DrugStorageCountException {
		UUID staffId = userService.getLoggedUserId();
		Staff staff = staffRepository.getOne(staffId);
		Pharmacy pharmacy = null;
		if(staff.getStaffType() == StaffType.PHARMACIST)
			pharmacy = pharamacistRepository.getOne(staffId).getPharmacy();
		else if (staff.getStaffType() == StaffType.DERMATOLOGIST)
			pharmacy = userService.getPharmacyForLoggedDermatologist();
		DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(drugId, pharmacy.getId());
		if(drugStorage == null)
			throw new EntityNotFoundException();
		if(drugStorage.getCount() < amount)
			throw new DrugStorageCountException("Amount exceeds drug storage count");
	}

}
