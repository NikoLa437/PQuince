package quince_it.pquince.services.implementation.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.repository.drugs.DrugStorageRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugStorageDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugStorageService;

@Service
public class DrugStorageService implements IDrugStorageService {

	@Autowired
	private DrugStorageRepository drugStorageRepository;
	
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

}
