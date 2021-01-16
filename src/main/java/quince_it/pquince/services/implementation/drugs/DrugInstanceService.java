package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.DrugPriceForPharmacyRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugInstanceDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.IdentifiablePharmacyDrugPriceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugInstanceService;
import quince_it.pquince.services.implementation.util.drugs.DrugInstanceMapper;

@Service
public class DrugInstanceService implements IDrugInstanceService{

	@Autowired
	private DrugInstanceRepository drugInstanceRepository;
	
	@Autowired
	private DrugPriceForPharmacyRepository drugPriceForPharmacyRepository;
	
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
	public List<IdentifiablePharmacyDrugPriceDTO> findByDrugId(UUID id) {
		return drugPriceForPharmacyRepository.findByDrugId(id);
	}

}