package Controller.Phases.SubPhases;

import Controller.GameEngine;
import Controller.Phases.PlayGamePhase;
import Utils.PlayerCommandHandler;

/**
 * Represents a main game play phase that extends the base game play phase. In
 * this phase, players can perform actions such as loading a map, managing game
 * players, assigning countries, and proceeding to the next phase. This phase
 * includes a player command handler for handling player commands related to
 * game play actions.
 *
 * @param p_gameEngine The GameEngine instance that manages the game.
 */
public abstract class PlayMainPhase extends PlayGamePhase {

	private PlayerCommandHandler d_playerCommandHandler;

	/**
	 * Get the player command handler associated with this phase.
	 *
	 * @return the player command handler.
	 */
	public PlayerCommandHandler getD_playerCommandHandler() {
		return d_playerCommandHandler;
	}

	/**
	 * Set the player command handler for this phase.
	 *
	 * @param d_playerCommandHandler The player command handler to set.
	 */
	public void setD_playerCommandHandler(PlayerCommandHandler d_playerCommandHandler) {
		this.d_playerCommandHandler = d_playerCommandHandler;
	}

	/**
	 * Constructs an instance of the PlayMainPhase.
	 *
	 * @param p_gameEngine The GameEngine instance that manages the game.
	 */
	public PlayMainPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
		d_playerCommandHandler = new PlayerCommandHandler(p_gameEngine);
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
	public void next(String[] p_commands) {
		printInvalidCommandMessage();
	}
}
