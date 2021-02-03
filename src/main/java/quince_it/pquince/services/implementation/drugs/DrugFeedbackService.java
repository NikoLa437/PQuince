package quince_it.pquince.services.implementation.drugs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import quince_it.pquince.services.contracts.dto.drugs.DrugKindIdDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugsWithGradesDTO;
import quince_it.pquince.services.contracts.dto.users.UserDTO;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugFeedbackService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;
import quince_it.pquince.services.implementation.util.drugs.DrugFeedbackMapper;
import quince_it.pquince.services.implementation.util.drugs.DrugsWithGradesMapper;
import quince_it.pquince.services.implementation.util.users.UserMapper;

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
	
	@Override
	public double findAvgGradeForDrug(UUID drugId) {
		try {
			double retVal = drugFeedbackRepository.findAvgGradeForDrug(drugId);
			
			return  retVal;
		}catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<IdentifiableDTO<DrugsWithGradesDTO>> findDrugsWithGrades() {
		List<DrugInstance> drugs = drugInstanceRepository.findAll();
		List<IdentifiableDTO<DrugsWithGradesDTO>> drugsWithGrades = new ArrayList<IdentifiableDTO<DrugsWithGradesDTO>>();
		double grade;
		for (DrugInstance var : drugs) 
		{ 
			grade = findAvgGradeForDrug(var.getId());
			drugsWithGrades.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(var, grade));
			
		}
	
		return drugsWithGrades;
	}
	
}
