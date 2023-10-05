package Controller;

import Utils.MapCommandHandler;

/**
 * Represents an abstract game phase.
 * 
 * @author YURUI
 */
public abstract class GamePhase {
	/**
	 * get information from game engine in a phase
	 */
	public GameEngine d_gameEngine;

	/**
	 * handle a command and return result
	 */
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
