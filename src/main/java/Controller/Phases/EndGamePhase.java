package Controller.Phases;

import Controller.GameEngine;
import Models.Player;

public class EndGamePhase extends Phase {

	public EndGamePhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public String showMap(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String editCountry(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String editContinent(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String editNeighbor(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String saveMap(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String editMap(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String validateMap(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String loadMap(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String gamePlayer(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
	}

	@Override
	public String assignCountries(String[] p_commands) {
		printInvalidCommandMessage();
		return null;
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
	public void end(String[] p_commands) {
		printInvalidCommandMessage();
	}

}
