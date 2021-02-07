package quince_it.pquince.services.contracts.interfaces.pharmacy;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import quince_it.pquince.services.contracts.dto.pharmacy.ActionAndPromotionsDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IActionAndPromotionsService {

	boolean create(ActionAndPromotionsDTO actionAndPromotions) throws MailException, InterruptedException, MessagingException;

	List<IdentifiableDTO<ActionAndPromotionsDTO>> findActionAndPromotionsInPharmacy();

}
