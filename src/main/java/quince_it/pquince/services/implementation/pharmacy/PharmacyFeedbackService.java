package quince_it.pquince.services.implementation.pharmacy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.repository.pharmacy.PharmacyFeedbackRepository;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationRepositoryDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyFeedbackService;

@Service
public class PharmacyFeedbackService implements IPharmacyFeedbackService {

	private int MAX_GRADE = 5;

	private int MIN_GRADE = 0;

	@Autowired
	private PharmacyFeedbackRepository pharmacyFeedbackRepository;

	@Override
	public double findAvgGradeForPharmacy(UUID pharmacyId) {
		return pharmacyFeedbackRepository.findAvgGradeForPharmacy(pharmacyId);
	}

	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findByNameCityAndGrade(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		
		pharmacyFiltrationDTO = validateFiltrationDTO(pharmacyFiltrationDTO);

		List<PharmacyFiltrationRepositoryDTO> pharmacyFiltrationDTOs = pharmacyFeedbackRepository
																		.findByNameCityAndGrade(pharmacyFiltrationDTO.getName().toLowerCase(),
																									pharmacyFiltrationDTO.getGradeFrom(),
																									pharmacyFiltrationDTO.getGradeTo(),
																									pharmacyFiltrationDTO.getCity().toLowerCase());
		List<IdentifiableDTO<PharmacyGradeDTO>> returnedPharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacyFiltrationDTOs.forEach((p) -> returnedPharmacies.add(MapPharmacyFiltrationDTOToIdentifiablePharmacyGradeDTO(p)));
		return returnedPharmacies;
	}

	private PharmacyFiltrationDTO validateFiltrationDTO(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		
		if(pharmacyFiltrationDTO.getGradeFrom() > pharmacyFiltrationDTO.getGradeTo() || pharmacyFiltrationDTO.getGradeTo() == 0)
			pharmacyFiltrationDTO.setGradeTo(MAX_GRADE);
		else if (pharmacyFiltrationDTO.getGradeFrom() == 0 && pharmacyFiltrationDTO.getGradeTo() == 0) {
			pharmacyFiltrationDTO.setGradeFrom(MIN_GRADE);
			pharmacyFiltrationDTO.setGradeTo(MAX_GRADE);
		}
		return pharmacyFiltrationDTO;
	}

	private IdentifiableDTO<PharmacyGradeDTO> MapPharmacyFiltrationDTOToIdentifiablePharmacyGradeDTO(PharmacyFiltrationRepositoryDTO pharmacy) {
		if(pharmacy == null) return null;
		
		return new IdentifiableDTO<PharmacyGradeDTO>(pharmacy.getPharmacyId(), new PharmacyGradeDTO(pharmacy.getName(), pharmacy.getAddress(), pharmacy.getDescription(), pharmacy.getGrade()));
	}

}
