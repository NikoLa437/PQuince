package quince_it.pquince.repository.users;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffType;

public interface StaffRepository  extends JpaRepository<Staff, UUID>{

	@Query(value = "SELECT s FROM Staff s WHERE s.staffType = ?1")
	List<Staff> findAllStaffByStaffType(StaffType staffType);

}
