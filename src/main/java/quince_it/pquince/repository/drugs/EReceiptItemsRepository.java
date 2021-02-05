package quince_it.pquince.repository.drugs;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.drugs.EReceiptItems;
import quince_it.pquince.entities.drugs.EReceiptItemsId;

public interface EReceiptItemsRepository extends JpaRepository<EReceiptItems, EReceiptItemsId>{

	
	@Query(value = "SELECT e FROM EReceiptItems e where e.eReceiptItemsId.eReceipt.id = ?1")
	List<EReceiptItems> findAllByEReceiptId(UUID id);	
}
