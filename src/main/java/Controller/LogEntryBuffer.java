package Controller;

/**
 * obtain outputs after executing commands
 */
public class LogEntryBuffer extends Observable {
	/**
	 * String log value
	 */
	private String d_value;

	/**
	 * Extract the string of output
	 *
	 * @return output string of the executed command
	 */
	public String getString() {
		return d_value;
	}

	/**
	 * Set the string of output to notify other observers of the change
	 *
	 * @param p_value the string of output that is used to notify the observers
	 */
	public void setString(String p_value) {
		d_value = p_value;
		notifyObservers(this);
	}
}
