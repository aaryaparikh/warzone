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

	public Phase(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	// general map behavior
	abstract public String showMap(String[] p_commands);

	// edit map state behavior
	abstract public String editCountry(String[] p_commands);

	abstract public String editContinent(String[] p_commands);

	abstract public String editNeighbor(String[] p_commands);

	abstract public String saveMap(String[] p_commands);

	abstract public String editMap(String[] p_commands);

	abstract public String validateMap(String[] p_commands);

	// game setup state behavior
	abstract public String loadMap(String[] p_commands);

	abstract public String gamePlayer(String[] p_commands);

	abstract public String assignCountries(String[] p_commands);

	// game play behavior
	abstract public String deploy(String[] p_commands, Player p_currentPlayer);

	abstract public String advance(String[] p_commands, Player p_currentPlayer);

	abstract public String bomb(String[] p_commands, Player p_currentPlayer);

	abstract public String blockade(String[] p_commands, Player p_currentPlayer);

	abstract public String airlift(String[] p_commands, Player p_currentPlayer);

	abstract public String negotiate(String[] p_commands, Player p_currentPlayer);

	abstract public String commit(String[] p_commands, Player p_currentPlayer);

	// go to next phase
	abstract public void next(String[] p_commands);

	// go to end phase
	abstract public String end(String[] p_commands, Player p_currentPlayer);

	// methods common to all states
	public void printInvalidCommandMessage() {
		System.out.println("Invalid command in state " + this.getClass().getSimpleName());
	}
}
