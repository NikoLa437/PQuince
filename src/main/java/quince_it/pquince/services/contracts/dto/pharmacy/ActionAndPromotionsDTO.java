package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.Date;


import quince_it.pquince.entities.pharmacy.ActionAndPromotionType;

public class ActionAndPromotionsDTO {
	
	private Date dateFrom;
	
	private Date dateTo;
    
	private double percentOfDiscount;
	
	private ActionAndPromotionType actionAndPromotionType;

	public ActionAndPromotionsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActionAndPromotionsDTO(Date dateFrom, Date dateTo, double percentOfDiscount,
			ActionAndPromotionType actionAndPromotionType) {
		super();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.percentOfDiscount = percentOfDiscount;
		this.actionAndPromotionType = actionAndPromotionType;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}


	public double getPercentOfDiscount() {
		return percentOfDiscount;
	}

	public void setPercentOfDiscount(double percentOfDiscount) {
		this.percentOfDiscount = percentOfDiscount;
	}

	public ActionAndPromotionType getActionAndPromotionType() {
		return actionAndPromotionType;
	}

	public void setActionAndPromotionType(ActionAndPromotionType actionAndPromotionType) {
		this.actionAndPromotionType = actionAndPromotionType;
	}
}
