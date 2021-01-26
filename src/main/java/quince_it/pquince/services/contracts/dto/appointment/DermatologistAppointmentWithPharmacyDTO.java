package quince_it.pquince.services.contracts.dto.appointment;

import java.util.Date;

import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.IdentifiableStaffGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class DermatologistAppointmentWithPharmacyDTO extends DermatologistAppointmentDTO {

	private IdentifiableDTO<PharmacyDTO> pharmacy;
	
	public DermatologistAppointmentWithPharmacyDTO() {
		super();
	}

	public DermatologistAppointmentWithPharmacyDTO(IdentifiableStaffGradeDTO staff, Date startDateTime,
			Date endDateTime, double price, IdentifiableDTO<PharmacyDTO> pharmacy) {
		super(staff, startDateTime, endDateTime, price);
		this.pharmacy = pharmacy;
	}

	public IdentifiableDTO<PharmacyDTO> getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(IdentifiableDTO<PharmacyDTO> pharmacy) {
		this.pharmacy = pharmacy;
	}
}
