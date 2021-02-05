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
import quince_it.pquince.repository.drugs.EReceiptItemsRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.services.contracts.dto.drugs.DrugFeedbackDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugsWithGradesDTO;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.drugs.IDrugFeedbackService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;
import quince_it.pquince.services.implementation.util.drugs.DrugFeedbackMapper;
import quince_it.pquince.services.implementation.util.drugs.DrugsWithGradesMapper;

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
	
	@Autowired
	private EReceiptItemsRepository eReceiptItemsRepository;
	
	@Override
	public void create(DrugFeedbackDTO entityDTO) throws FeedbackNotAllowedException {
		
		UUID patientId = userService.getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();
		
		CanPatientGiveFeedback(patient.getId(), entityDTO.getDrugId());
		
		
		DrugInstance drugInstance = drugInstanceRepository.findById(entityDTO.getDrugId()).get();
		DrugFeedback drugFeedback = new DrugFeedback(drugInstance,  patient, entityDTO.getGrade());

		drugFeedbackRepository.save(drugFeedback);
			
	}

	private void CanPatientGiveFeedback(UUID patientId, UUID drugId) throws FeedbackNotAllowedException {		
		if(!(drugReservationRepository.findProcessedDrugReservationsForPatientForDrug(patientId, drugId).size() > 0 || eReceiptItemsRepository.findAllByPatientAndDrugId(patientId, drugId).size() > 0))
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
	

	private List<DrugInstance> checkDrugKind(List<DrugInstance> drugs, String drugKind)
	{
		List<DrugInstance> drugsWithKind = new ArrayList<DrugInstance>();
		if(!drugKind.equals(""))
			for (DrugInstance var : drugs) 
			{
				if(var.getDrugKind().toString().equals(drugKind))
					drugsWithKind.add(var);
			}
		else
			drugsWithKind = drugs;
		
		return drugsWithKind;
	}
	
	
	private List<IdentifiableDTO<DrugsWithGradesDTO>> checkDrugGrades(List<DrugInstance> drugsWithKind, double gradeFrom, double gradeTo){
		List<IdentifiableDTO<DrugsWithGradesDTO>> drugsWithGrades = new ArrayList<IdentifiableDTO<DrugsWithGradesDTO>>();
		double grade;
		
		for (DrugInstance var : drugsWithKind) 
		{ 
			grade = findAvgGradeForDrug(var.getId());
			
			if(!(gradeFrom == -1.0 || gradeTo == -1.0)) {
				if(grade >= gradeFrom && grade <= gradeTo) {
					drugsWithGrades.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(var, grade));
				}
			}else {
				if(gradeFrom == -1.0 & gradeTo != -1.0) {
					if(grade <= gradeTo)
						drugsWithGrades.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(var, grade));
				}else if (gradeTo == -1.0 & gradeFrom != -1.0){
					if(grade >= gradeFrom)
						drugsWithGrades.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(var, grade));
				}else {
					drugsWithGrades.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(var, grade));
				}
			}
			
		}
		
		return drugsWithGrades;
	}
	
	@Override
	public List<IdentifiableDTO<DrugsWithGradesDTO>> searchDrugs(String name, double gradeFrom, double gradeTo, String drugKind) {
		List<DrugInstance> drugs;
		if(!name.equals("")) {
			drugs = drugInstanceRepository.findByName(name);
		}else
			drugs = drugInstanceRepository.findAll();
		
		List<DrugInstance> drugsWithKind = checkDrugKind(drugs, drugKind);
		List<IdentifiableDTO<DrugsWithGradesDTO>> drugsWithGrades = checkDrugGrades(drugsWithKind, gradeFrom, gradeTo);
		
		return drugsWithGrades;
	}
	
}
