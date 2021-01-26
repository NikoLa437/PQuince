package quince_it.pquince.services.implementation.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.Allergen;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.repository.users.UserRepository;
import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.dto.drugs.AllergenUserDTO;
import quince_it.pquince.services.contracts.dto.users.IdentifiableStaffGradeDTO;
import quince_it.pquince.services.contracts.dto.users.PatientDTO;
import quince_it.pquince.services.contracts.dto.users.UserDTO;
import quince_it.pquince.services.contracts.dto.users.UserInfoChangeDTO;
import quince_it.pquince.services.contracts.dto.users.UserRequestDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.users.IStaffFeedbackService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;
import quince_it.pquince.services.implementation.drugs.AllergenService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.services.implementation.util.users.UserMapper;

@Service
public class UserService implements IUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IStaffFeedbackService staffFeedbackService;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private AllergenService allergenService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public List<IdentifiableDTO<UserDTO>> findAll() {
		List<IdentifiableDTO<UserDTO>> users = new ArrayList<IdentifiableDTO<UserDTO>>();
		userRepository.findAll().forEach((u) -> users.add(UserMapper.MapUserPersistenceToUserIdentifiableDTO(u)));
		return users;
	}

	@Override
	public IdentifiableDTO<UserDTO> findById(UUID id) {
		return UserMapper.MapUserPersistenceToUserIdentifiableDTO(userRepository.getOne(id));
	}

	@Override
	public UUID create(UserDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(UserDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IdentifiableDTO<UserDTO> findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if (user == null)
			return null;
		return UserMapper.MapUserPersistenceToUserIdentifiableDTO(user);
	}

	@Override
	public UUID createPatient(UserRequestDTO entityDTO) {
		Patient patient = CreatePatientFromDTO(entityDTO);
		patientRepository.save(patient);
		try {
			emailService.sendSignUpNotificaitionAsync(patient);
		} catch (MailException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return patient.getId();
	}
	
	private Patient CreatePatientFromDTO(UserRequestDTO patientDTO) {
		return new Patient(patientDTO.getEmail(), passwordEncoder.encode(patientDTO.getPassword()), patientDTO.getName(), patientDTO.getSurname(), patientDTO.getAddress(), patientDTO.getPhoneNumber());
	}

	@Override
	public boolean activatePatientAccount(UUID id) {
		try {
			Patient patient = patientRepository.getOne(id);
			patient.setActive(true);
			patientRepository.save(patient);
			return true;
		}
		catch (EntityNotFoundException e) { return false; } 
		catch (IllegalArgumentException e) { return false; }
	}

	@Override
	public IdentifiableDTO<PatientDTO> getPatientById(UUID id) {	
		return UserMapper.MapPatientPersistenceToPatientIdentifiableDTO(patientRepository.getOne(id));
	}

	@Override
	public boolean addAllergen(AllergenUserDTO allergenUserDTO) {
		try {
			Patient patient = patientRepository.getOne(allergenUserDTO.getPatientId());
			IdentifiableDTO<AllergenDTO> allergenDTO = allergenService.findById(allergenUserDTO.getAllergenId());
			patient.addAllergen(new Allergen(allergenDTO.Id, allergenDTO.EntityDTO.getName()));
			
			patientRepository.save(patient);
			return true;
		} 
		catch (EntityNotFoundException e) { return false; } 
		catch (IllegalArgumentException e) { return false; }
	}

	@Override
	public boolean removeAllergen(AllergenUserDTO allergenUserDTO) {
		try {
			Patient patient = patientRepository.getOne(allergenUserDTO.getPatientId());			
			patient.removeAllergen(allergenUserDTO.getAllergenId());
			
			patientRepository.save(patient);
			return true;
		} 
		catch (EntityNotFoundException e) { return false; } 
		catch (IllegalArgumentException e) { return false; }
	}

	@Override
	public void updatePatient(UUID patientId, UserInfoChangeDTO patientInfoChangeDTO) {
		Patient patient = patientRepository.getOne(patientId);		
		
		patient.setAddress(patientInfoChangeDTO.getAddress());
		patient.setName(patientInfoChangeDTO.getName());
		patient.setPhoneNumber(patientInfoChangeDTO.getPhoneNumber());
		patient.setSurname(patientInfoChangeDTO.getSurname());
		
		patientRepository.save(patient);
	}

	@Override
	public List<IdentifiableStaffGradeDTO> findAllStaffWithAvgGradeByStaffType(StaffType staffType) {
		
		List<Staff> staffs = staffRepository.findAllStaffByStaffType(staffType);
		List<IdentifiableStaffGradeDTO> retStaffs = new ArrayList<IdentifiableStaffGradeDTO>();
		
		staffs.forEach((s) -> retStaffs.add(MapStaffPersistenceToStaffGradeIdentifiableDTO(s)));
		return retStaffs;
	}

	
	public IdentifiableStaffGradeDTO MapStaffPersistenceToStaffGradeIdentifiableDTO(Staff staff){
		if(staff == null) throw new IllegalArgumentException();
		
		return new IdentifiableStaffGradeDTO(staff.getId(), staff.getEmail(), staff.getName(), staff.getSurname(),
											 staff.getAddress(), staff.getPhoneNumber(), staffFeedbackService.findAvgGradeForStaff(staff.getId()));
	}

	@Override
	public void deleteAllPatientsPenalties() {
		try {
			List<Patient> patients = patientRepository.findAll();
			for(Patient patient : patients) {
				if(patient.getPenalty() > 0) {
					patient.setPenalty(0);
					patientRepository.save(patient);
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public List<IdentifiableDTO<UserDTO>> findByNameAndSurname(String name, String surname) {
		List<IdentifiableDTO<UserDTO>> users = new ArrayList<IdentifiableDTO<UserDTO>>();
		patientRepository.findByNameAndSurname(name.toLowerCase(), surname.toLowerCase()).forEach((u) -> users.add(UserMapper.MapUserPersistenceToUserIdentifiableDTO(u)));
		return users;
	}

}
