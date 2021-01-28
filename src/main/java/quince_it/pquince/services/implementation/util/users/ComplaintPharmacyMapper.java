package quince_it.pquince.services.implementation.util.users;

import quince_it.pquince.entities.pharmacy.ComplaintPharmacy;
import quince_it.pquince.services.contracts.dto.users.ComplaintPharmacyDTO;

public class ComplaintPharmacyMapper {

	
	public static ComplaintPharmacyDTO MapComplaintPharmacyPersistenceToComplaintPharmacyDTO(ComplaintPharmacy complaintPharmacy) {
		if(complaintPharmacy == null) throw new IllegalArgumentException();
		
		return new ComplaintPharmacyDTO(complaintPharmacy.getPharmacy().getId(), complaintPharmacy.getDate(), complaintPharmacy.getText());
	}
}
