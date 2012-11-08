
public class Cashier {
	private int payments = 0;
	public synchronized void getPayment() {
		try {
			Thread.sleep(500);
			payments++;
		} catch (InterruptedException e) {
			return;
		}
	}
	
	public int getPayments() {
		return this.payments;
	}
}
