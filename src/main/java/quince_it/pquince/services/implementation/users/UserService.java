package quince_it.pquince.services.implementation.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.Allergen;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Authority;
import quince_it.pquince.entities.users.Dermatologist;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.repository.users.DermatologistRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.repository.users.UserRepository;
import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.dto.drugs.AllergenUserDTO;
import quince_it.pquince.services.contracts.dto.users.AuthorityDTO;
import quince_it.pquince.services.contracts.dto.users.IdentifiableDermatologistForPharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.users.PatientDTO;
import quince_it.pquince.services.contracts.dto.users.StaffDTO;
import quince_it.pquince.services.contracts.dto.users.StaffGradeDTO;
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
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private AllergenService allergenService;

	@Autowired
	private AuthorityService authorityService;
	
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
	public List<Authority> getAuthorityById(UUID id) {
		 User user = userRepository.getOne(id);
		 return user.getUserAuthorities();
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
		IdentifiableDTO<AuthorityDTO> authority = authorityService.findByName("PATIENT");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		patient.setUserAuthorities(authorities);
		
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
	
	@Override
	public UUID createSupplier(UserRequestDTO entityDTO) {
		Staff staff = CreateSupplierFromDTO(entityDTO);
		IdentifiableDTO<AuthorityDTO> authority = authorityService.findByName("SUPPLIER");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		staff.setUserAuthorities(authorities);
		
		userRepository.save(staff);
		
		return staff.getId();
	}
	
	private Staff CreateSupplierFromDTO(UserRequestDTO staffDTO) {
		return new Staff(staffDTO.getEmail(), passwordEncoder.encode(staffDTO.getPassword()), staffDTO.getName(), staffDTO.getSurname(), staffDTO.getAddress(), staffDTO.getPhoneNumber(), StaffType.SUPPLIER);
	}
	
	private Patient CreatePatientFromDTO(UserRequestDTO patientDTO) {
		return new Patient(patientDTO.getEmail(), passwordEncoder.encode(patientDTO.getPassword()), patientDTO.getName(), patientDTO.getSurname(), patientDTO.getAddress(), patientDTO.getPhoneNumber());
	}
	
	@Override
	public UUID createDermathologist(UserRequestDTO entityDTO) {
		Staff staff = CreateDermathologistFromDTO(entityDTO);
		IdentifiableDTO<AuthorityDTO> authority = authorityService.findByName("DERMATHOLOGIST");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		staff.setUserAuthorities(authorities);
		
		userRepository.save(staff);
		
		return staff.getId();
	}
	
	private Staff CreateDermathologistFromDTO(UserRequestDTO staffDTO) {
		return new Staff(staffDTO.getEmail(), passwordEncoder.encode(staffDTO.getPassword()), staffDTO.getName(), staffDTO.getSurname(), staffDTO.getAddress(), staffDTO.getPhoneNumber(), StaffType.DERMATOLOGIST);
	}

	@Override
	public UUID createPharmacist(UserRequestDTO entityDTO) {
		Staff staff = CreatePharmacistFromDTO(entityDTO);
		IdentifiableDTO<AuthorityDTO> authority = authorityService.findByName("PHARMACIST");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		staff.setUserAuthorities(authorities);
		
		userRepository.save(staff);
		
		return staff.getId();
	}
	
	private Staff CreatePharmacistFromDTO(UserRequestDTO staffDTO) {
		return new Staff(staffDTO.getEmail(), passwordEncoder.encode(staffDTO.getPassword()), staffDTO.getName(), staffDTO.getSurname(), staffDTO.getAddress(), staffDTO.getPhoneNumber(), StaffType.PHARMACIST);
	}
	
	@Override
	public UUID createPharmacyAdmin(UserRequestDTO entityDTO) {
		Staff staff = CreatePharmacyAdminFromDTO(entityDTO);
		IdentifiableDTO<AuthorityDTO> authority = authorityService.findByName("PHARMACYADMIN");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		staff.setUserAuthorities(authorities);
		
		userRepository.save(staff);
		
		return staff.getId();
	}
	
	private Staff CreatePharmacyAdminFromDTO(UserRequestDTO staffDTO) {
		return new Staff(staffDTO.getEmail(), passwordEncoder.encode(staffDTO.getPassword()), staffDTO.getName(), staffDTO.getSurname(), staffDTO.getAddress(), staffDTO.getPhoneNumber(), StaffType.PHARMACYADMIN);
	}
	
	@Override
	public UUID createAdmin(UserRequestDTO entityDTO) {
		Staff staff = CreateAdminFromDTO(entityDTO);
		IdentifiableDTO<AuthorityDTO> authority = authorityService.findByName("SYSADMIN");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		staff.setUserAuthorities(authorities);
		
		userRepository.save(staff);
		
		return staff.getId();
	}
	
	private Staff CreateAdminFromDTO(UserRequestDTO staffDTO) {
		return new Staff(staffDTO.getEmail(), passwordEncoder.encode(staffDTO.getPassword()), staffDTO.getName(), staffDTO.getSurname(), staffDTO.getAddress(), staffDTO.getPhoneNumber(), StaffType.SYSADMIN);
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
	public IdentifiableDTO<StaffDTO> getStaffById(UUID id) {	
		return UserMapper.MapStaffPersistenceToStaffIdentifiableDTO(staffRepository.getOne(id));
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
	public void updateStaff(UUID staffId, UserInfoChangeDTO staffInfoChangeDTO) {
		Staff staff = staffRepository.getOne(staffId);		
		
		staff.setAddress(staffInfoChangeDTO.getAddress());
		staff.setName(staffInfoChangeDTO.getName());
		staff.setPhoneNumber(staffInfoChangeDTO.getPhoneNumber());
		staff.setSurname(staffInfoChangeDTO.getSurname());
		
		staffRepository.save(staff);
	}
	
	@Override
	public List<IdentifiableDTO<StaffGradeDTO>> findAllStaffWithAvgGradeByStaffType(StaffType staffType) {
		
		List<Staff> staffs = staffRepository.findAllStaffByStaffType(staffType);
		List<IdentifiableDTO<StaffGradeDTO>> retStaffs = new ArrayList<IdentifiableDTO<StaffGradeDTO>>();
		
		staffs.forEach((s) -> retStaffs.add(MapStaffPersistenceToStaffGradeIdentifiableDTO(s)));
		return retStaffs;
	}

	
	public IdentifiableDTO<StaffGradeDTO> MapStaffPersistenceToStaffGradeIdentifiableDTO(Staff staff){
		if(staff == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<StaffGradeDTO>(staff.getId(), new StaffGradeDTO(staff.getEmail(), staff.getName(), staff.getSurname(),
											 staff.getAddress(), staff.getPhoneNumber(), staffFeedbackService.findAvgGradeForStaff(staff.getId())));
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
	public List<IdentifiableDermatologistForPharmacyGradeDTO> findAllDermatologistForPharmacy(UUID pharmacyId) {
		
		List<Dermatologist> dermatologists = dermatologistRepository.findAll();
		List<IdentifiableDermatologistForPharmacyGradeDTO> retDermatologist = new ArrayList<IdentifiableDermatologistForPharmacyGradeDTO>();
		
		for(Dermatologist dermatologist : dermatologists) {
			if(IsDermatogistWorkInPharmacy(dermatologist,pharmacyId))
				retDermatologist.add(MapDermatologistPersistenceToDermatolgoistForPharmacyGradeIdentifiableDTO(dermatologist));
		}
		
		return retDermatologist;
	}

	private IdentifiableDermatologistForPharmacyGradeDTO MapDermatologistPersistenceToDermatolgoistForPharmacyGradeIdentifiableDTO(
			Dermatologist dermatologist) {
		return new IdentifiableDermatologistForPharmacyGradeDTO(dermatologist.getId(),dermatologist.getEmail(),dermatologist.getName(),dermatologist.getSurname(),dermatologist.getPhoneNumber(),staffFeedbackService.findAvgGradeForStaff(dermatologist.getId()));
	}

	private boolean IsDermatogistWorkInPharmacy(Dermatologist dermatologist, UUID pharmacyId) {
		for(Pharmacy pharmacy : dermatologist.getPharmacies()) {
			if(pharmacy.getId().equals(pharmacyId))
				return true;
		}
		return false;
	}


	@Override
	public List<IdentifiableDTO<UserDTO>> findByNameAndSurname(String name, String surname) {
		List<IdentifiableDTO<UserDTO>> users = new ArrayList<IdentifiableDTO<UserDTO>>();
		patientRepository.findByNameAndSurname(name.toLowerCase(), surname.toLowerCase()).forEach((u) -> users.add(UserMapper.MapUserPersistenceToUserIdentifiableDTO(u)));
		return users;
	}

}
