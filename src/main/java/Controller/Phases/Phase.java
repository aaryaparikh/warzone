package Controller.Phases;

import Controller.GameEngine;
import Models.Player;

/**
 * Represents an abstract game phase.
 * 
 * @author YURUI
 */
public abstract class Phase {
	/**
	 * get information from game engine in a phase
	 */
	public GameEngine d_gameEngine;

	/**
	 * Represents a phase in the game. All game phases extend this class and
	 * implement specific behaviors for each phase. This abstract class defines the
	 * methods common to all game phases.
	 *
	 * @param p_gameEngine The GameEngine instance that manages the game.
	 */
	public Phase(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	/**
	 * Display the map based on the provided commands.
	 *
	 * @param p_commands An array of commands for displaying the map.
	 * @return A message or response related to the map display.
	 */
	abstract public String showMap(String[] p_commands);

	/**
	 * Edit the state of a country on the map based on the provided commands.
	 *
	 * @param p_commands An array of commands for editing a country.
	 * @return A message or response related to editing the country.
	 */

	abstract public String editCountry(String[] p_commands);

	/**
	 * Edit the state of a continent on the map based on the provided commands.
	 *
	 * @param p_commands An array of commands for editing a continent.
	 * @return A message or response related to editing the continent.
	 */
	abstract public String editContinent(String[] p_commands);

	/**
	 * Edit the neighbor relationships of a country on the map based on the provided
	 * commands.
	 *
	 * @param p_commands An array of commands for editing neighbors.
	 * @return A message or response related to editing neighbor relationships.
	 */
	abstract public String editNeighbor(String[] p_commands);

	/**
	 * Save the current map state based on the provided commands.
	 *
	 * @param p_commands An array of commands for saving the map.
	 * @return A message or response related to saving the map.
	 */
	abstract public String saveMap(String[] p_commands);

	/**
	 * Edit the map state based on the provided commands.
	 *
	 * @param p_commands An array of commands for editing the map.
	 * @return A message or response related to editing the map state.
	 */
	abstract public String editMap(String[] p_commands);

	/**
	 * Validate the current map based on the provided commands.
	 *
	 * @param p_commands An array of commands for map validation.
	 * @return A message or response related to map validation.
	 */
	abstract public String validateMap(String[] p_commands);

	/**
	 * Load a map based on the provided commands.
	 *
	 * @param p_commands An array of commands for loading a map.
	 * @return A message or response related to map loading.
	 */
	abstract public String loadMap(String[] p_commands);

	/**
	 * Handle player setup based on the provided commands.
	 *
	 * @param p_commands An array of commands for setting up game players.
	 * @return A message or response related to player setup.
	 */
	abstract public String gamePlayer(String[] p_commands);

	/**
	 * Assign countries to players based on the provided commands.
	 *
	 * @param p_commands An array of commands for assigning countries.
	 * @return A message or response related to country assignment.
	 */
	abstract public String assignCountries(String[] p_commands);

	/**
	 * Deploy armies on the map during gameplay.
	 *
	 * @param p_commands      An array of commands for deploying armies.
	 * @param p_currentPlayer The current player performing the deployment.
	 * @return A message or response related to army deployment.
	 */
	abstract public String deploy(String[] p_commands, Player p_currentPlayer);

	/**
	 * Advance armies on the map during gameplay.
	 *
	 * @param p_commands      An array of commands for advancing armies.
	 * @param p_currentPlayer The current player performing the advancement.
	 * @return A message or response related to army advancement.
	 */
	abstract public String advance(String[] p_commands, Player p_currentPlayer);

	/**
	 * Launch a bomb operation during game play.
	 *
	 * @param p_commands      An array of commands for launching a bomb operation.
	 * @param p_currentPlayer The current player initiating the bomb operation.
	 * @return A message or response related to the bomb operation.
	 */
	abstract public String bomb(String[] p_commands, Player p_currentPlayer);

	/**
	 * Blockade a country during game play.
	 *
	 * @param p_commands      An array of commands for initiating a blockade.
	 * @param p_currentPlayer The current player initiating the blockade.
	 * @return A message or response related to the blockade operation.
	 */
	abstract public String blockade(String[] p_commands, Player p_currentPlayer);

	/**
	 * Conduct an airlift operation during game play.
	 *
	 * @param p_commands      An array of commands for conducting an airlift.
	 * @param p_currentPlayer The current player initiating the airlift.
	 * @return A message or response related to the airlift operation.
	 */
	abstract public String airlift(String[] p_commands, Player p_currentPlayer);

	/**
	 * Negotiate with other players during game play.
	 *
	 * @param p_commands      An array of commands for negotiation.
	 * @param p_currentPlayer The current player initiating negotiations.
	 * @return A message or response related to the negotiation.
	 */
	abstract public String negotiate(String[] p_commands, Player p_currentPlayer);

	/**
	 * Commit to an operation during game play.
	 *
	 * @param p_commands      An array of commands for committing to an operation.
	 * @param p_currentPlayer The current player committing to the operation.
	 * @return A message or response related to the commitment.
	 */
	abstract public String commit(String[] p_commands, Player p_currentPlayer);

	/**
	 * Move to the next phase based on the provided commands.
	 *
	 * @param p_commands An array of commands for moving to the next phase.
	 */
	abstract public void next(String[] p_commands);

	/**
	 * End the current phase based on the provided commands.
	 *
	 * @param p_commands      An array of commands for ending the phase.
	 * @param p_currentPlayer The current player ending the phase.
	 * @return A message or response related to ending the phase.
	 */

	abstract public String end(String[] p_commands, Player p_currentPlayer);

	/**
	 * Print an invalid command message for the current phase.
	 */
	public void printInvalidCommandMessage() {
		System.out.println("Invalid command in state " + this.getClass().getSimpleName());
	}

}
