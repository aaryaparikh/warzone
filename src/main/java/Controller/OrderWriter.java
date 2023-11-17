package Controller;

import java.io.FileWriter;
import java.io.IOException;

import Models.Player;

/**
 * write the output to log file
 */
public class OrderWriter implements Observer {

	/**
	 * FileWriter instance
	 */
	private FileWriter d_logFile;

	/**
	 * Constructor to attach the object of LogEntryBuffer
	 *
	 * @param p_logEntry object of LogEntryBuffer
	 */
	public OrderWriter() {

	}

	/**
	 * Display the current time and the state changes
	 *
	 * @param p_observableState current state
	 */
	@Override
	public void update(Observable p_observableState) {

		try {
			d_logFile = new FileWriter(("src/main/resources/orders.txt"), true);
			d_logFile.append(">> " + ((Player) p_observableState).getString() + "\n");
			d_logFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reset() {
		try {
			d_logFile = new FileWriter(("src/main/resources/orders.txt"), false);
			d_logFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}