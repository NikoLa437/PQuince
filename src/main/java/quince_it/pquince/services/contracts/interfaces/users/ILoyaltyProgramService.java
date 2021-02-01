package quince_it.pquince.services.contracts.interfaces.users;

import quince_it.pquince.services.contracts.dto.users.LoyaltyProgramDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface ILoyaltyProgramService extends IService<LoyaltyProgramDTO, IdentifiableDTO<LoyaltyProgramDTO>> {

	double getDiscountDrugPriceForPatient(double regularPrice);
}
