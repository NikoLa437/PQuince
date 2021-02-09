package quince_it.pquince.repository.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.DrugInstance;
import quince_it.pquince.entities.drugs.EReceiptItems;
import quince_it.pquince.entities.drugs.EReceiptItemsId;
import quince_it.pquince.services.contracts.dto.drugs.IdentifiableEReceiptForDrugDTO;

public interface EReceiptItemsRepository extends JpaRepository<EReceiptItems, EReceiptItemsId>{

	
	@Query(value = "SELECT e FROM EReceiptItems e where e.eReceiptItemsId.eReceipt.id = ?1")
	List<EReceiptItems> findAllByEReceiptId(UUID id);	
	
	@Query(value = "SELECT DISTINCT e.eReceiptItemsId.drugInstance FROM EReceiptItems e where e.eReceiptItemsId.eReceipt.patient.id = ?1 AND e.eReceiptItemsId.eReceipt.status = 'PROCESSED'")
	List<DrugInstance> findAllProcessedDistinctDrugsByPatientId(UUID patientId);	
	
	@Query(value = "SELECT new quince_it.pquince.services.contracts.dto.drugs.IdentifiableEReceiptForDrugDTO(e.eReceiptItemsId.eReceipt.id, e.eReceiptItemsId.eReceipt.status,"
				+ " e.eReceiptItemsId.eReceipt.creationDate, e.eReceiptItemsId.eReceipt.pharmacy.name, e.amount, e.eReceiptItemsId.eReceipt.price)"
				+ " FROM EReceiptItems e where e.eReceiptItemsId.eReceipt.patient.id = ?1 AND"
				+ " e.eReceiptItemsId.eReceipt.status = 'PROCESSED' AND e.eReceiptItemsId.drugInstance.id = ?2")
	List<IdentifiableEReceiptForDrugDTO> findAllProcessedByPatientAndDrugId(UUID patientId, UUID drugId);
	
	@Query(value = "SELECT e FROM EReceiptItems e where e.eReceiptItemsId.eReceipt.patient.id = ?1 AND e.eReceiptItemsId.drugInstance.id = ?2")
	List<EReceiptItems> findAllByPatientAndDrugId(UUID patientId, UUID drugId);
}
