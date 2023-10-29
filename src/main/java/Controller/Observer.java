package Controller;

/**
 * Interface class for the Observer
 */
public interface Observer {

	/**
	 * react to the notification
	 * 
	 * @param p_observableState: Object that is passed by the subject (observable).
	 */
	void update(Observable p_observableState);
}
