package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.repository.drugs.DrugKindRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugKindIdDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugKindIdService;
import quince_it.pquince.services.implementation.util.drugs.DrugKindMapper;

@Service
public class DrugKindIdService implements IDrugKindIdService {

	@Autowired
	private  DrugKindRepository  drugKindRepository;
	
	@Override
	public List<IdentifiableDTO<DrugKindIdDTO>> findAll() {
		List<IdentifiableDTO<DrugKindIdDTO>> drugKinds = new ArrayList<IdentifiableDTO<DrugKindIdDTO>>();
		drugKindRepository.findAll().forEach((a) -> drugKinds.add(DrugKindMapper.MapDrugKindIdPersistenceToDrugKindIdIdentifiableDTO(a)));

		return drugKinds;

	}

	@Override
	public IdentifiableDTO<DrugKindIdDTO> findById(UUID id) {
		return null;
	}

	@Override
	public UUID create(DrugKindIdDTO entityDTO) {
		return null;
	}

	@Override
	public void update(DrugKindIdDTO entityDTO, UUID id) {
		
	}

	@Override
	public boolean delete(UUID id) {
		return false;
	}

}
