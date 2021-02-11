package quince_it.pquince.services.contracts.dto.drugs;

import java.util.Date;
import java.util.List;

import quince_it.pquince.entities.drugs.OrderStatus;

public class OrderDTO {

	private List<DrugForOrderDTO> drugs;
	
	private Date endDate;
	
	private int numberOfOffers;
	
	private String creator;
	
	private OrderStatus orderStatus;


	public OrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDTO(List<DrugForOrderDTO> drugs, Date endDate,int numberOfOffers,String creator, OrderStatus orderStatus) {
		super();
		this.drugs = drugs;
		this.endDate = endDate;
		this.numberOfOffers=numberOfOffers;
		this.creator=creator;
		this.orderStatus=orderStatus;
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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
