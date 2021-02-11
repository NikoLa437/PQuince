package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugRequest;
import quince_it.pquince.repository.drugs.DrugRequestRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugRequestDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugRequestService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;

@Service
public class DrugRequestService implements IDrugRequestService{
	@Autowired
	private DrugRequestRepository drugRequestRepository;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public List<DrugRequestDTO> getDrugRequestsForPharmacy(){
		List<DrugRequestDTO> retVal = new ArrayList<DrugRequestDTO>();

		UUID pharmacyId= userService.getPharmacyIdForPharmacyAdmin();
		List<DrugRequest> drugRequests= drugRequestRepository.findAllForPharmacy(pharmacyId);
		
		drugRequests.forEach(dr -> retVal.add(MapDrugRequestPersistanceToDrugRequestDTO(dr)));
		
		return retVal;
	}

	private DrugRequestDTO MapDrugRequestPersistanceToDrugRequestDTO(DrugRequest dr) {
		return new DrugRequestDTO(dr.getDrugInstance().getDrugInstanceName(),dr.getDrugInstance().getManufacturer().getName(),"Dr "+dr.getStaff().getName()+" "+dr.getStaff().getSurname(),dr.getDateTime());
	}
}
