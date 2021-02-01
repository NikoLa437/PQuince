package quince_it.pquince.services.contracts.interfaces.users;

import java.util.UUID;

import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.services.contracts.dto.users.LoyaltyProgramDTO;
import quince_it.pquince.services.contracts.dto.users.PatientLoyalityProgramDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface ILoyaltyProgramService extends IService<LoyaltyProgramDTO, IdentifiableDTO<LoyaltyProgramDTO>> {

	double getDiscountDrugPriceForPatient(double regularPrice);
	
	double getDiscountAppointmentPriceForPatient(double regularPrice, AppointmentType appointmentType);
	
	int getDiscountPercentageForAppointmentForPatient(AppointmentType appointmentType);
	
	PatientLoyalityProgramDTO getLoggedPatientLoyalityProgram(UUID patientId);
}
