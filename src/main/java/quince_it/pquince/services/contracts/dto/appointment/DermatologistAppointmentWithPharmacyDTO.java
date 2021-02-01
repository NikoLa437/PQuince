package quince_it.pquince.services.contracts.dto.appointment;

import java.util.Date;

import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.users.StaffGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class DermatologistAppointmentWithPharmacyDTO extends DermatologistAppointmentDTO {

	private IdentifiableDTO<PharmacyDTO> pharmacy;
	
	public DermatologistAppointmentWithPharmacyDTO() {
		super();
	}

	public DermatologistAppointmentWithPharmacyDTO(IdentifiableDTO<StaffGradeDTO> staff, Date startDateTime,
			Date endDateTime, double price, IdentifiableDTO<PharmacyDTO> pharmacy, double priceWithDiscount) {
		super(staff, startDateTime, endDateTime, price, priceWithDiscount);
		this.pharmacy = pharmacy;
	}

	public IdentifiableDTO<PharmacyDTO> getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(IdentifiableDTO<PharmacyDTO> pharmacy) {
		this.pharmacy = pharmacy;
	}
}
