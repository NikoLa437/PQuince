package quince_it.pquince.services.implementation.users;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.users.LoyalityCategory;
import quince_it.pquince.entities.users.LoyaltyProgram;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.repository.users.LoyaltyProgramRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.services.contracts.dto.users.LoyaltyProgramDTO;
import quince_it.pquince.services.contracts.dto.users.PatientLoyalityProgramDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.users.ILoyaltyProgramService;
import quince_it.pquince.services.implementation.util.users.LoyaltyProgramMapper;

@Service
public class LoyaltyProgramService implements ILoyaltyProgramService {


	@Autowired
	private LoyaltyProgramRepository loyaltyProgramRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	private final UUID LOYALITY_PROGRAM_ID = UUID.fromString("791fee27-bb12-4340-9b0a-a7c9ef575278");

	@Override
	public IdentifiableDTO<LoyaltyProgramDTO> findById(UUID id) {
		return LoyaltyProgramMapper.MapLoyaltyProgramPersistenceToLoyaltyProgramIdentifiableDTO(loyaltyProgramRepository.getOne(id));
	}

	@Override
	public boolean delete(UUID id) {
		return false;
	}

	@Override
	public UUID create(LoyaltyProgramDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(LoyaltyProgramDTO entityDTO, UUID id) {

		try {
			//LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findById(id).get();
			LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.getOne(id);
			loyaltyProgram.setPointsForAppointment(entityDTO.getPointsForAppointment());
			loyaltyProgram.setPointsForConsulting(entityDTO.getPointsForConsulting());
			loyaltyProgram.setPointsToEnterRegularCathegory(entityDTO.getPointsToEnterRegularCathegory());
			loyaltyProgram.setPointsToEnterSilverCathegory(entityDTO.getPointsToEnterSilverCathegory());
			loyaltyProgram.setPointsToEnterGoldCathegory(entityDTO.getPointsToEnterGoldCathegory());
			
			loyaltyProgram.setAppointmentDiscountRegular(entityDTO.getAppointmentDiscountRegular());
			loyaltyProgram.setDrugDiscountRegular(entityDTO.getDrugDiscountRegular());
			loyaltyProgram.setConsultationDiscountRegular(entityDTO.getConsultationDiscountRegular());

			loyaltyProgram.setAppointmentDiscountSilver(entityDTO.getAppointmentDiscountSilver());
			loyaltyProgram.setDrugDiscountSilver(entityDTO.getDrugDiscountSilver());
			loyaltyProgram.setConsultationDiscountSilver(entityDTO.getConsultationDiscountSilver());
			

			loyaltyProgram.setAppointmentDiscountGold(entityDTO.getAppointmentDiscountGold());
			loyaltyProgram.setDrugDiscountGold(entityDTO.getDrugDiscountGold());
			loyaltyProgram.setConsultationDiscountGold(entityDTO.getConsultationDiscountGold());
			
			loyaltyProgramRepository.save(loyaltyProgram);
			
		}catch (Exception e) {
		}
	}

	@Override
	public List<IdentifiableDTO<LoyaltyProgramDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDiscountDrugPriceForPatient(double regularPrice, UUID patientId) {

		PatientLoyalityProgramDTO patientLoyalityProgramDTO = getLoggedPatientLoyalityProgram(patientId);

		return ((100 - patientLoyalityProgramDTO.getDrugDiscount()) / 100.0 ) * regularPrice;
	}
	
	@Override
	public PatientLoyalityProgramDTO getLoggedPatientLoyalityProgram(UUID patientId) {

		Patient patient = patientRepository.findById(patientId).get();		
		LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findById(LOYALITY_PROGRAM_ID).get();
		
		return createPatientLoyalityProgramDTO(loyaltyProgram, patient.getPoints());
	}

	private PatientLoyalityProgramDTO createPatientLoyalityProgramDTO(LoyaltyProgram loyaltyProgram, int points) {
		
		if(points >= loyaltyProgram.getPointsToEnterGoldCathegory())
			return new PatientLoyalityProgramDTO(LoyalityCategory.GOLD, loyaltyProgram.getAppointmentDiscountGold(), loyaltyProgram.getConsultationDiscountGold(), loyaltyProgram.getDrugDiscountGold());
		else if (points >= loyaltyProgram.getPointsToEnterSilverCathegory() && points < loyaltyProgram.getPointsToEnterGoldCathegory())
			return new PatientLoyalityProgramDTO(LoyalityCategory.SILVER, loyaltyProgram.getAppointmentDiscountSilver(), loyaltyProgram.getConsultationDiscountSilver(), loyaltyProgram.getDrugDiscountSilver());
		else
			return new PatientLoyalityProgramDTO(LoyalityCategory.REGULAR, loyaltyProgram.getAppointmentDiscountRegular(), loyaltyProgram.getConsultationDiscountRegular(), loyaltyProgram.getDrugDiscountRegular());

	}

	@Override
	public double getDiscountAppointmentPriceForPatient(double regularPrice, AppointmentType appointmentType, UUID patientId) {
		
		PatientLoyalityProgramDTO patientLoyalityProgramDTO = getLoggedPatientLoyalityProgram(patientId);
		if(appointmentType.equals(AppointmentType.EXAMINATION))
			return ((100 - patientLoyalityProgramDTO.getAppointmentDiscount()) / 100.0) * regularPrice;
		else
			return ((100 - patientLoyalityProgramDTO.getConsultationDiscount()) / 100.0) * regularPrice;

	}

	@Override
	public int getDiscountPercentageForAppointmentForPatient(AppointmentType appointmentType, UUID patientId) {

		PatientLoyalityProgramDTO patientLoyalityProgramDTO = getLoggedPatientLoyalityProgram(patientId);
		if(appointmentType.equals(AppointmentType.EXAMINATION))
			return patientLoyalityProgramDTO.getAppointmentDiscount();
		else
			return patientLoyalityProgramDTO.getConsultationDiscount();
	}

		
}
