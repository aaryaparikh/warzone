package Controller.Phases.SubPhases;

import Controller.GameEngine;
import Models.Player;

/**
 * Represents the phase where players issue orders during game play. In this
 * phase, players can issue various types of orders, including deploying armies,
 * advancing, bombing, blockading, airlifting, negotiating, and committing to
 * orders. This phase allows players to issue orders, and the command handling
 * is delegated to the player command handler.
 *
 * @param p_gameEngine The GameEngine instance that manages the game.
 */
public class IssueOrderPhase extends PlayMainPhase {

	/**
	 * Constructs an instance of the IssueOrderPhase.
	 *
	 * @param p_gameEngine The GameEngine instance that manages the game.
	 */
	public IssueOrderPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	// Override methods from the PlayMainPhase class

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String deploy(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String advance(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String bomb(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String blockade(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String airlift(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String negotiate(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String commit(String[] p_commands, Player p_currentPlayer) {
		String l_response = super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
		return l_response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String end(String[] p_commands, Player p_currentPlayer) {
		return super.getD_playerCommandHandler().handlePlayerCommand(p_commands, p_currentPlayer);
	}
}
