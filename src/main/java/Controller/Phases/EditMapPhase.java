package Controller.Phases;

import Controller.GameEngine;
import Controller.Phases.SubPhases.PlaySetupPhase;
import Models.Player;
import Utils.MapCommandHandler;

public class EditMapPhase extends Phase {
	private MapCommandHandler d_mapCommandHandler;

	public EditMapPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
		d_mapCommandHandler = new MapCommandHandler(p_gameEngine);
	}

	@Override
	public String showMap(String[] p_commands) {
		super.d_gameEngine.getGameMap().getD_mapView().showMap();
		return null;
	}

	@Override
	public String editCountry(String[] p_commands) {
		String l_response = d_mapCommandHandler.handleMapCommand(p_commands);
		return l_response;
	}

	@Override
	public String editContinent(String[] p_commands) {
		String l_response = d_mapCommandHandler.handleMapCommand(p_commands);
		return l_response;
	}

	@Override
	public String editNeighbor(String[] p_commands) {
		String l_response = d_mapCommandHandler.handleMapCommand(p_commands);
		return l_response;
	}

	@Override
	public String saveMap(String[] p_commands) {
		String l_response = d_mapCommandHandler.handleMapCommand(p_commands);
		return l_response;
	}

	@Override
	public String editMap(String[] p_commands) {
		String l_response = d_mapCommandHandler.handleMapCommand(p_commands);
		return l_response;
	}

	@Override
	public String validateMap(String[] p_commands) {
		String l_response = d_mapCommandHandler.handleMapCommand(p_commands);
		return l_response;
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
		if (d_mapCommandHandler.handleMapCommand(p_commands) == "Valid")
			super.d_gameEngine.setPhase(new PlaySetupPhase(super.d_gameEngine));
	}

	@Override
	public void end(String[] p_commands) {
		printInvalidCommandMessage();
	}

}
