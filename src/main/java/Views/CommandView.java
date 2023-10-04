package Views;

/**
 * Command View class
 */
public class CommandView {

	/**
	 * Creates a new PhaseView associated with the provided GameEngine.
	 */
	public CommandView() {
	}

	/**
	 * Displays information after execution of a command.
	 * 
	 * @param information string
	 */
	public void showGameInfo(String p_info) {
		System.out.println(p_info);
	}
}
