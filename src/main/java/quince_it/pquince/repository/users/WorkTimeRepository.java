package quince_it.pquince.repository.users;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.users.WorkTime;

public interface WorkTimeRepository extends JpaRepository<WorkTime, UUID>{

	@Query(value = "SELECT w from WorkTime w WHERE w.staff.staffType = 'PHARMACIST'"
				+ " AND w.startDate <= ?1 AND w.endDate >= ?1 AND w.startTime <= ?2 AND w.endTime >= ?2")
    List<WorkTime> findWorkTimesByDesiredConsultationTime(Date date, int hourOfFinishing);
	
	@Query(value = "SELECT w from WorkTime w WHERE w.staff.staffType = 'PHARMACIST'"
			+ " AND w.startDate <= ?1 AND w.endDate >= ?1 AND w.startTime <= ?2 AND w.endTime >= ?2 AND w.pharmacy.id = ?3")
	List<WorkTime> findWorkTimesByDesiredConsultationTimeAndPharmacyId(Date date, int hourOfFinishing, UUID pharmacyId);
	
	@Query(value = "SELECT w from WorkTime w WHERE w.staff.staffType = 'PHARMACIST'"
			+ " AND w.startDate <= ?1 AND w.endDate >= ?1 AND w.startTime <= ?2 AND w.endTime >= ?2 AND w.staff.id = ?3")
	WorkTime findWorkTimeByDesiredConsultationTimeAndPharmacistId(Date date, int hourOfFinishing, UUID pharmacistId);
}
