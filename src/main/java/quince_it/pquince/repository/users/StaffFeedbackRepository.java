package quince_it.pquince.repository.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.users.StaffFeedback;
import quince_it.pquince.entities.users.StaffFeedbackId;

public interface StaffFeedbackRepository extends JpaRepository<StaffFeedback, StaffFeedbackId>{
	
	/*
	 * @Query(value =
	 * "SELECT new quince_it.pquince.services.contracts.dto.users.IdentifiableStaffGradeDTO(s.staffFedbackId.staff.id, s.staffFedbackId.staff.email, s.staffFedbackId.staff.name,"
	 * +
	 * " s.staffFedbackId.staff.surname, s.staffFedbackId.staff.address, s.staffFedbackId.staff.phoneNumber, AVG(s.grade)) "
	 * +
	 * "FROM StaffFeedback s WHERE s.staffFedbackId.staff.staffType = ?1 GROUP BY s.staffFedbackId.staff.id, s.staffFedbackId.staff.email, s.staffFedbackId.staff.name,"
	 * +
	 * " s.staffFedbackId.staff.surname,s.staffFedbackId.staff.address, s.staffFedbackId.staff.phoneNumber"
	 * ) List<IdentifiableStaffGradeDTO>
	 * findAllStaffWithAvgGradeByStaffType(StaffType staffType);
	 */
	
	@Query(value = "SELECT AVG(s.grade) FROM StaffFeedback s WHERE s.staffFedbackId.staff.id = ?1")
	double findAvgGradeForStaff(UUID staffId);
	
	@Query(value = "SELECT s FROM StaffFeedback s WHERE s.staffFedbackId.staff.id = ?1 AND s.staffFedbackId.patient.id = ?2")
	StaffFeedback findByStaffIdAndPatientId(UUID staffId, UUID patientId);
	
}
