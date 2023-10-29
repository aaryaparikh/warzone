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
		// different output for different phase
		switch (p_phase) {
		case "edit":
			System.out.println("\n<<EditMapPhase>><end><showmap><savemap><editmap><validatemap>");
			break;
		case "start":
			System.out
					.println("\n<<GameStartupPhase>><next><backtoedit><showmap><loadmap><gameplayer><assigncountries>");
			break;
		case "play":
			System.out.println("\n<<IssueOrderPhase>><next><deploy><advance><...>");
			break;
		case "execute":
			System.out.println("\n<<ExecuteOrderPhase>>");
			break;
		case "end":
			System.out.println("\n<<EndPhase>><TBD>");
			break;
		default:
			break;
		}
	}
}
