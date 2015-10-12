
public class Stats {
	private long id;
	private long cycleTime;
	
	public Stats(long id, long cycleTime) {
		super();
		this.setId(id);
		this.setCycleTime(cycleTime);
	}

	public double getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(long cycleTime) {
		this.cycleTime = cycleTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "File [id=" + id + ", Cycle Time=" + cycleTime + "]";
	}
}