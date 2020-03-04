package data;

public class ProgressData {
	public String taskName;
	public int progress;
	public int total;
	
	public ProgressData(String taskName, int progress, int total) {
		this.taskName = taskName;
		this.progress = progress;
		this.total = total;
	}
}
