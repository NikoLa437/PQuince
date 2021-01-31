package quince_it.pquince.repository.users;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.users.WorkTime;

public interface WorkTimeRepository extends JpaRepository<WorkTime, UUID>{
	@Query(value = "SELECT wt FROM WorkTime wt WHERE wt.forStaff.id = ?1 AND wt.endDate>= ?2 AND wt.startDate<=?2 AND wt.forPharmacy.id =?3")
	WorkTime getWorkTimeForDermatologistForDateForPharmacy(UUID dermatologistId, Date date, UUID pharmacyId);
}
