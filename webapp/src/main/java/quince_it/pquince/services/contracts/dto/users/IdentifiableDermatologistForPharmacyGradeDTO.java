package quince_it.pquince.services.contracts.dto.users;

import java.util.UUID;

import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class IdentifiableDermatologistForPharmacyGradeDTO extends IdentifiableDTO<DermatologistForPharmacyGradeDTO> {

	public IdentifiableDermatologistForPharmacyGradeDTO() {
		super();
	}

	public IdentifiableDermatologistForPharmacyGradeDTO(UUID id, String email, String name, String surname,String phoneNumber, double grade) {
		super(id, new DermatologistForPharmacyGradeDTO(email, name, surname, phoneNumber, grade));
	}
}
