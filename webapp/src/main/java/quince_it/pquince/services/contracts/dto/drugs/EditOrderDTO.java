package quince_it.pquince.services.contracts.dto.drugs;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class EditOrderDTO {
	
	private UUID orderId;
	
	private List<DrugOrderDTO> drugs;
	
	private Date endDate;

	public EditOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EditOrderDTO(List<DrugOrderDTO> drugs, Date endDate,UUID orderId) {
		super();
		this.drugs = drugs;
		this.endDate = endDate;
		this.orderId= orderId;
	}

	public List<DrugOrderDTO> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<DrugOrderDTO> drugs) {
		this.drugs = drugs;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public UUID getOrderId() {
		return orderId;
	}

	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	
}
