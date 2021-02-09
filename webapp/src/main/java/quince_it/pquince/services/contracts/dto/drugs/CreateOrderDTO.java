package quince_it.pquince.services.contracts.dto.drugs;

import java.sql.Date;
import java.util.List;

public class CreateOrderDTO {
	private List<DrugOrderDTO> drugs;
	
	private Date endDate;

	public CreateOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateOrderDTO(List<DrugOrderDTO> drugs, Date endDate) {
		super();
		this.drugs = drugs;
		this.endDate = endDate;
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
}
