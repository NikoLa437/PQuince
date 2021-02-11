package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExaminationsStatisticsDTO {
	private Map<Integer, Integer> montlyStatistics;
	private Map<Integer, Integer> quartalStatistics;
	private Map<Integer, Integer> yearsStatistics;

	public ExaminationsStatisticsDTO() {
		super();
		montlyStatistics = new HashMap<Integer,Integer>();
		montlyStatistics.put(0, 0);
		montlyStatistics.put(1, 0);
		montlyStatistics.put(2, 0);
		montlyStatistics.put(3, 0);
		montlyStatistics.put(4, 0);
		montlyStatistics.put(5, 0);
		montlyStatistics.put(6, 0);
		montlyStatistics.put(7, 0);
		montlyStatistics.put(8, 0);
		montlyStatistics.put(9, 0);
		montlyStatistics.put(10, 0);
		montlyStatistics.put(11, 0);
		montlyStatistics.put(12, 0);
		quartalStatistics = new HashMap<Integer,Integer>();
		quartalStatistics.put(0, 0);
		quartalStatistics.put(1, 0);
		quartalStatistics.put(2, 0);
		quartalStatistics.put(3, 0);
		
		yearsStatistics= new HashMap<Integer,Integer>();
		yearsStatistics.put(0, 0);
		yearsStatistics.put(1, 0);
		yearsStatistics.put(2, 0);

	}
	
	public ExaminationsStatisticsDTO(Map<Integer, Integer> montlyStatistics,Map<Integer, Integer> quartalStatistics,Map<Integer, Integer> yearsStatistics) {
		super();
		this.montlyStatistics = montlyStatistics;
		this.quartalStatistics= quartalStatistics;
		this.yearsStatistics=yearsStatistics;
	}

	public Map<Integer, Integer> getMontlyStatistics() {
		return montlyStatistics;
	}

	public void setMontlyStatistics(Map<Integer, Integer> montlyStatistics) {
		this.montlyStatistics = montlyStatistics;
	}
	
	
	
	public Map<Integer, Integer> getQuartalStatistics() {
		return quartalStatistics;
	}

	public void setQuartalStatistics(Map<Integer, Integer> quartalStatistics) {
		this.quartalStatistics = quartalStatistics;
	}
	
	

	public Map<Integer, Integer> getYearsStatistics() {
		return yearsStatistics;
	}

	public void setYearsStatistics(Map<Integer, Integer> yearsStatistics) {
		this.yearsStatistics = yearsStatistics;
	}

	public void incrementMap(int index) {
		int value = montlyStatistics.get(index);
		montlyStatistics.put(index, ++value);
	}
	
	public void setFirstQuartalValue(int value) {
		quartalStatistics.put(0, value);
	}

	public void setSecondQuartalValue(int value) {
		quartalStatistics.put(1, value);
	}

	public void setThirdQuartalValue(int value) {
		quartalStatistics.put(2, value);
		
	}

	public void setFourthQuartalValue(int value) {
		quartalStatistics.put(3, value);

	}
	
	public void setThisYearValue(int value) {
		yearsStatistics.put(0,value);
	}
	
	public void setLastYearValue(int value) {
		yearsStatistics.put(1,value);
	}
	
	public void setPrecededYearValue(int value) {
		yearsStatistics.put(2,value);
	}
	
}
