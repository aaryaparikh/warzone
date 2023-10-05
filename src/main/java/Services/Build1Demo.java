package Services;

import Controller.GameEngine;
import Models.*;
import Utils.MapCommandHandler;

/**
 * Build1 Demo: 1. Initialize the map 2. Enter map edit phase 3. Enter startup
 * game phase 4. Main loop for game play
 * 
 * @author YURUI
 */
public class Build1Demo {

	/**
	 * Initialize the default game map
	 * 
	 * @param p_gameMap game map object
	 * 
	 */
	public static void defaultGameMap(GameMap p_gameMap) {
		p_gameMap.addContinent(1, 3);
		p_gameMap.addContinent(2, 5);

		p_gameMap.addCountry(1, 1);
		p_gameMap.addCountry(2, 1);
		p_gameMap.addCountry(7, 2);

		p_gameMap.addNeighbor(1, 2);
		p_gameMap.addNeighbor(2, 1);
		p_gameMap.addNeighbor(7, 1);
		p_gameMap.addNeighbor(1, 7);
	}

	/**
	 * Main function for build1 presentation
	 * 
	 * @param p_args parameter for main function
	 * 
	 */
	public static void main(String[] p_args) {
		// Initialize Map and Game Engine
		GameMap l_map = new GameMap();
		//defaultGameMap(l_map);
		GameEngine l_gameEngine = new GameEngine(l_map);
		MapCommandHandler l_commandHandler = new MapCommandHandler(l_gameEngine);
		l_gameEngine.getPhaseView().showGameInfo();

		// Initialize map edit phase
		l_gameEngine.setPhase("edit");
		l_gameEngine.getPhaseView().showNextPhaseInfo("edit");
		MapService l_mapService = new MapService(l_map);

		while (l_gameEngine.getPhase().equals("edit")) {
			// Enter map edit phase
			l_mapService.main(null);
			l_commandHandler.handlePlayerCommand("end", null);

			// Enter start up phase
			StartUpGameService StartupService = new StartUpGameService(l_gameEngine);
			StartupService.main(null);
			l_mapService.setD_map(l_gameEngine.getGameMap());
		}

		// Enter game play phase
		while (l_gameEngine.getPhase().equals("play")) {
			l_gameEngine.assignReinforcements();

			l_gameEngine.playerIssueOrdersInTurn();

			if (l_gameEngine.executeAllCommittedOrders().equals("gameOver"))
				break;
		}

		// End game play
	}
}
