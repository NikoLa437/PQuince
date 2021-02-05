package quince_it.pquince.services.contracts.interfaces.users;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Authority;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.services.contracts.dto.EntityIdDTO;
import quince_it.pquince.services.contracts.dto.drugs.AllergenUserDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.users.AddDermatologistToPharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.DermatologistFiltrationDTO;
import quince_it.pquince.services.contracts.dto.users.IdentifiableDermatologistForPharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.users.PatientDTO;
import quince_it.pquince.services.contracts.dto.users.RemoveDermatologistFromPharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.PharmacistForPharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.users.StaffDTO;
import quince_it.pquince.services.contracts.dto.users.StaffGradeDTO;
import quince_it.pquince.services.contracts.dto.users.UserDTO;
import quince_it.pquince.services.contracts.dto.users.UserInfoChangeDTO;
import quince_it.pquince.services.contracts.dto.users.UserRequestDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface IUserService extends IService<UserDTO, IdentifiableDTO<UserDTO>> {

	IdentifiableDTO<UserDTO> findByEmail ( String email );
	
	UUID createPatient(UserRequestDTO entityDTO);
	
	void changePassword(String oldPassword, String newPassword);
	
	UUID getLoggedUserId();
	
	IdentifiableDTO<PatientDTO> getPatientById();
	
	IdentifiableDTO<StaffDTO> getStaff();
	
	boolean activatePatientAccount(UUID id);
	
	boolean addAllergen(AllergenUserDTO allergenUserDTO);
	
	boolean removeAllergen(AllergenUserDTO allergenUserDTO);
	
	void updatePatient(UserInfoChangeDTO patientInfoChangeDTO);
	
	List<IdentifiableDTO<StaffGradeDTO>> findAllStaffWithAvgGradeByStaffType(StaffType staffType);
	
	void updateStaff(UserInfoChangeDTO staffInfoChangeDTO);
	
	List<IdentifiableDTO<UserDTO>> findByNameAndSurname(String name, String surname);
	
	void deleteAllPatientsPenalties();
	
	List<IdentifiableDermatologistForPharmacyGradeDTO> findAllDermatologistForPharmacy(UUID pharmacyId);
	
	List<IdentifiableDTO<PharmacistForPharmacyGradeDTO>> findAllFreePharmacistForPharmacy(Date startDateTime, UUID pharmacyId);

	List<IdentifiableDTO<PharmacistForPharmacyGradeDTO>> findAllFreePharmacistForPharmacySortByGradeAscending(Date startDateTime, UUID pharmacyId);
	
	List<IdentifiableDTO<PharmacistForPharmacyGradeDTO>> findAllFreePharmacistForPharmacySortByGradeDescending(Date startDateTime, UUID pharmacyId);

	List<Authority> getAuthorityById(UUID id);

	UUID createAdmin(UserRequestDTO entityDTO);

	UUID createPharmacist(UserRequestDTO entityDTO);

	UUID createDermatologist(UserRequestDTO userRequest);

	UUID createSupplier(UserRequestDTO entityDTO);


	boolean removeDermatologistFromPharmacy(RemoveDermatologistFromPharmacyDTO removeDermatologistFromPharmacyDTO);

	List<IdentifiableDermatologistForPharmacyGradeDTO> findAllDermatologistForEmplooyeToPharmacy(UUID pharmacyId);

	List<IdentifiableDTO<PharmacyDTO>> getPharmaciesWhereDermatologistWork(UUID dermatologistId);

	boolean addDermatologistToPharmacy(AddDermatologistToPharmacyDTO addDermatologistToPharmacyDTO);

	UUID createPharmacyAdmin(UserRequestDTO entityDTO, UUID pharmacyDTO);
	
	IdentifiableDTO<UserDTO> getPatientById(UUID patientId);

	List<IdentifiableDTO<PharmacyDTO>> getPharmacies();

	List<IdentifiableDermatologistForPharmacyGradeDTO> findByNameSurnameAndGradeForPharmacy(DermatologistFiltrationDTO dermatologistFiltrationDTO);

	List<IdentifiableDermatologistForPharmacyGradeDTO> findAllDermatologist();

	UUID getPharmacyIdForPharmacyAdmin();

	boolean subscribeToPharmacy(EntityIdDTO pharmacyIdDTO);

	boolean unsubscribeFromPharmacy(EntityIdDTO pharmacyIdDTO);

	boolean checkIfPatientSubscribed(UUID pharmacyId);
	
	Pharmacy getPharmacyForLoggedDermatologist();
}
