package Controller.Phases;

import Controller.GameEngine;

public abstract class PlayGamePhase extends Phase {
	public PlayGamePhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public String showMap(String[] p_commands) {
		d_gameEngine.getGameMap().getD_mapView().showGameMap();
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
	public void end(String[] p_commands) {
		printInvalidCommandMessage();
	}
}
