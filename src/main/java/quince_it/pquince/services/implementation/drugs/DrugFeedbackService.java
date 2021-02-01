package quince_it.pquince.services.implementation.drugs;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugFeedback;
import quince_it.pquince.entities.drugs.DrugFeedbackId;
import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.repository.drugs.DrugFeedbackRepository;
import quince_it.pquince.repository.drugs.DrugInstanceRepository;
import quince_it.pquince.repository.drugs.DrugReservationRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugFeedbackDTO;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugFeedbackService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;
import quince_it.pquince.services.implementation.util.drugs.DrugFeedbackMapper;

@Service
public class DrugFeedbackService implements IDrugFeedbackService{

	@Autowired
	private DrugFeedbackRepository drugFeedbackRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private DrugInstanceRepository drugInstanceRepository;
	
	@Autowired
	private DrugReservationRepository drugReservationRepository;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public void create(DrugFeedbackDTO entityDTO) throws FeedbackNotAllowedException {
		// TODO eReciept check
		
		UUID patientId = userService.getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();
		
		CanPatientGiveFeedback(patient.getId());
		
		
		DrugInstance drugInstance = drugInstanceRepository.findById(entityDTO.getDrugId()).get();
		DrugFeedback drugFeedback = new DrugFeedback(drugInstance,  patient, entityDTO.getGrade());

		drugFeedbackRepository.save(drugFeedback);
			
	}

	private void CanPatientGiveFeedback(UUID patientId) throws FeedbackNotAllowedException {
		// TODO eReciept check
		
		if(!(drugReservationRepository.findProcessedDrugReservationsForPatient(patientId).size() > 0))
			throw new FeedbackNotAllowedException();
	}

	@Override
	public void update(DrugFeedbackDTO entityDTO) {		
		UUID patientId = userService.getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();
		
		DrugInstance drugInstance = drugInstanceRepository.findById(entityDTO.getDrugId()).get();
		
		DrugFeedback drugFeedback  = drugFeedbackRepository.findById(new DrugFeedbackId(drugInstance, patient)).get();
		drugFeedback.setDate(new Date());
		drugFeedback.setGrade(entityDTO.getGrade());
		
		drugFeedbackRepository.save(drugFeedback);
	}

	@Override
	public DrugFeedbackDTO findByPatientAndDrug(UUID drugId) {
		UUID patientId = userService.getLoggedUserId();
		return DrugFeedbackMapper.MapDrugFeedbackPersistenceToPDrugFeedbackDTO(drugFeedbackRepository.findByPatientAndDrug(patientId, drugId));
	}

	
}
