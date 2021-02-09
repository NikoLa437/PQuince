package quince_it.pquince.services.implementation.util.users;

import quince_it.pquince.entities.pharmacy.ComplaintPharmacy;
import quince_it.pquince.services.contracts.dto.users.ComplaintPharmacyDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class ComplaintPharmacyMapper {

	
	public static IdentifiableDTO<ComplaintPharmacyDTO> MapComplaintPharmacyPersistenceToComplaintIdentifiableDTO(ComplaintPharmacy complaintPharmacy) {
		if(complaintPharmacy == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<ComplaintPharmacyDTO>(complaintPharmacy.getId(), new ComplaintPharmacyDTO(complaintPharmacy.getPharmacy().getId(), complaintPharmacy.getDate(), complaintPharmacy.getText(), complaintPharmacy.getName(), complaintPharmacy.getReply()));
	}
}
