package quince_it.pquince.services.contracts.dto.drugs;

import java.util.Date;
import java.util.UUID;

import quince_it.pquince.entities.drugs.EReceiptStatus;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class IdentifiableEReceiptForDrugDTO extends IdentifiableDTO<EReceiptForDrugDTO>{

	public IdentifiableEReceiptForDrugDTO() {
		super();
	}

	public IdentifiableEReceiptForDrugDTO(UUID id, EReceiptStatus status, Date creationDate, String pharmacyName, int drugAmount, double price) {
		super(id, new EReceiptForDrugDTO(status, creationDate, pharmacyName, drugAmount, price));
	}

}
