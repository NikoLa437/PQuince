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
	public void create(DrugFeedbackDTO entityDTO) {
		// TODO eReciept check
		
		try {
			UUID patientId = userService.getLoggedUserId();
			Patient patient = patientRepository.findById(patientId).get();
			
			if(!CanPatientGiveFeedback(patient.getId())) throw new FeedbackNotAllowedException();
			
			
			DrugInstance drugInstance = drugInstanceRepository.findById(entityDTO.getDrugId()).get();
			DrugFeedback drugFeedback = new DrugFeedback(drugInstance,  patient, entityDTO.getGrade());

			drugFeedbackRepository.save(drugFeedback);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private boolean CanPatientGiveFeedback(UUID patientId) {
		// TODO eReciept check
		return drugReservationRepository.findProcessedDrugReservationsForPatient(patientId).size() > 0;
	}

	@Override
	public void update(DrugFeedbackDTO entityDTO) {		
		try {
			UUID patientId = userService.getLoggedUserId();
			Patient patient = patientRepository.findById(patientId).get();
			
			DrugInstance drugInstance = drugInstanceRepository.findById(entityDTO.getDrugId()).get();
			
			DrugFeedback drugFeedback  = drugFeedbackRepository.findById(new DrugFeedbackId(drugInstance, patient)).get();
			drugFeedback.setDate(new Date());
			drugFeedback.setGrade(entityDTO.getGrade());
			
			drugFeedbackRepository.save(drugFeedback);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public DrugFeedbackDTO findByPatientAndDrug(UUID drugId) {
		UUID patientId = userService.getLoggedUserId();
		return DrugFeedbackMapper.MapDrugFeedbackPersistenceToPDrugFeedbackDTO(drugFeedbackRepository.findByPatientAndDrug(patientId, drugId));
	}

	
}
