package Controller;

import Utils.MapCommandHandler;

/**
 * Represents an abstract game phase.
 */
public abstract class GamePhase {
	public GameEngine d_gameEngine;
	public MapCommandHandler d_commandHandler;

	/**
	 * Constructor for GamePhase.
	 *
	 * @param p_gameEngine     The game engine.
	 * @param p_commandHandler The command handler.
	 */
	public GamePhase(GameEngine p_gameEngine, MapCommandHandler p_commandHandler) {
		d_gameEngine = p_gameEngine;
		d_commandHandler = p_commandHandler;
	}
}
