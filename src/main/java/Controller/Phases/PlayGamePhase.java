package Controller.Phases;

import Controller.GameEngine;

/**
 * Represents a phase during game play. This abstract class extends the Phase
 * class and provides a base for game play phases. In this phase, common methods
 * such as showing the game map and editing map elements are implemented to
 * handle the common behavior in game play phases.
 *
 * @param p_gameEngine The GameEngine instance that manages the game.
 */
public abstract class PlayGamePhase extends Phase {

	/**
	 * Constructs an instance of the PlayGamePhase.
	 *
	 * @param p_gameEngine The GameEngine instance that manages the game.
	 */
	public PlayGamePhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String showMap(String[] p_commands) {
		d_gameEngine.getGameMap().getD_mapView().showGameMap();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String editCountry(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String editContinent(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String editNeighbor(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String saveMap(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String editMap(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String validateMap(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}
}