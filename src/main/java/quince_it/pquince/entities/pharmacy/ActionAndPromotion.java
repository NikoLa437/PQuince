package quince_it.pquince.entities.pharmacy;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ActionAndPromotion {

	@Id
	private UUID id;
	
    @Column(name = "dateFrom")
	private Date dateFrom;
	
    @Column(name = "dateTo")
	private Date dateTo;
	
	@Column(name="percentOfDiscount")
	private double percentOfDiscount;
	
    @Enumerated(EnumType.STRING)
	@Column(name = "Type", nullable = false)
	private ActionAndPromotionType actionAndPromotionType;
	
	@ManyToOne
	private Pharmacy pharmacy;

	public ActionAndPromotion() {
		super();
	}

	public ActionAndPromotion(UUID id, Date dateFrom, Date dateTo, Pharmacy pharmacy,   double percentOfDiscount,
			ActionAndPromotionType actionAndPromotionType) {
		super();
		this.id = id;
		this.dateFrom = dateFrom;
		this.pharmacy=pharmacy;
		this.dateTo = dateTo;
		this.percentOfDiscount = percentOfDiscount;
		this.actionAndPromotionType = actionAndPromotionType;
	}
	
	public ActionAndPromotion(Date dateFrom, Date dateTo, Pharmacy pharmacy,  double percentOfDiscount,
			ActionAndPromotionType actionAndPromotionType) {
		super();
		this.id = UUID.randomUUID();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.pharmacy=pharmacy;
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
