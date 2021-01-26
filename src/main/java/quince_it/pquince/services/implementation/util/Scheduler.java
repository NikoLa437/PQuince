package quince_it.pquince.services.implementation.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import quince_it.pquince.services.contracts.interfaces.drugs.IDrugReservationService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;

@Component
public class Scheduler {

	@Autowired
	private IDrugReservationService drugReservationService;
	
	@Autowired
	private IUserService userService;
	
	@Scheduled(fixedRate = 300000, initialDelay = 2000)
	public void givePenaltyForMissedDrugReservation() {
		drugReservationService.givePenaltyForMissedDrugReservation();
	}
	
	@Scheduled(cron="0 0 1 1/1 * *")
	public void deleteAllPatientsPenalties() {
		userService.deleteAllPatientsPenalties();
	}
}
