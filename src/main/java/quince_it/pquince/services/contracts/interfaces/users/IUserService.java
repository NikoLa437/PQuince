package quince_it.pquince.services.contracts.interfaces.users;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.entities.users.Authority;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.services.contracts.dto.drugs.AllergenUserDTO;
import quince_it.pquince.services.contracts.dto.users.IdentifiableDermatologistForPharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.users.PatientDTO;
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
	
	IdentifiableDTO<PatientDTO> getPatientById(UUID id);
	
	IdentifiableDTO<StaffDTO> getStaffById(UUID id);
	
	boolean activatePatientAccount(UUID id);
	
	boolean addAllergen(AllergenUserDTO allergenUserDTO);
	
	boolean removeAllergen(AllergenUserDTO allergenUserDTO);
	
	void updatePatient(UUID patientId,UserInfoChangeDTO patientInfoChangeDTO);
	
	List<IdentifiableDTO<StaffGradeDTO>> findAllStaffWithAvgGradeByStaffType(StaffType staffType);
	
	void updateStaff(UUID staffId, UserInfoChangeDTO staffInfoChangeDTO);
	
	List<IdentifiableDTO<UserDTO>> findByNameAndSurname(String name, String surname);
	
	void deleteAllPatientsPenalties();
	
	List<IdentifiableDermatologistForPharmacyGradeDTO> findAllDermatologistForPharmacy(UUID pharmacyId);

	List<Authority> getAuthorityById(UUID id);

	UUID createSupplier(UserRequestDTO entityDTO);

	UUID createDermathologist(UserRequestDTO entityDTO);

	UUID createPharmacist(UserRequestDTO entityDTO);

	UUID createPharmacyAdmin(UserRequestDTO entityDTO);

	UUID createAdmin(UserRequestDTO entityDTO);


}
