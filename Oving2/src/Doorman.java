/**
 * This class implements the doorman's part of the
 * Barbershop thread synchronization example.
 */
public class Doorman implements Runnable {
	private CustomerQueue queue;
	private Gui gui;
	Thread thread;
	/**
	 * Creates a new doorman.
	 * @param queue		The customer queue.
	 * @param gui		A reference to the GUI interface.
	 */
	public Doorman(CustomerQueue queue, Gui gui) { 
		this.queue = queue;
		this.gui = gui;
	}

	/**
	 * Starts the doorman running as a separate thread.
	 */
	public void startThread() {
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Stops the doorman thread.
	 */
	public void stopThread() {
		thread.interrupt();
		thread.stop();
	}
	
	private void randomSleep(int time) {
		double var = time * 0.20;
		double min = time - var;
		double max = time + var;
		double r = min+(Math.random()*(max-min+1));
		try {
			Thread.sleep((int)r);
		} catch (InterruptedException e) {
			return;
		}
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				randomSleep(Globals.doormanSleep);
				queue.push(new Customer());
			}
		} catch (InterruptedException e) {
			return;
		}
	}

	// Add more methods as needed
}
