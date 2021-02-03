package quince_it.pquince.services.implementation.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.Allergen;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Authority;
import quince_it.pquince.entities.users.Dermatologist;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.PharmacyAdmin;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.DermatologistRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.PharmacyAdminRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.repository.users.UserRepository;
import quince_it.pquince.security.exception.ResourceConflictException;
import quince_it.pquince.services.contracts.dto.EntityIdDTO;
import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.dto.drugs.AllergenUserDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.AddDermatologistToPharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.AuthorityDTO;
import quince_it.pquince.services.contracts.dto.users.DermatologistFiltrationDTO;
import quince_it.pquince.services.contracts.dto.users.IdentifiableDermatologistForPharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.users.PatientDTO;
import quince_it.pquince.services.contracts.dto.users.PharmacistForPharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.users.RemoveDermatologistFromPharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.StaffDTO;
import quince_it.pquince.services.contracts.dto.users.StaffGradeDTO;
import quince_it.pquince.services.contracts.dto.users.UserDTO;
import quince_it.pquince.services.contracts.dto.users.UserInfoChangeDTO;
import quince_it.pquince.services.contracts.dto.users.UserRequestDTO;
import quince_it.pquince.services.contracts.dto.users.WorkTimeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.appointment.IAppointmentService;
import quince_it.pquince.services.contracts.interfaces.users.ILoyaltyProgramService;
import quince_it.pquince.services.contracts.interfaces.users.IStaffFeedbackService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;
import quince_it.pquince.services.implementation.drugs.AllergenService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.services.implementation.util.pharmacy.PharmacyMapper;
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
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private WorkTimeService workTimeService;
	
	@Autowired
	private AllergenService allergenService;

	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private IAppointmentService appointmentService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ILoyaltyProgramService loyalityProgramService;
	
	@Autowired
	private PharmacyAdminRepository pharmacyAdminRepository;
	
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
		
		IdentifiableDTO<UserDTO> existUser = findByEmail(entityDTO.getEmail());
		if (existUser != null) throw new ResourceConflictException(entityDTO.getEmail(), "Email already exists");
		
		Patient patient = CreatePatientFromDTO(entityDTO);
		IdentifiableDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_PATIENT");
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
		IdentifiableDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_SUPPLIER");
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
	public UUID createDermatologist(UserRequestDTO entityDTO) {
		Staff staff = CreateDermathologistFromDTO(entityDTO);
		IdentifiableDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_DERMATHOLOGIST");
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
		IdentifiableDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_PHARMACIST");
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
	public UUID createPharmacyAdmin(UserRequestDTO entityDTO, UUID pharmacyId) {
		//Pharmacy pharmacy = CreatePharmacyFromDTO(pharmacyDTO);
		Pharmacy pharmacy = pharmacyRepository.getOne(pharmacyId);
		System.out.println(pharmacy.getName() + pharmacy.getId() + "PHARMACY");
		PharmacyAdmin staff = CreatePharmacyAdminFromDTO(entityDTO, pharmacy);
		System.out.println(staff.getName() + staff.getEmail() + "STAFF");
		IdentifiableDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_PHARMACYADMIN");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		staff.setUserAuthorities(authorities);
		
		userRepository.save(staff);
		
		return staff.getId();
	}
	
	private PharmacyAdmin CreatePharmacyAdminFromDTO(UserRequestDTO staffDTO,Pharmacy pharmacy) {
		return new PharmacyAdmin(staffDTO.getEmail(), passwordEncoder.encode(staffDTO.getPassword()), staffDTO.getName(), staffDTO.getSurname(), staffDTO.getAddress(), staffDTO.getPhoneNumber(),pharmacy);
	}
	
	@Override
	public UUID createAdmin(UserRequestDTO entityDTO) {
		Staff staff = CreateAdminFromDTO(entityDTO);
		IdentifiableDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_SYSADMIN");
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
	public IdentifiableDTO<PatientDTO> getPatientById() {	
		UUID patientId = getLoggedUserId();
		return UserMapper.MapPatientPersistenceToPatientIdentifiableDTO(patientRepository.getOne(patientId),
																		loyalityProgramService.getLoggedPatientLoyalityProgram(patientId));
	}
	
	@Override
	public IdentifiableDTO<StaffDTO> getStaff() {	
		UUID staffId = getLoggedUserId();
		return UserMapper.MapStaffPersistenceToStaffIdentifiableDTO(staffRepository.getOne(staffId));
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
	public boolean removeDermatologistFromPharmacy(RemoveDermatologistFromPharmacyDTO removeDermatologistFromPharmacyDTO) {
		try {
			if(!appointmentService.hasAppointmentInFuture(removeDermatologistFromPharmacyDTO)) {
				Dermatologist dermatologist = dermatologistRepository.getOne(removeDermatologistFromPharmacyDTO.getDermatologistId());
				dermatologist.removePharmacy(removeDermatologistFromPharmacyDTO.getPharmacyId());
				
				dermatologistRepository.save(dermatologist);
				
				workTimeService.removeWorkTimeForDermatologistForPharmacy(removeDermatologistFromPharmacyDTO);
				return true;
			}else {
				return false;
			}
		} 
		catch (EntityNotFoundException e) { return false; } 
		catch (IllegalArgumentException e) { return false; }
	}

	@Override
	public void updatePatient(UserInfoChangeDTO patientInfoChangeDTO) {
		
		UUID patientId = getLoggedUserId();
		Patient patient = patientRepository.getOne(patientId);		
		
		patient.setAddress(patientInfoChangeDTO.getAddress());
		patient.setName(patientInfoChangeDTO.getName());
		patient.setPhoneNumber(patientInfoChangeDTO.getPhoneNumber());
		patient.setSurname(patientInfoChangeDTO.getSurname());
		
		patientRepository.save(patient);
	}

	@Override
	public void updateStaff(UserInfoChangeDTO staffInfoChangeDTO) {
		UUID staffId = getLoggedUserId();
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
	
	@Override
	public List<IdentifiableDermatologistForPharmacyGradeDTO> findAllDermatologistForEmplooyeToPharmacy(UUID pharmacyId) {
		
		List<Dermatologist> dermatologists = dermatologistRepository.findAll();
		List<IdentifiableDermatologistForPharmacyGradeDTO> retDermatologist = new ArrayList<IdentifiableDermatologistForPharmacyGradeDTO>();
		
		for(Dermatologist dermatologist : dermatologists) {
			if(!IsDermatogistWorkInPharmacy(dermatologist,pharmacyId))
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

	@Override
	public List<IdentifiableDTO<PharmacistForPharmacyGradeDTO>> findAllFreePharmacistForPharmacy(Date startDateTime, UUID pharmacyId) {
		
		long time = startDateTime.getTime();
		Date endDateTime= new Date(time + (Integer.parseInt(env.getProperty("consultation_time")) * 60000));
				
		List<IdentifiableDTO<PharmacistForPharmacyGradeDTO>> pharmacists = new ArrayList<IdentifiableDTO<PharmacistForPharmacyGradeDTO>>();
		appointmentService.findAllDistinctPharmacistsForAppointmentTimeForPharmacy(startDateTime, endDateTime, pharmacyId).forEach((p) -> pharmacists.add(MapStaffPersistenceToPharmacistGradeIdentifiableDTO(p)));
		
		return pharmacists;
	}
	
	public  IdentifiableDTO<PharmacistForPharmacyGradeDTO> MapStaffPersistenceToPharmacistGradeIdentifiableDTO(Staff staff){
		if(staff == null) throw new IllegalArgumentException();
		
		double avgGrade = staffFeedbackService.findAvgGradeForStaff(staff.getId());
		return new IdentifiableDTO<PharmacistForPharmacyGradeDTO>(staff.getId(), new PharmacistForPharmacyGradeDTO(staff.getName(), staff.getSurname(), avgGrade));
	}

	@Override
	public List<IdentifiableDTO<PharmacistForPharmacyGradeDTO>> findAllFreePharmacistForPharmacySortByGradeAscending(Date startDateTime, UUID pharmacyId) {
		
		List<IdentifiableDTO<PharmacistForPharmacyGradeDTO>> pharmacists = findAllFreePharmacistForPharmacy(startDateTime, pharmacyId);
		Collections.sort(pharmacists, (s1, s2) -> Double.compare(s1.EntityDTO.getGrade(), s2.EntityDTO.getGrade()));

		return pharmacists;
	}

	@Override
	public List<IdentifiableDTO<PharmacistForPharmacyGradeDTO>> findAllFreePharmacistForPharmacySortByGradeDescending(Date startDateTime, UUID pharmacyId) {
		
		List<IdentifiableDTO<PharmacistForPharmacyGradeDTO>> pharmacists = findAllFreePharmacistForPharmacy(startDateTime, pharmacyId);
		Collections.sort(pharmacists, (s1, s2) -> Double.compare(s1.EntityDTO.getGrade(), s2.EntityDTO.getGrade()));
		Collections.reverse(pharmacists);

		return pharmacists;
	}

	@Override
	public UUID getLoggedUserId() {
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email = currentUser.getName();
		User user = userRepository.findByEmail(email);
		
		return user.getId();
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		
		UUID loggedUserId = getLoggedUserId();
		User user = userRepository.findById(loggedUserId).get();
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), oldPassword));
		
		if(newPassword.isEmpty())
			throw new IllegalArgumentException("Invalid new password");
		
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		
	}
	
	@Override
	public List<IdentifiableDTO<PharmacyDTO>> getPharmaciesWhereDermatologistWork(UUID dermatologistId) {
		try {
			Dermatologist dermatologist = dermatologistRepository.getOne(dermatologistId);
			List<IdentifiableDTO<PharmacyDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyDTO>>();
			dermatologist.getPharmacies().forEach((p) -> pharmacies.add(PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(p)));
			return pharmacies;
		}catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public List<IdentifiableDTO<PharmacyDTO>> getPharmacies() {
		try {
			UUID dermatologistId = getLoggedUserId();
			Dermatologist dermatologist = dermatologistRepository.getOne(dermatologistId);
			List<IdentifiableDTO<PharmacyDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyDTO>>();
			dermatologist.getPharmacies().forEach((p) -> pharmacies.add(PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(p)));
			return pharmacies;
		}catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public boolean addDermatologistToPharmacy(AddDermatologistToPharmacyDTO addDermatologistToPharmacyDTO) {
		try {
			Dermatologist dermatologist = dermatologistRepository.getOne(addDermatologistToPharmacyDTO.getDermatologistId());
			Pharmacy pharmacy = pharmacyRepository.getOne(addDermatologistToPharmacyDTO.getPharmacyId());
			
			dermatologist.addPharmacy(pharmacy);
			dermatologistRepository.save(dermatologist);
			
			WorkTimeDTO workTimeDTO = new WorkTimeDTO(addDermatologistToPharmacyDTO.getPharmacyId(),addDermatologistToPharmacyDTO.getDermatologistId(), addDermatologistToPharmacyDTO.getStartDate(), addDermatologistToPharmacyDTO.getEndDate(), addDermatologistToPharmacyDTO.getStartTime(), addDermatologistToPharmacyDTO.getEndTime(),"");
			workTimeService.create(workTimeDTO);
			return true;
		} 
		catch (EntityNotFoundException e) { return false; } 
		catch (IllegalArgumentException e) { return false; }
	}
	
	@Override
	public List<IdentifiableDermatologistForPharmacyGradeDTO> findByNameSurnameAndGradeForPharmacy(DermatologistFiltrationDTO dermatologistFiltrationDTO) {
		
		List<IdentifiableDermatologistForPharmacyGradeDTO> dermatologistForSearch = this.findAllDermatologistForPharmacy(dermatologistFiltrationDTO.getPharmacyId());
		
		if(!dermatologistFiltrationDTO.getName().equals("")&&(!dermatologistFiltrationDTO.getSurname().equals("")))
			return findDermatologistByNameAndSurname(dermatologistForSearch, dermatologistFiltrationDTO);
		else if(!dermatologistFiltrationDTO.getName().equals(""))
			return findDermatologistByName(dermatologistForSearch,dermatologistFiltrationDTO);
		else if(!dermatologistFiltrationDTO.getSurname().equals(""))
			return findDermatologistBySurname(dermatologistForSearch,dermatologistFiltrationDTO);
		else if(dermatologistFiltrationDTO.getGradeFrom()!=-1 || dermatologistFiltrationDTO.getGradeTo()!=-1)
			return findDermatologistByGrade(dermatologistForSearch,dermatologistFiltrationDTO);	
		
		return dermatologistForSearch;
	}

	private List<IdentifiableDermatologistForPharmacyGradeDTO> findDermatologistBySurname(List<IdentifiableDermatologistForPharmacyGradeDTO> dermatologistForSearch, DermatologistFiltrationDTO dermatologistFiltrationDTO) {
		List<IdentifiableDermatologistForPharmacyGradeDTO> retVal = new ArrayList<IdentifiableDermatologistForPharmacyGradeDTO>();
		
		for(IdentifiableDermatologistForPharmacyGradeDTO dermatologist : dermatologistForSearch) {
			if(dermatologist.EntityDTO.getSurname().toLowerCase().contains(dermatologistFiltrationDTO.getSurname().toLowerCase()))
				retVal.add(dermatologist);
		}
		
		if(dermatologistFiltrationDTO.getGradeTo()!=-1 || dermatologistFiltrationDTO.getGradeTo()!=-1)
			return findDermatologistByGrade(retVal,dermatologistFiltrationDTO);
		
		return retVal;
	}
	@Override
	public IdentifiableDTO<UserDTO> getPatientById(UUID patientId) {
		return UserMapper.MapUserPersistenceToUserIdentifiableDTO(userRepository.getOne(patientId));
	}


	private List<IdentifiableDermatologistForPharmacyGradeDTO> findDermatologistByName(
			List<IdentifiableDermatologistForPharmacyGradeDTO> dermatologistForSearch, DermatologistFiltrationDTO dermatologistFiltrationDTO) {
		List<IdentifiableDermatologistForPharmacyGradeDTO> retVal = new ArrayList<IdentifiableDermatologistForPharmacyGradeDTO>();
		
		for(IdentifiableDermatologistForPharmacyGradeDTO dermatologist : dermatologistForSearch) {
			if(dermatologist.EntityDTO.getName().toLowerCase().contains(dermatologistFiltrationDTO.getName().toLowerCase()))
				retVal.add(dermatologist);
		}
		
		if(dermatologistFiltrationDTO.getGradeTo()!=-1 || dermatologistFiltrationDTO.getGradeTo()!=-1)
			return findDermatologistByGrade(retVal,dermatologistFiltrationDTO);
		
		return retVal;
	}

	private List<IdentifiableDermatologistForPharmacyGradeDTO> findDermatologistByGrade(List<IdentifiableDermatologistForPharmacyGradeDTO> dermatologistForSearch, DermatologistFiltrationDTO dermatologistFiltrationDTO) {
		List<IdentifiableDermatologistForPharmacyGradeDTO> retVal = new ArrayList<IdentifiableDermatologistForPharmacyGradeDTO>();
		
		for(IdentifiableDermatologistForPharmacyGradeDTO dermatologist : dermatologistForSearch) {
			if(dermatologist.EntityDTO.getGrade()>= dermatologistFiltrationDTO.getGradeFrom() && (dermatologist.EntityDTO.getGrade()< dermatologistFiltrationDTO.getGradeTo() || dermatologistFiltrationDTO.getGradeTo()==-1))
				retVal.add(dermatologist);
		}
		
		return retVal;
	}

	private List<IdentifiableDermatologistForPharmacyGradeDTO> findDermatologistByNameAndSurname(
			List<IdentifiableDermatologistForPharmacyGradeDTO> dermatologistForSearch, DermatologistFiltrationDTO dermatologistFiltrationDTO) {
		// TODO Auto-generated method stub
		List<IdentifiableDermatologistForPharmacyGradeDTO> retVal = new ArrayList<IdentifiableDermatologistForPharmacyGradeDTO>();
		
		for(IdentifiableDermatologistForPharmacyGradeDTO dermatologist : dermatologistForSearch) {
			if(dermatologist.EntityDTO.getName().toLowerCase().contains(dermatologistFiltrationDTO.getName().toLowerCase()) && dermatologist.EntityDTO.getSurname().toLowerCase().contains(dermatologistFiltrationDTO.getSurname().toLowerCase()))
				retVal.add(dermatologist);
		}
		
		if(dermatologistFiltrationDTO.getGradeTo()!=-1 || dermatologistFiltrationDTO.getGradeTo()!=-1)
			return findDermatologistByGrade(retVal,dermatologistFiltrationDTO);
		
		return retVal;	}
	
	@Override
	public List<IdentifiableDermatologistForPharmacyGradeDTO> findAllDermatologist() {
		List<IdentifiableDermatologistForPharmacyGradeDTO> retDermatologist = new ArrayList<IdentifiableDermatologistForPharmacyGradeDTO>();
		
		dermatologistRepository.findAll().forEach((d) -> retDermatologist.add(MapDermatologistPersistenceToDermatolgoistForPharmacyGradeIdentifiableDTO(d)));

		return retDermatologist;
	}

	public List<IdentifiableDermatologistForPharmacyGradeDTO> findByNameSurnameGradeAndPharmacy(DermatologistFiltrationDTO dermatologistFiltrationDTO) {
		
		List<IdentifiableDermatologistForPharmacyGradeDTO> retVal= new ArrayList<IdentifiableDermatologistForPharmacyGradeDTO>();
		List<Dermatologist> dermatologistForSearch = dermatologistRepository.findByNameAndSurname(dermatologistFiltrationDTO.getName().toLowerCase(),dermatologistFiltrationDTO.getSurname().toLowerCase());
		
		if(!dermatologistFiltrationDTO.getPharmacyId().toString().equals("00000000-0000-0000-0000-000000000000"))
			dermatologistForSearch = findDermatologistByPharmacy(dermatologistForSearch,dermatologistFiltrationDTO);
		
		dermatologistForSearch.forEach((d) -> retVal.add(MapDermatologistPersistenceToDermatolgoistForPharmacyGradeIdentifiableDTO(d)));
		
		if(dermatologistFiltrationDTO.getGradeTo()!=-1 || dermatologistFiltrationDTO.getGradeTo()!=-1)
			return findDermatologistByGrade(retVal,dermatologistFiltrationDTO);
		
		return retVal;
	}

	private List<Dermatologist> findDermatologistByPharmacy(List<Dermatologist> dermatologistForSearch, DermatologistFiltrationDTO dermatologistFiltrationDTO) {
		List<Dermatologist> retVal = new ArrayList<Dermatologist>();
		
		
		for(Dermatologist dermatologist : dermatologistForSearch) {
			if(IsDermatogistWorkInPharmacy(dermatologist,dermatologistFiltrationDTO.getPharmacyId()))
				retVal.add(dermatologist);
		}

		return retVal;
	}
	
	@Override
	public UUID getPharmacyIdForPharmacyAdmin() {
		UUID loggedUser= this.getLoggedUserId();
		
		PharmacyAdmin pharmacyAdmin = pharmacyAdminRepository.getOne(loggedUser);
		
		return pharmacyAdmin.getPharmacy().getId();
	}
	
	@Override
	public boolean subscribeToPharmacy(EntityIdDTO pharmacyIdDTO) {
		try {
			UUID loggedUser= this.getLoggedUserId();

			Patient patient = patientRepository.getOne(loggedUser);
			Pharmacy pharmacy = pharmacyRepository.getOne(pharmacyIdDTO.getId());
			patient.addSubscribeToPharmacy(pharmacy);
			
			patientRepository.save(patient);
			return true;
		} 
		catch (EntityNotFoundException e) { return false; } 
		catch (IllegalArgumentException e) { return false; }		
	}
	
	@Override
	public boolean unsubscribeFromPharmacy(EntityIdDTO pharmacyIdDTO) {
		// TODO Auto-generated method stub
		try {
			UUID loggedUser= this.getLoggedUserId();

			Patient patient = patientRepository.getOne(loggedUser);
			patient.removeSubscribeFromPharmacy(pharmacyIdDTO.getId());
			
			patientRepository.save(patient);
			return true;
		} 
		catch (EntityNotFoundException e) { return false; } 
		catch (IllegalArgumentException e) { return false; }	
	}
	
	@Override
	public boolean checkIfPatientSubscribed(UUID pharmacyId) {
		try {
			UUID loggedUser= this.getLoggedUserId();

			Patient patient = patientRepository.getOne(loggedUser);

			return patient.isPatientSubscribedToPharmacy(pharmacyId);
		} 
		catch (EntityNotFoundException e) { return false; } 
		catch (IllegalArgumentException e) { return false; }	
	}

	
}
