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

	@Query(value = "SELECT a from Absence a WHERE a.pharmacy.id=?1 AND a.absenceStatus = 'WAIT'")
	List<Absence> findNotProcessAbsenceForPharmacy(UUID pharmacyId);

	@Query(value = "SELECT a FROM Absence a WHERE a.forStaff.id = ?1 AND DATE(a.startDate) <= ?2 AND DATE(a.endDate) >= ?2 AND a.pharmacy.id =?3 AND a.absenceStatus = 'ACCEPTED'")
	List<Absence> getAbsenceForDermatologistForDateForPharmacy(UUID staff, Date startDateTime, UUID pharmacy);
	
	@Query(value = "SELECT a from Absence a WHERE a.forStaff.id = ?1 ORDER BY a.startDate DESC")
	List<Absence> findAllAbsencesByStaff(UUID staffId);
}
