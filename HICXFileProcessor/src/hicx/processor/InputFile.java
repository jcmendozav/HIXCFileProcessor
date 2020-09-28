package hicx.processor;

import java.util.List;

public abstract class InputFile {
	public List<StatisticAlgorithm> getStats() {
		return stats;
	}

	public void setStats(List<StatisticAlgorithm> stats) {
		this.stats = stats;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	private List<StatisticAlgorithm> stats;
	private String name;
	private String extention;
	private String filePath;

	public void performStats() {
		stats.forEach(s -> s.perform(filePath));
	}

}
