public class Cpu {
    private Queue cpuQueue;
    private Statistics statistics;
    private Gui gui;
    private Process currentProcess;
    private long timeOfLastEvent = 0;

    public Cpu(Queue cpuQueue, Gui gui, Statistics statistics) {
        this.gui = gui;
        this.cpuQueue = cpuQueue;
        this.statistics = statistics;
    }

    public void insertProcess(Process p, long clock) {
        cpuQueue.insert(p);
        p.enterCpuQueue(clock);
    }
    
    public Process startProcess(long clock) {
    	assert currentProcess == null;
    	statistics.idleCpuTime += clock - timeOfLastEvent;
    	timeOfLastEvent = clock;
    	if (!cpuQueue.isEmpty()) {
    		currentProcess = (Process)cpuQueue.removeNext();
    		currentProcess.startCpu(clock);
    	} else {
    		currentProcess = null;
    	}
    	gui.setCpuActive(currentProcess);
    	return currentProcess;
    }
    
    public Process stopProcess(long clock) {
    	long elapsed = clock - timeOfLastEvent;
    	if (currentProcess != null) {
    		statistics.busyCpuTime += elapsed;
    		currentProcess.endCpu(clock);
    	} else {
    		statistics.idleCpuTime += elapsed;
    	}
    	timeOfLastEvent = clock;
    	Process finished = currentProcess;
    	currentProcess = null;
    	gui.setCpuActive(null);
    	return finished;
    }
    
    public void timePassed(long timePassed) {
    	statistics.cpuQueueLengthTime += cpuQueue.getQueueLength()*timePassed;
		if (cpuQueue.getQueueLength() > statistics.cpuQueueLargestLength) {
			statistics.cpuQueueLargestLength = cpuQueue.getQueueLength(); 
		}
    }
}