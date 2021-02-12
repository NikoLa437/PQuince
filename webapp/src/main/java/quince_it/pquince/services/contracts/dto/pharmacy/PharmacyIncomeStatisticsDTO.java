package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PharmacyIncomeStatisticsDTO {
	private Map<Date, Double> incomeStatistics;
	private List<IncomeStatisticsDTO> listOfIncomeStatistics = new ArrayList<IncomeStatisticsDTO>();
	
	
	public PharmacyIncomeStatisticsDTO() {
		super();
		incomeStatistics = new TreeMap<Date, Double>(new DateComparator());
	}

	public PharmacyIncomeStatisticsDTO(Map<Date, Double> incomeStatistics) {
		super();
		this.incomeStatistics = incomeStatistics;
	}

	public Map<Date, Double> getIncomeStatistics() {
		return incomeStatistics;
	}

	public void setIncomeStatistics(Map<Date, Double> incomeStatistics) {
		this.incomeStatistics = incomeStatistics;
	}

	public void addPrice(Date startDateTime, double priceToPay) {
		if(incomeStatistics.get(startDateTime)==null) {
			incomeStatistics.put(startDateTime, priceToPay);
		}else {
			double price = incomeStatistics.get(startDateTime);
			double newPrice = price+priceToPay;
			incomeStatistics.put(startDateTime, newPrice);
		}
		
	}
	
	public class DateComparator implements Comparator<Date> {
	    public int compare(Date date1, Date date2) {
	        return date1.compareTo(date2);
	    }
	}

	public List<IncomeStatisticsDTO> getListOfIncomeStatistics() {
		return listOfIncomeStatistics;
	}

	public void setListOfIncomeStatistics(List<IncomeStatisticsDTO> listOfIncomeStatistics) {
		this.listOfIncomeStatistics = listOfIncomeStatistics;
	}
}
