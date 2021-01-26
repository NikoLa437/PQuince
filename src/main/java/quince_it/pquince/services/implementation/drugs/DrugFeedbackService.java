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
	
	@Override
	public void create(DrugFeedbackDTO entityDTO) {
		// TODO eReciept check, logged patient
		
		try {
			Patient patient = patientRepository.findById(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")).get();
			System.out.println("USAO1");

			
			if(!CanPatientGiveFeedback(patient.getId())) throw new FeedbackNotAllowedException();
			
			System.out.println("USAO");
			System.out.println(entityDTO.getDrugId());
			DrugInstance drugInstance = drugInstanceRepository.findById(entityDTO.getDrugId()).get();
			DrugFeedback drugFeedback = new DrugFeedback(drugInstance,  patient, entityDTO.getGrade());
			System.out.println("USAO3");

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
		// TODO get logged patient
		
		try {
			Patient patient = patientRepository.findById(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")).get();
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
	public DrugFeedbackDTO findByPatientAndDrug(UUID patientId, UUID drugId) {
		return DrugFeedbackMapper.MapDrugFeedbackPersistenceToPDrugFeedbackDTO(drugFeedbackRepository.findByPatientAndDrug(patientId, drugId));
	}

	
}
