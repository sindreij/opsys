public class IO {
	private Queue ioQueue;
	private Statistics statistics;
	private Gui gui;
	private Process currentProcess;
	
	public IO(Queue ioQueue, Gui gui, Statistics statistics) {
		this.ioQueue = ioQueue;
		this.gui = gui;
		this.statistics = statistics;
	}
	
	public void insertProcess(Process p, long clock) {
		ioQueue.insert(p);
		p.enterIoQueue(clock);
	}
	
	public Process startProcess(long clock) {
		assert currentProcess == null;
		if (!ioQueue.isEmpty()) {
    		currentProcess = (Process)ioQueue.removeNext();
    		currentProcess.startIo(clock);
    	} else {
    		currentProcess = null;
    	}
    	gui.setIoActive(currentProcess);
    	return currentProcess;
	}
	
	public Process processFinished(long clock) {
		assert currentProcess != null;
		currentProcess.endIo(clock);
		Process finished = currentProcess;
		currentProcess = null;
		gui.setCpuActive(null);
		return finished;
	}
	
	public boolean idle() {
		return currentProcess == null;
	}
	
	public void timePassed(long timePassed) {
		statistics.ioQueueLengthTime += ioQueue.getQueueLength()*timePassed;
		if (ioQueue.getQueueLength() > statistics.ioQueueLargestLength) {
			statistics.ioQueueLargestLength = ioQueue.getQueueLength(); 
		}
	}
}