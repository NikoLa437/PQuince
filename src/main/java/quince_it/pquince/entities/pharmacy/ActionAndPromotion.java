package quince_it.pquince.entities.pharmacy;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ActionAndPromotion {

	@Id
	private UUID id;
	
    @Column(name = "dateFrom")
	private Date dateFrom;
	
    @Column(name = "dateTo")
	private Date dateTo;
    
	@Column(name="text")
	private String text;
	
	@Column(name="percentOfDiscount")
	private double percentOfDiscount;
	
	@Column(name="type")
	private ActionAndPromotionType actionAndPromotionType;

	public ActionAndPromotion() {
		super();
	}

	public ActionAndPromotion(UUID id, Date dateFrom, Date dateTo, String text, double percentOfDiscount,
			ActionAndPromotionType actionAndPromotionType) {
		super();
		this.id = id;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.text = text;
		this.percentOfDiscount = percentOfDiscount;
		this.actionAndPromotionType = actionAndPromotionType;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
