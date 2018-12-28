package pl.cybertech.diagnosis;


public class ExecutionBlock {

	long count;
	
	private long startTime;
	
	public ExecutionBlock() {
		start();
	}
	
	public void reset() {
		count = 0;
	}
	
	public void start() {
		startTime = System.currentTimeMillis();
	}
	
	public long end() {
		count++;
		return System.currentTimeMillis() - startTime;
	}

	public long getCount() {
		return count;
	}
}
