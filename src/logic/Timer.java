package logic;

//do liczenia czasu wykonywania algorytmow
public class Timer {
	
	private long startTime=0;
	private long endTime=0;
	
	public void start() {
		startTime = System.nanoTime();
		endTime=0;
	}
	
	public void stop() {
		if(startTime==0)
			System.out.println("Timer nie zostal wlaczony");
		else
			endTime = System.nanoTime();
	}
	
	public long elapsed() {
		if(endTime==0)stop();
		return endTime-startTime;
	}
	
	public double elapsedms() {
		return (double)elapsed()/(double)1000000;
	}
}
