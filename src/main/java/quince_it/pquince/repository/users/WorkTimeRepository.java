package quince_it.pquince.repository.users;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.users.WorkTime;
import quince_it.pquince.services.contracts.dto.users.WorkTimeDTO;

public interface WorkTimeRepository extends JpaRepository<WorkTime, UUID>{
	@Query(value = "SELECT new quince_it.pquince.services.contracts.dto.users.WorkTimeDTO(wt.forPharmacy.id, wt.forStaff.id,wt.startDate,wt.endDate,wt.startTime,wt.endTime) FROM WorkTime wt WHERE wt.forStaff.id = ?1")
	List<WorkTimeDTO> findWorkTimeForStaff(UUID staffId);
}
