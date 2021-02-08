package quince_it.pquince.services.contracts.dto.drugs;

import java.sql.Date;
import java.util.List;

public class OrderDTO {
	
	private List<DrugForOrderDTO> drugs;
	
	private Date endDate;
	
	private int numberOfOffers;
	
	private String creator;

	public OrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDTO(List<DrugForOrderDTO> drugs, Date endDate,int numberOfOffers,String creator) {
		super();
		this.drugs = drugs;
		this.endDate = endDate;
		this.numberOfOffers=numberOfOffers;
		this.creator=creator;
	}

	public List<DrugForOrderDTO> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<DrugForOrderDTO> drugs) {
		this.drugs = drugs;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getNumberOfOffers() {
		return numberOfOffers;
	}

	public void setNumberOfOffers(int numberOfOffers) {
		this.numberOfOffers = numberOfOffers;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	
}
