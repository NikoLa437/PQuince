package quince_it.pquince.entities.pharmacy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class PharmacyIncomeStatistics {
	private Map<Date, Double> incomeStatistics;
	private List<IncomeStatistics> listOfIncomeStatistics = new ArrayList<IncomeStatistics>();
	private double priceSum=0;
	
	public PharmacyIncomeStatistics() {
		super();
		incomeStatistics = new TreeMap<Date, Double>(new DateComparator());
	}

	public PharmacyIncomeStatistics(Map<Date, Double> incomeStatistics,double priceSum) {
		super();
		this.incomeStatistics = incomeStatistics;
		this.priceSum=priceSum;
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

	public List<IncomeStatistics> getListOfIncomeStatistics() {
		return listOfIncomeStatistics;
	}

	public void setListOfIncomeStatistics(List<IncomeStatistics> listOfIncomeStatistics) {
		this.listOfIncomeStatistics = listOfIncomeStatistics;
	}

	public void createList() {
		// TODO Auto-generated method stub
		for (Map.Entry<Date, Double> map : incomeStatistics.entrySet()) {
			listOfIncomeStatistics.add(new IncomeStatistics(map.getKey(),map.getValue()));
			priceSum+=map.getValue();
	    }
	}

	public double getPriceSum() {
		return priceSum;
	}

	public void setPriceSum(double priceSum) {
		this.priceSum = priceSum;
	}
	
	
}
