/**
 * This class implements a queue of customers as a circular buffer.
 */
public class CustomerQueue {
	private Customer[] array;
	private int length;
	private int count = 0;
	private int start = 0; //The position of the first customer in the queue.
	private Gui gui;
	/**
	 * Creates a new customer queue.
	 * @param queueLength	The maximum length of the queue.
	 * @param gui			A reference to the GUI interface.
	 */
    public CustomerQueue(int queueLength, Gui gui) {
    	array = new Customer[queueLength];
    	this.length = queueLength;
    	this.gui = gui;
	}
    
    public synchronized void push(Customer customer) throws InterruptedException {
    	while (count >= length) {
    		gui.println("The dorman is waiting for a free spot");
    		wait();
    		gui.println("The dorman is notified of a free spot");
    	}
    	count++;
    	setCustomer((start+count-1)%length, new Customer());
    	notify();
    }
    
    public synchronized Customer pop() throws InterruptedException {
    	while (count == 0) {
    		wait(); 
    	}
    	Customer customer = array[start];
    	setCustomer(start, null);
    	start++;
    	start = start % length;
    	count--;
    	notify();
    	return customer;
    }
    
    private void setCustomer(int pos, Customer customer) {
    	array[pos] = customer;
    	if (customer != null) {
    		gui.fillLoungeChair(pos, customer);
    	} else {
    		gui.emptyLoungeChair(pos);
    	}
    }
    
    
}
