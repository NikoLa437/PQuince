package quince_it.pquince.services.contracts.dto.drugs;

import java.util.Date;

public class DrugRequestDTO {

	private String drugInstanceName;
	private String drugInstanceManufacturer;
	private String staffName;
	private Date date;
	public DrugRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public DrugRequestDTO(String drugInstanceName, String drugInstanceManufacturer, String staffName, Date date) {
		super();
		this.drugInstanceName = drugInstanceName;
		this.drugInstanceManufacturer = drugInstanceManufacturer;
		this.staffName = staffName;
		this.date = date;
	}


	public String getDrugInstanceName() {
		return drugInstanceName;
	}
	public void setDrugInstanceName(String drugInstanceName) {
		this.drugInstanceName = drugInstanceName;
	}
	public String getDrugInstanceManufacturer() {
		return drugInstanceManufacturer;
	}
	public void setDrugInstanceManufacturer(String drugInstanceManufacturer) {
		this.drugInstanceManufacturer = drugInstanceManufacturer;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
