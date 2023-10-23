package Utils;

import Controller.GameEngine;
import Models.Player;

/**
 * Handles player commands.
 * 
 * @author YURUI
 */
public class MapCommandHandler {
	private GameEngine d_gameEngine;

	/**
	 * Constructor for CommandHandler.
	 *
	 * @param p_gameEngine The game engine.
	 */
	public MapCommandHandler(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	/**
	 * Handles player commands.
	 *
	 * @param p_command       The player's command.
	 * @param p_currentPlayer The current player.
	 * @return A response string indicating the result of the command.
	 */
	public String handlePlayerCommand(String p_command, Player p_currentPlayer) {
		String[] l_command = p_command.split(" ");
		switch (l_command[0]) {
		case "end":
			switch (d_gameEngine.getPhase()) {
			case "edit":
				d_gameEngine.setPhase("start");
				d_gameEngine.getPhaseView().showNextPhaseInfo("start");
				break;
			case "start":
				d_gameEngine.setPhase("play");
				d_gameEngine.getPhaseView().showNextPhaseInfo("play");
				break;
			case "play":
				d_gameEngine.setPhase("end");
				d_gameEngine.getPhaseView().showNextPhaseInfo("end");
				break;
			case "execute":
				d_gameEngine.setPhase("end");
				d_gameEngine.getPhaseView().showNextPhaseInfo("end");
				break;
			}
			break;
		default:
			System.out.println("Please Enter a Valid Command");
		}
		return "continue";
	}
}
