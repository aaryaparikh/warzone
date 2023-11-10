package Controller.Phases;

import Controller.GameEngine;
import Controller.Phases.SubPhases.PlaySetupPhase;
import Models.Player;
import Utils.MapCommandHandler;

/**
 * The `EditMapPhase` class represents a phase in a game where players can edit
 * the game map. This phase allows various map editing commands, such as adding
 * continents, countries, and neighbors. It is responsible for handling
 * map-related operations during the game setup.
 */
public class EditMapPhase extends Phase {
	/**
	 * MapCommandHandler instance
	 */
	private MapCommandHandler d_mapCommandHandler;

	/**
	 * Constructor for EditMapPhase.
	 *
	 * @param p_gameEngine The game engine.
	 */
	public EditMapPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
		d_mapCommandHandler = new MapCommandHandler(p_gameEngine);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String showMap(String[] p_commands) {
		super.d_gameEngine.getGameMap().getD_mapView().showMap();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String editCountry(String[] p_commands) {
		String l_response = d_mapCommandHandler.handleMapCommand(p_commands);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String editContinent(String[] p_commands) {
		String l_response = d_mapCommandHandler.handleMapCommand(p_commands);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String editNeighbor(String[] p_commands) {
		String l_response = d_mapCommandHandler.handleMapCommand(p_commands);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String saveMap(String[] p_commands) {
		String l_response = d_mapCommandHandler.handleMapCommand(p_commands);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String editMap(String[] p_commands) {
		String l_response = d_mapCommandHandler.handleMapCommand(p_commands);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String validateMap(String[] p_commands) {
		String l_response = d_mapCommandHandler.handleMapCommand(p_commands);
		return l_response;
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
		if (d_mapCommandHandler.handleMapCommand(p_commands) == "Valid")
			super.d_gameEngine.setPhase(new PlaySetupPhase(super.d_gameEngine));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String end(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

}
