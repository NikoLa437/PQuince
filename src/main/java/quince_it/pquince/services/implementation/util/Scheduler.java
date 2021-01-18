package quince_it.pquince.services.implementation.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import quince_it.pquince.services.contracts.interfaces.drugs.IDrugReservationService;

@Component
public class Scheduler {

	@Autowired
	private IDrugReservationService drugReservationService;
	
	@Scheduled(fixedRate = 300000, initialDelay = 2000)
	public void givePenaltyForMissedDrugReservation() {
		drugReservationService.givePenaltyForMissedDrugReservation();
	}
}
