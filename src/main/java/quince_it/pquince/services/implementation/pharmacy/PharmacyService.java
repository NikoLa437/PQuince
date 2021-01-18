package quince_it.pquince.services.implementation.pharmacy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyService;
import quince_it.pquince.services.implementation.util.LocationUtil;
import quince_it.pquince.services.implementation.util.pharmacy.PharmacyMapper;

@Service
public class PharmacyService implements IPharmacyService {

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private PharmacyFeedbackService pharmacyFeedbackService;
	
	@Override
	public List<IdentifiableDTO<PharmacyDTO>> findAll() {
		List<IdentifiableDTO<PharmacyDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyDTO>>();
		pharmacyRepository.findAll().forEach((p) -> pharmacies.add(PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(p)));
	
		return pharmacies;
	}

	@Override
	public IdentifiableDTO<PharmacyDTO> findById(UUID id) {
		return PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(pharmacyRepository.getOne(id));
	}

	@Override
	public UUID create(PharmacyDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(PharmacyDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGrades() {
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacyRepository.findAll().forEach((p) -> pharmacies.add(MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(p)));

		return pharmacies;
	}
	
	public IdentifiableDTO<PharmacyGradeDTO> MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(Pharmacy pharmacy){
		if(pharmacy == null) throw new IllegalArgumentException();
		
		double avgGrade;
		
		try {
			avgGrade = pharmacyFeedbackService.findAvgGradeForPharmacy(pharmacy.getId());
		} catch (AopInvocationException e) {
			avgGrade = 0.0;
		}

		return new IdentifiableDTO<PharmacyGradeDTO>(pharmacy.getId(), new PharmacyGradeDTO(pharmacy.getName(), pharmacy.getAddress(), pharmacy.getDescription(),avgGrade));
	}

	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findByNameGradeAndDistance(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		
		if((pharmacyFiltrationDTO.getGradeFrom() == 0 && pharmacyFiltrationDTO.getGradeTo() == 0) || (pharmacyFiltrationDTO.getGradeFrom() == 0 && pharmacyFiltrationDTO.getGradeTo() > 0))
			pharmacies = findByNameAndCity(pharmacyFiltrationDTO.getName(), pharmacyFiltrationDTO.getCity());
		else
			pharmacies = pharmacyFeedbackService.findByNameCityAndGrade(pharmacyFiltrationDTO);
		
		if(pharmacyFiltrationDTO.getLatitude() < -500 || pharmacyFiltrationDTO.getDistanceFrom() == pharmacyFiltrationDTO.getDistanceTo()) return pharmacies;
		
		return findByDistance(pharmacies, pharmacyFiltrationDTO);
	}
	
	private List<IdentifiableDTO<PharmacyGradeDTO>> findByDistance(List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies, PharmacyFiltrationDTO pharmacyFiltrationDTO){
		
		List<IdentifiableDTO<PharmacyGradeDTO>> returnPharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		
		for(IdentifiableDTO<PharmacyGradeDTO> pharmacy : pharmacies) {
			double distance = LocationUtil.calculateDistance(pharmacy.EntityDTO.getAddress().getLatitude(), pharmacy.EntityDTO.getAddress().getLongitude(), pharmacyFiltrationDTO.getLatitude(), pharmacyFiltrationDTO.getLongitude());
			if(isDistanceInRange(distance, pharmacyFiltrationDTO.getDistanceFrom(), pharmacyFiltrationDTO.getDistanceTo()))
				returnPharmacies.add(pharmacy);
		}
		
		return returnPharmacies;
	}

	private boolean isDistanceInRange(double distance, double distanceFrom, double distanceTo) {
		return distance >= distanceFrom && distance <= distanceTo;
	}
	
	private List<IdentifiableDTO<PharmacyGradeDTO>> findByNameAndCity(String name, String city){
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacyRepository.findByNameAndCity(name.toLowerCase(), city.toLowerCase()).forEach((p) -> pharmacies.add(MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(p)));

		return pharmacies;
	}
	
}
