package quince_it.pquince.repository.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.EReceipt;
import quince_it.pquince.entities.drugs.EReceiptStatus;

public interface EReceiptRepository extends JpaRepository<EReceipt, UUID> {

	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1")
	List<EReceipt> findAllByPatientId(UUID patientId);
	
	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1 ORDER BY e.creationDate ASC")
	List<EReceipt> findAllByPatientIdSortByDateAscending(UUID patientId);
	
	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1 ORDER BY e.creationDate DESC")
	List<EReceipt> findAllByPatientIdSortByDateDescending(UUID patientId);
	
	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1 AND e.status = ?2")
	List<EReceipt> findAllByPatientSearchByStatus(UUID patientId, EReceiptStatus status);
	
	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1 AND e.status = ?2 ORDER BY e.creationDate ASC")
	List<EReceipt> findAllByPatientSearchByStatusSortByDateAscending(UUID patientId, EReceiptStatus status);
	
	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1 AND e.status = ?2 ORDER BY e.creationDate DESC")
	List<EReceipt> findAllByPatientSearchByStatusSortByDateDescending(UUID patientId, EReceiptStatus status);
	
	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1 AND e.pharmacy.id = ?2")
	List<EReceipt> findAllByPatienIdAndPharmacy(UUID patientId, UUID pharmacyId);
}
