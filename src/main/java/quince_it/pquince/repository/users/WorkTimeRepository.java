package quince_it.pquince.repository.users;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.WorkTime;

public interface WorkTimeRepository extends JpaRepository<WorkTime, UUID>{
	@Query(value = "SELECT wt FROM WorkTime wt WHERE wt.staff.id = ?1 AND wt.endDate>= ?2 AND wt.startDate<=?2 AND wt.pharmacy.id =?3")
	WorkTime getWorkTimeForDermatologistForDateForPharmacy(UUID dermatologistId, Date date, UUID pharmacyId);

	@Query(value = "SELECT w from WorkTime w WHERE w.staff.staffType = 'PHARMACIST'"
				+ " AND w.startDate <= ?1 AND w.endDate >= ?1 AND w.startTime <= ?2 AND w.endTime >= ?2")
    List<WorkTime> findWorkTimesByDesiredConsultationTime(Date date, int hourOfFinishing);
	
	@Query(value = "SELECT w from WorkTime w WHERE w.staff.staffType = 'PHARMACIST'"
			+ " AND w.startDate <= ?1 AND w.endDate >= ?1 AND w.startTime <= ?2 AND w.endTime >= ?2 AND w.pharmacy.id = ?3")
	List<WorkTime> findWorkTimesByDesiredConsultationTimeAndPharmacyId(Date date, int hourOfFinishing, UUID pharmacyId);
	
	@Query(value = "SELECT w from WorkTime w WHERE w.staff.staffType = 'PHARMACIST'"
			+ " AND w.startDate <= ?1 AND w.endDate >= ?1 AND w.startTime <= ?2 AND w.endTime >= ?2 AND w.staff.id = ?3")
	WorkTime findWorkTimeByDesiredConsultationTimeAndPharmacistId(Date date, int hourOfFinishing, UUID pharmacistId);

	@Query(value = "SELECT wt FROM WorkTime wt WHERE wt.staff.id = ?1 AND wt.pharmacy.id =?2")
	List<WorkTime> findWorkTimesForDermatologistForPharmacy(UUID dermatologistId, UUID pharmacistId);
	
	@Query(value = "SELECT wt FROM WorkTime wt WHERE wt.staff.id = ?1 AND wt.startDate <= CURRENT_DATE AND wt.endDate >= CURRENT_DATE")
	List<WorkTime> findWorkTimesForDeramtologistAndCurrentDate(UUID dermatologistId);
	
	@Query(value = "SELECT wt FROM WorkTime wt WHERE wt.staff.id = ?1 AND wt.pharmacy.id =?2")
	List<WorkTime> findWorkTimesForPharmacistForPharmacy(UUID pharmacistId, UUID pharmacyId);
}
