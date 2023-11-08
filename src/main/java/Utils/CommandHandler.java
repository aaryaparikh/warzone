package Utils;

import Controller.GameEngine;
import Controller.LogEntryBuffer;

/**
 * An abstract class representing a command handler for game-related commands.
 * This class provides access to the game engine and log entry buffer for
 * command handling.
 *
 */
public abstract class CommandHandler {
	/**
	 * The game engine associated with this command handler.
	 */
	public GameEngine d_gameEngine;

	/**
	 * The log entry buffer for storing log entries related to command handling.
	 */
	public LogEntryBuffer d_logEntryBuffer;

	/**
	 * Creates a new command handler with a reference to the game engine and log
	 * entry buffer.
	 *
	 * @param p_gameEngine The game engine to associate with this command handler.
	 */
	public CommandHandler(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
		d_logEntryBuffer = p_gameEngine.getD_logEntryBuffer();
	}
}
