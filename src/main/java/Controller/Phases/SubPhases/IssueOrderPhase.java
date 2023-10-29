package Controller.Phases.SubPhases;

import Controller.GameEngine;
import Models.Player;

public class IssueOrderPhase extends PlayMainPhase {

	public IssueOrderPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public String deploy(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	@Override
	public String advance(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	@Override
	public String bomb(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	@Override
	public String blockade(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	@Override
	public String airlift(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	@Override
	public String negotiate(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	@Override
	public String commit(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	@Override
	public void next(String[] p_commands) {
		// TODO Auto-generated method stub

	}

}
