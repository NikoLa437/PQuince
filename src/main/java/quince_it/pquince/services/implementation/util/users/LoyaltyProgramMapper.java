package quince_it.pquince.services.implementation.util.users;

import quince_it.pquince.entities.users.LoyaltyProgram;
import quince_it.pquince.services.contracts.dto.users.LoyaltyProgramDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class LoyaltyProgramMapper {
	
	public static IdentifiableDTO<LoyaltyProgramDTO> MapLoyaltyProgramPersistenceToLoyaltyProgramIdentifiableDTO(LoyaltyProgram loyaltyProgram){
		if(loyaltyProgram == null) throw new IllegalArgumentException();

		
		return new IdentifiableDTO<LoyaltyProgramDTO>(loyaltyProgram.getId(), new LoyaltyProgramDTO(loyaltyProgram.getPointsForAppointment(), loyaltyProgram.getPointsForConsulting(),
				loyaltyProgram.getPointsToEnterRegularCathegory(), loyaltyProgram.getPointsToEnterSilverCathegory(), loyaltyProgram.getPointsToEnterGoldCathegory()));

	}
	
}
