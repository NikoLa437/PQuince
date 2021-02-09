package quince_it.pquince.repository.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.users.ComplaintStaff;
import quince_it.pquince.entities.users.StaffFeedbackId;

public interface ComplaintRepository extends JpaRepository<ComplaintStaff, UUID>{
	
	@Query(value = "SELECT s FROM ComplaintStaff s WHERE s.staffComplaintId.staff.id = ?1 AND s.staffComplaintId.patient.id = ?2")
	ComplaintStaff findByStaffIdAndPatientId(UUID pharmacyId, UUID patientId);
	
	@Query(value = "SELECT s FROM ComplaintPharmacy s WHERE s.complaintPharmacyId.pharmacy.id = ?1 AND s.complaintPharmacyId.patient.id = ?2")
	ComplaintStaff findByPharmacyIdAndPatientId(UUID pharmacyId, UUID patientId);
	
}
