package Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class
 *
 */
public class Observable {
	/**
	 * Observer instance
	 */
	private List<Observer> d_observers = new ArrayList<>();

	/**
	 * Attach an observer to the model.
	 *
	 * @param p_observer: observers that are to be notified.
	 */
	public void attach(Observer p_observer) {
		this.d_observers.add(p_observer);
	}

	/**
	 * Detach an observer from the model.
	 *
	 * @param p_observer: observers to be removed.
	 */
	public void detach(Observer p_observer) {
		if (!d_observers.isEmpty()) {
			d_observers.remove(p_observer);
		}
	}

	/**
	 * Notify all observers attached to the model.
	 *
	 * @param p_observable: object that contains the information to be observed.
	 */
	public void notifyObservers(Observable p_observable) {
		for (Observer l_observer : d_observers) {
			l_observer.update(p_observable);
		}
	}
}