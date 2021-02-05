package quince_it.pquince.services.implementation.util.drugs;

import java.util.ArrayList;
import java.util.List;

import quince_it.pquince.entities.drugs.EReceipt;
import quince_it.pquince.entities.drugs.EReceiptItems;
import quince_it.pquince.services.contracts.dto.appointment.EReceiptWithDrugsDTO;
import quince_it.pquince.services.contracts.dto.drugs.DrugEReceiptDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class EReceiptMapper {

	public static IdentifiableDTO<EReceiptWithDrugsDTO> MapEReceiptPersistenceToEReceiptWithDrugsIdentifiableDTO(EReceipt eReceipt, List<EReceiptItems> eReceiptItems){
		if(eReceipt == null) throw new IllegalArgumentException();
		
		return new IdentifiableDTO<EReceiptWithDrugsDTO>(eReceipt.getId(), new EReceiptWithDrugsDTO(eReceipt.getStatus(),
																									eReceipt.getCreationDate(),
																									MapEReceiptItemsToDrugEReceiptDTOList(eReceiptItems)));
	}
	
	public static List<IdentifiableDTO<DrugEReceiptDTO>> MapEReceiptItemsToDrugEReceiptDTOList(List<EReceiptItems> eReceiptItems){
		
		List<IdentifiableDTO<DrugEReceiptDTO>> drugsDTO = new ArrayList<IdentifiableDTO<DrugEReceiptDTO>>();
		eReceiptItems.forEach((e) -> drugsDTO.add(new IdentifiableDTO<DrugEReceiptDTO>(e.getDrugInstance().getId(),
																					   new DrugEReceiptDTO(e.getDrugInstance().getDrugInstanceName(),
																							   			   e.getDrugInstance().getDrugFormat(),
																							   			   e.getAmount(), e.getDrugInstance().getDrugKind()))));
		
		return drugsDTO;
	}
}
