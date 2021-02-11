package quince_it.pquince.services.contracts.dto.pharmacy;

import java.util.HashMap;
import java.util.Map;

public class ExaminationsStatisticsDTO {
	private Map<Integer, Integer> montlyStatistics;

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
	}
	
	public ExaminationsStatisticsDTO(Map<Integer, Integer> montlyStatistics) {
		super();
		this.montlyStatistics = montlyStatistics;
	}

	public Map<Integer, Integer> getMontlyStatistics() {
		return montlyStatistics;
	}

	public void setMontlyStatistics(Map<Integer, Integer> montlyStatistics) {
		this.montlyStatistics = montlyStatistics;
	}
	
	public void incrementMap(int index) {
		int value = montlyStatistics.get(index);
		montlyStatistics.put(index, ++value);
	}
	
}
