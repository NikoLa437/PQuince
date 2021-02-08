package quince_it.pquince.repository.users;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.users.Absence;

public interface AbsenceRepository extends JpaRepository<Absence, UUID>{

	@Query(value = "SELECT a from Absence a WHERE a.forStaff.id = ?1 AND a.startDate <= ?2 AND a.endDate >= ?2 AND a.absenceStatus = 'ACCEPTED'")
	List<Absence> findPharmacistAbsenceByStaffIdAndDate(UUID staffId, Date date);
}
