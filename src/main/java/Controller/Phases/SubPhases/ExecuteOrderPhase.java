package Controller.Phases.SubPhases;

import Controller.GameEngine;
import Models.Player;

public class ExecuteOrderPhase extends PlayMainPhase {

	public ExecuteOrderPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public String deploy(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String advance(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String bomb(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String blockade(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();

		return null;
	}

	@Override
	public String airlift(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String negotiate(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String commit(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public void next(String[] p_commands) {
		printInvalidCommandMessage();
	}
	
	@Override
	public String end(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

}
