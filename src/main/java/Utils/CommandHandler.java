package Utils;

import Controller.GameEngine;
import Controller.LogEntryBuffer;

public abstract class CommandHandler {
	public GameEngine d_gameEngine;
	public LogEntryBuffer d_logEntryBuffer;

	public CommandHandler(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
		d_logEntryBuffer = p_gameEngine.getD_logEntryBuffer();
	}
}
