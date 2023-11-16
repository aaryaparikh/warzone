package Controller.Phases;

import Controller.GameEngine;
import Models.Player;

/**
 * Represents the end game phase in the game. This phase allows players to view
 * the game map, but no further editing or gameplay actions are allowed. All
 * editing and gameplay commands result in an "Invalid command" message during
 * this phase.
 *
 */
public class EndGamePhase extends Phase {

	/**
	 * Constructs an instance of the EndGamePhase.
	 *
	 * @param p_gameEngine The GameEngine instance that manages the game.
	 */
	public EndGamePhase(GameEngine p_gameEngine) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String loadMap(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String gamePlayer(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String assignCountries(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String deploy(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String advance(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String bomb(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String blockade(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String airlift(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String negotiate(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String commit(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void next(String[] p_commands) {
		printInvalidCommandMessage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String end(String[] p_commands, Player p_currentPlayer) {
		d_gameEngine.setPhase(null);
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveGame(String[] p_commands) {
		printInvalidCommandMessage();
	}
}
