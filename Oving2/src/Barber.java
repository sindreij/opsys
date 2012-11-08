/**
 * This class implements the barber's part of the
 * Barbershop thread synchronization example.
 */
public class Barber implements Runnable {
	private Gui gui;
	private int pos;
	private CustomerQueue queue;
	private Thread thread;
	private int customers = 0;
	private Cashier cashier;
	/**
	 * Creates a new barber.
	 * @param queue		The customer queue.
	 * @param gui		The GUI.
	 * @param pos		The position of this barber's chair
	 */
	public Barber(CustomerQueue queue, Gui gui, int pos, Cashier cashier) { 
		this.gui = gui;
		this.pos = pos;
		this.queue = queue;
		this.cashier = cashier;
	}

	/**
	 * Starts the barber running as a separate thread.
	 */
	public void startThread() {
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Stops the barber thread.
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

	public void run() {
		try {
			while (true) {
				gui.println(String.format("Barber #%d is waiting for a customer", pos));
				Customer customer = queue.pop();
				gui.fillBarberChair(pos, customer);
				gui.println(String.format("Barber #%d has received a customer", pos));
				randomSleep(Globals.barberWork);
				gui.emptyBarberChair(pos);
				customers++;
				cashier.getPayment();
				gui.println(String.format("Barber #%d is sleeping", pos));
				gui.barberIsSleeping(pos);
				randomSleep(Globals.barberSleep);
				gui.barberIsAwake(pos);
				gui.println(String.format("Barber #%d is awake", pos));
			}
		} catch (InterruptedException e) {
			return;
		}
	}
	
	public int getCustomers() {
		return customers;
	}
}

