package Views;

import Controller.GameEngine;

/**
 * Represents a view for displaying information about the phases in the game.
 */
public class PhaseView {
	@SuppressWarnings("unused")
	private GameEngine d_gameEngine;

	/**
	 * Creates a new PhaseView associated with the provided GameEngine.
	 *
	 * @param p_gameEngine The GameEngine to associate with this PhaseView.
	 */
	public PhaseView(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	/**
	 * Displays the initial game information when the game starts.
	 */
	public void showGameInfo() {
		System.out.println("<<Game Start>>");
	}

	/**
	 * Displays information about the next game phase based on the provided phase
	 * name.
	 *
	 * @param p_phase The name of the next game phase.
	 */
	public void showNextPhaseInfo(String p_phase) {
		switch (p_phase) {
		case "edit":
			System.out.println("\n<<Edit map phase>><end><showmap><savemap><editmap><validatemap>");
			break;
		case "start":
			System.out.println(
					"\n<<Game startup phase>><end><backtoedit><showmap><loadmap><gameplayer><assigncountries>");
			break;
		case "play":
			System.out.println("\n<<Game issue order phase>><end><deploy>");
			break;
		case "execute":
			System.out.println("\n<<Game execute order phase>>");
			break;
		case "end":
			System.out.println("\n<<Game end phase>><TBD>");
			break;
		default:
			break;
		}
	}
}
