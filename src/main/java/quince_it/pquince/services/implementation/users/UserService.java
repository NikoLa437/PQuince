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
import quince_it.pquince.entities.users.City;
import quince_it.pquince.entities.users.Country;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.UserRepository;
import quince_it.pquince.services.contracts.dto.drugs.AllergenDTO;
import quince_it.pquince.services.contracts.dto.drugs.AllergenUserDTO;
import quince_it.pquince.services.contracts.dto.users.CityDTO;
import quince_it.pquince.services.contracts.dto.users.CountryDTO;
import quince_it.pquince.services.contracts.dto.users.PatientDTO;
import quince_it.pquince.services.contracts.dto.users.UserDTO;
import quince_it.pquince.services.contracts.dto.users.UserInfoChangeDTO;
import quince_it.pquince.services.contracts.dto.users.UserRequestDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.ICityService;
import quince_it.pquince.services.contracts.interfaces.ICountryService;
import quince_it.pquince.services.contracts.interfaces.IUserService;
import quince_it.pquince.services.implementation.drugs.AllergenService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.services.implementation.util.UserMapper;

@Service
public class UserService implements IUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private ICityService cityService;
	
	@Autowired
	private AllergenService allergenService;
	
	@Autowired
	private ICountryService countryService;
	
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
		IdentifiableDTO<CityDTO> cityDTO = cityService.findById(patientDTO.getCityId());
		IdentifiableDTO<CountryDTO> countryDTO = countryService.findById(cityDTO.EntityDTO.getCountryId());
		
		City city = new City(cityDTO.Id, cityDTO.EntityDTO.getName(), new Country(countryDTO.Id, countryDTO.EntityDTO.getName()));
		return new Patient(patientDTO.getEmail(), passwordEncoder.encode(patientDTO.getPassword()), patientDTO.getName(), patientDTO.getSurname(), patientDTO.getAddress(), city, patientDTO.getPhoneNumber());
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
		
		System.out.println("City ID" + patientInfoChangeDTO.getCityId());
		IdentifiableDTO<CityDTO> city = cityService.findById(patientInfoChangeDTO.getCityId());
		IdentifiableDTO<CountryDTO> country = countryService.findById(city.EntityDTO.getCountryId());

		patient.setCity(new City(city.Id, city.EntityDTO.getName(), new Country(country.Id, country.EntityDTO.getName())));
		
		patientRepository.save(patient);
	}


}
