package quince_it.pquince.services.implementation.pharmacy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.pharmacy.PharmacyFeedback;
import quince_it.pquince.entities.pharmacy.PharmacyFeedbackId;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.drugs.DrugReservationRepository;
import quince_it.pquince.repository.pharmacy.PharmacyFeedbackRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFeedbackDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationRepositoryDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyFeedbackService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;
import quince_it.pquince.services.implementation.util.pharmacy.PharmacyFeedbackMapper;

@Service
public class PharmacyFeedbackService implements IPharmacyFeedbackService {

	private int MAX_GRADE = 5;

	private int MIN_GRADE = 0;

	@Autowired
	private PharmacyFeedbackRepository pharmacyFeedbackRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private DrugReservationRepository drugReservationRepository;
	
	@Autowired
	private IUserService userService;

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

	@Override
	public void create(PharmacyFeedbackDTO entityDTO) throws FeedbackNotAllowedException {
		
		UUID patientId = userService.getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();		
		
		CanPatientGiveFeedback(patient.getId(), entityDTO.getPharmacyId());
		
		Pharmacy pharmacy = pharmacyRepository.findById(entityDTO.getPharmacyId()).get();
		PharmacyFeedback pharmacyFeedback = new PharmacyFeedback(pharmacy, entityDTO.getGrade(), patient);
		pharmacyFeedbackRepository.save(pharmacyFeedback);
		
	}

	private void CanPatientGiveFeedback(UUID patientId, UUID pharmacyId) throws FeedbackNotAllowedException {
		// TODO : eReciept check, consultation check!
		if(!(appointmentRepository.findAllPreviousAppointmentsForPatientForPharmacy(patientId, pharmacyId, AppointmentType.CONSULTATION).size() > 0 ||
			   appointmentRepository.findAllPreviousAppointmentsForPatientForPharmacy(patientId, pharmacyId, AppointmentType.EXAMINATION).size() > 0  ||
			   drugReservationRepository.findProcessedDrugReservationsForPatientForPharmacy(patientId, pharmacyId).size() > 0))
			throw new FeedbackNotAllowedException();
	}

	@Override
	public void update(PharmacyFeedbackDTO entityDTO) {
		
		UUID patientId = userService.getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();
		Pharmacy pharmacy = pharmacyRepository.findById(entityDTO.getPharmacyId()).get();
					
		PharmacyFeedback pharmacyFeedback = pharmacyFeedbackRepository.findById(new PharmacyFeedbackId(pharmacy, patient)).get();
		
		pharmacyFeedback.setDate(new Date());
		pharmacyFeedback.setGrade(entityDTO.getGrade());
		
		pharmacyFeedbackRepository.save(pharmacyFeedback);
	}

	@Override
	public PharmacyFeedbackDTO findByPatientAndPharmacy(UUID pharmacyId) {
		UUID patientId = userService.getLoggedUserId();
		return PharmacyFeedbackMapper.MapPharmacyFeedbackPersistenceToPharmacyFeedbackDTO(pharmacyFeedbackRepository.findByPatientAndPharmacy(patientId, pharmacyId));
	}

}
