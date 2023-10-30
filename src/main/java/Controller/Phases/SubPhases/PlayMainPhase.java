package Controller.Phases.SubPhases;

import Controller.GameEngine;
import Controller.Phases.PlayGamePhase;
import Utils.PlayerCommandHandler;

public abstract class PlayMainPhase extends PlayGamePhase {
	private PlayerCommandHandler d_playerCommandHandler;

	/**
	 * @return the d_playerCommandHandler
	 */
	public PlayerCommandHandler getD_playerCommandHandler() {
		return d_playerCommandHandler;
	}

	/**
	 * @param d_playerCommandHandler the d_playerCommandHandler to set
	 */
	public void setD_playerCommandHandler(PlayerCommandHandler d_playerCommandHandler) {
		this.d_playerCommandHandler = d_playerCommandHandler;
	}

	public PlayMainPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
		d_playerCommandHandler = new PlayerCommandHandler(p_gameEngine);
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
	public void next(String[] p_commands) {
		printInvalidCommandMessage();
	}
}
