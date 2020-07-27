/**
 * Class that define instant attributes and methods for Customer object.
 * @author Xintong Zhu
 * @version 0.1
 */

public class Customer {
	
	// instant attributes
	
	/**
	 * An integer variable for customer's arrival time.
	 */
	private int arrivalTime;
	/**
	 * An integer variable for customer's id.
	 */
	private int id;
	
	
	// constructors
	
	// default constructor
	public Customer() {};
	// overloaded constructor
	/**
	 * Set up the arrivalTime and id for customer.
	 * @param arrivalTime An integer of the customer's arrival time.
	 * @param id An integer of the customer's id.
	 */
	public Customer(int arrivalTime, int id) {
		this.arrivalTime = arrivalTime;
		this.id = id;
	}
	
	// getters and setters
	
	/**
	 * Getter of this customer's arrival time.
	 * @return An integer of the customer's arrival time.
	 */
	public int getArrivalTime() {
		return this.arrivalTime;
	} // getArrivalTime
	
	/**
	 * Getter of this customer's id.
	 * @return An integer of this customer's id.
	 */
	public int getId() {
		return this.id;
	} // getId
	
	/**
	 * Setter of this customer's arrival time.
	 * @param An integer of this customer's arrival time.
	 */
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	} // setArrivalTime

}
