/**
 * Classs to implement queue data structure.
 * @author Xintong Zhu
 * @version 0.1
 */
public class Queue {
	
	/**
	 * Class to construct the Node object inside the queue.
	 * @author Xintong Zhu
	 * @version 0.1
	 */
	public class Node{
		
		// instant attributes
		
		/**
		 * A customer object for data storage.
		 */
		public Customer data;
		/**
		 * A node object to store the reference of next node.
		 */
		public Node next;
		
		/**
		 * A node object to store the reference of previous node.
		 */
		public Node previous;
		
		
		// constructors
		
		/**
		 * default constructors to set all instant attributes to null.
		 */
		public Node() {
			this.data = null;
			this.next = null;
			this.previous = null;
		}
		/**
		 * Overloaded constructors to set up the customer data.
		 * @param data A customer object of customer data.
		 */
		public Node(Customer data) {
			this.data = data;
			this.next = null;
			this.previous = null;
		}
		
	} // Node class
	
	
	
	// instant attributes
	
	/**
	 * A node object for the first element of the queue.
	 */
	public Node first;
	/**
	 * A node object for the last element of the queue.
	 */
	public Node last;
	/**
	 * An integer variable for the size of the queue.
	 */
	private int size;
	
	// constructors
	
	/**
	 * Default constructors.
	 */
	public Queue() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}
	
	
	
	// instant methods
	
	/**
	 * A method to add an element in the end.
	 * @param customer A customer object to be added.
	 */
	public void enqueue(Customer customer) {
		// construct a new node object
		Node addLast = new Node(customer);
		// set this new node to be the last element
		addLast.next = null;
		// linked the new node the the original last one
		addLast.previous = last;
		if(this.size == 0) {
			first = addLast;
			last = addLast;
		}
		// linked the original previous last element to this new node
		last.next = addLast;
		// update the last
		last = addLast;		
		// update the size
		this.size++;		
	} // enqueue
	
	/**
	 * A method to return and delete the first element.
	 * @return A Customer object of the deleted first element.
	 */
	public Customer dequeue() {
		// if the size if empty, return nothing
		if(this.size == 0) {
			System.out.println("There is no customer left. The queue is currently empty.");
			return null;
		}
		// else, remove and return
		else {
			// store the removed data
			Customer returnCustomer = first.data;
			// delete the first element
			first.next.previous = null;
			first = first.next;
			// update the size
			this.size--;
			return returnCustomer;
		}
	} // dequeue
	
	/**
	 * Getter of the size of this queue.
	 * @return An integer of the size.
	 */
	public int getSize() {
		return this.size;
	} // getSize
	
	/**
	 * Check if the current customer has customers behind him.
	 * @param currentNode A Node object of the checked node.
	 * @return A boolean value: true if he has, false otherwise.
	 */
	public boolean hasNext(Node currentNode) {
		if(currentNode.next == null) return false;
		else return true;
	} // hasNext
	
	/**
	 * Calculate the waiting time of a specific customer in queue.
	 * @param checkedId An integer of the id of the checked customer.
	 * @param processing An integer of processing time.
	 * @return An integer of the waiting time in seconds.
	 */
	public int waitTime(int checkedId, int processing) {
		// find the given customer in queue by id
		Node currentNode = first;
		Node checkedCust = null;
		// set a variable to store how many customers in front of him
		int position = 0;
		while(hasNext(first)) {
			if (currentNode.data.getId() == checkedId) {
				checkedCust = currentNode;
				break;
			}
			currentNode = currentNode.next;
			position++;
		} // while
		
		// determine the waiting time
		// if the checked one arrives after 5, no need to wait
		if (checkedCust.data.getArrivalTime() >= 61200) {
			return 0;
		}
		// if the checked one is the first customer
		if(position == 0) {
			// if he arrives earlier than 9
			if(checkedCust.data.getArrivalTime() < 9 * 3600) {
				return 9 * 3600 - checkedCust.data.getArrivalTime();
			}
			// if he arrives after
			else return 0;
		} // if first
		// if not the first one
		else {
			// if he arrives earlier than 9
			if(checkedCust.data.getArrivalTime() < 9 * 3600) {
				return 9 * 3600 - checkedCust.data.getArrivalTime() + position * processing;
			} // earlier
			// if he arrives after
			else {
				// if he arrives after previous one leaves, then he does not need to wait
				if(checkedCust.data.getArrivalTime() - checkedCust.previous.data.getArrivalTime() >= waitTime(checkedCust.previous.data.getId(), processing) + processing) {
//					System.out.println("yo");
					return 0;
				}
				// 
				else {					
					int waitIdeal = waitTime(checkedCust.previous.data.getId(), processing) + processing + checkedCust.previous.data.getArrivalTime() - checkedCust.data.getArrivalTime();
					// check after waiting such time, is it still before 5
					if (checkedCust.data.getArrivalTime() + waitIdeal < 61200) {
						// can be served
						return waitIdeal;
					} // can serve
					else {
						// cannot be served, and has to leave at 5
						return 17 * 3600 - checkedCust.data.getArrivalTime();
					} // cannot serve
				} // later			
			}			
		} 
	}// waitTime	
	
	/**
	 * Calculate how many customers are able to be served on that day.
	 * @param processing An integer of the processing time.
	 * @return An integer of the number of served customers.
	 */
	public int servedCust(int processing) {
		// set a variable to count the number of served customers
		int served = 0;
		// iterate through the first customer
		Node current = this.first;
		// use a while loop to go through the queue and check whether each customer can be served
		while (current.next != null) {
			// check if this customer can be served
			if (current.data.getArrivalTime() + waitTime(current.data.getId(), processing) < 61200) {
				// will be served
				served++;
			} // if < 61200
			// move to next customers
			current = current.next;
		} // while
		return served;
	} // servedCust
		
	/**
	 * Calculate the total idle time of the employee.
	 * @param processing An integer of processing time.
	 * @return An integer of total idle time in seconds.
	 */
	public int totalIdle (int processing) {
		// set a variable to calculate
		int total = 0;
		// use a while loop to iterate through and sum up
		Node current = this.first;
		while (current.next != null) {
			// if this customer needs to wait, then no idle time
			// if this customer does not need to wait, then there may be idle time
			// no need to consider customers who arrive after 5
			// if first customer arrives after 9:00, then have idle time
			if (current.data.getId() == first.data.getId()) {
				if (current.data.getArrivalTime() > 9 * 3600) {
					total += current.data.getArrivalTime() - 9 * 3600;
				} // if > 9
			} // if first
			else if (this.waitTime(current.data.getId(), processing) == 0 && current.data.getArrivalTime() < 61200){
				total += (current.data.getArrivalTime() - (current.previous.data.getArrivalTime() + this.waitTime(current.previous.data.getId(), processing) + processing));
			} // if no wait
			// move to next customer
			current = current.next;
		} // while
		return total;
	} // totalIdle
		
	/**
	 * Calculate the longest break time for employee.
	 * @param processing An integer of processing time.
	 * @return An integer of the longest break time in seconds.
	 */
	public int longestBreak (int processing) {
		// set a variable to store the longest break time
		int longest = 0;
		// use a while loop to iterate through and sum up
		Node current = this.first;
		while (current.next != null) {
			// set a variable to store the current break time
			int currentBreak = 0;
			// if this customer needs to wait, then no idle time
			// if this customer does not need to wait, then there may be idle time
			// no need to consider customers who arrive after 5
			// if first customer arrives after 9:00, then have idle time
			if (current.data.getId() == first.data.getId()) {
				if (current.data.getArrivalTime() > 9 * 3600) {
					currentBreak = current.data.getArrivalTime() - 9 * 3600;
				} // if > 9
			} // if first
			else if (this.waitTime(current.data.getId(), processing) == 0 && current.data.getArrivalTime() < 61200){
				currentBreak = (current.data.getArrivalTime() - (current.previous.data.getArrivalTime() + this.waitTime(current.previous.data.getId(), processing) + processing));
			} // if no wait
			// decide if this is the longest break time
			if (currentBreak > longest) longest = currentBreak;
			// move to next customer
			current = current.next;
		} // while
		return longest;
	} // longestBreak
	
	/**
	 * Calculate the max number of customers waiting in line.
	 * @param processing An integer of the processing time.
	 * @return An integer of the max number of customers waiting in line.
	 */
	public int maxNum (int processing) {
		// set a variable to store the number of the queue
		int num = 0;
		// set a variable to store the max number
		int maxNum = 0;
		// iterate through the first customer
		Node current = this.first;
		// use a while loop to go through the queue
		while (current.next != null) {
			// if the customer has to wait, then he is in line
			if (this.waitTime(current.data.getId(), processing) != 0) {
				num ++;
				// check if this is the max num
				if (num > maxNum) maxNum = num;
			} // if need to wait
			else {
				// empty the queue
				num = 0;
			}
			// move to next customer
			current = current.next;
		} // while
		return maxNum;
	} // maxNum

} // class
