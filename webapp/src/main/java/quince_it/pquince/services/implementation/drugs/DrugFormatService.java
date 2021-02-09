package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.repository.drugs.DrugFormatRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugFormatIdDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugFormatService;
import quince_it.pquince.services.implementation.util.drugs.DrugFormatMapper;

@Service
public class DrugFormatService implements IDrugFormatService {

	@Autowired
	private  DrugFormatRepository  drugFormatRepository;
	
	@Override
	public List<IdentifiableDTO<DrugFormatIdDTO>> findAll() {
		List<IdentifiableDTO<DrugFormatIdDTO>> drugFormats = new ArrayList<IdentifiableDTO<DrugFormatIdDTO>>();
		drugFormatRepository.findAll().forEach((a) -> drugFormats.add(DrugFormatMapper.MapDrugFormatIdPersistenceToDrugFormatIdIdentifiableDTO(a)));

		return drugFormats;
	}

	@Override
	public IdentifiableDTO<DrugFormatIdDTO> findById(UUID id) {
		return null;
	}

	@Override
	public UUID create(DrugFormatIdDTO entityDTO) {
		return null;
	}

	@Override
	public void update(DrugFormatIdDTO entityDTO, UUID id) {
		
	}

	@Override
	public boolean delete(UUID id) {
		return false;
	}

}
