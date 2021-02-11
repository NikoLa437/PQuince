package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.HashMap;
import java.util.Map;

public class ExaminationsStatisticsDTO {
	private Map<Integer, Integer> montlyStatistics;
	private Map<Integer, Integer> quartalStatistics;

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
	}
	
	public ExaminationsStatisticsDTO(Map<Integer, Integer> montlyStatistics,Map<Integer, Integer> quartalStatistics) {
		super();
		this.montlyStatistics = montlyStatistics;
		this.quartalStatistics= quartalStatistics;
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
	
}
