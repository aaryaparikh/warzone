package Controller.Phases.SubPhases;

import Controller.GameEngine;
import Models.Player;

/**
 * Represents the phase where players execute their orders during game play. In
 * this phase, various game play actions such as deploying armies, advancing,
 * bombing, blockading, airlifting, negotiating, and committing to orders can be
 * executed by players. This phase allows for the execution of orders, and
 * invalid commands result in "Invalid command" messages.
 *
 */
public class ExecuteOrderPhase extends PlayMainPhase {

	/**
	 * Constructs an instance of the ExecuteOrderPhase.
	 *
	 * @param p_gameEngine The GameEngine instance that manages the game.
	 */
	public ExecuteOrderPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
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
		printInvalidCommandMessage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String end(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveGame(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadGame(String[] p_commands, Player p_currentPlayer) {
		printInvalidCommandMessage();
	}
}
