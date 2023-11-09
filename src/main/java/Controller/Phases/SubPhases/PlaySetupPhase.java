package Controller.Phases.SubPhases;

import Controller.GameEngine;
import Controller.Phases.PlayGamePhase;
import Models.Country;
import Models.Player;
import Utils.GameCommandHandler;

/**
 * Represents the setup phase in the game. In this phase, players load a map,
 * configure game players, and assign countries. Once all players and countries
 * are assigned, the phase transitions to the issue order phase. This phase also
 * handles some invalid commands and messages.
 *
 */
public class PlaySetupPhase extends PlayGamePhase {

	private GameCommandHandler d_gameCommandHandler;

	/**
	 * Constructs an instance of the PlaySetupPhase.
	 *
	 * @param p_gameEngine The GameEngine instance that manages the game.
	 */
	public PlaySetupPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
		d_gameCommandHandler = new GameCommandHandler(p_gameEngine);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String loadMap(String[] p_commands) {
		String l_response = d_gameCommandHandler.handleGameCommand(p_commands);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String gamePlayer(String[] p_commands) {
		String l_response = d_gameCommandHandler.handleGameCommand(p_commands);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String assignCountries(String[] p_commands) {
		String l_response = d_gameCommandHandler.handleGameCommand(p_commands);
		return l_response;
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
		boolean ifAllPlayerAssigned = true;
		for (Player l_player : d_gameEngine.getPlayers())
			if (l_player.getD_countries().size() == 0) {
				System.out.println("Please assign all players before play.");
				ifAllPlayerAssigned = false;
				break;
			}

		boolean ifAllCountryAssigned = true;
		for (Country l_country : d_gameEngine.getGameMap().getCountries())
			if (l_country.getOwner() == null) {
				System.out.println("Please assign all countries before play.");
				ifAllCountryAssigned = false;
				break;
			}

		if (ifAllPlayerAssigned && ifAllCountryAssigned) {
			d_gameEngine.setPhase(new IssueOrderPhase(d_gameEngine));
		}
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
