package Services;

import Controller.GameEngine;
import Models.*;
import Utils.MapCommandHandler;

/**
 * Build1 Demo: 1. Initialize the map 2. Enter map edit phase 3. Enter startup
 * game phase 4. Main loop for game play
 */
public class Build1Demo {

	/**
	 * Initialize the default game map
	 * 
	 * @param game map object
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
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		GameMap map = new GameMap();
		defaultGameMap(map);
		GameEngine gameEngine = new GameEngine(map);
		MapCommandHandler commandHandler = new MapCommandHandler(gameEngine);
		gameEngine.getPhaseView().showGameInfo();

		// Initialize map edit phase
		gameEngine.setphase("edit");
		gameEngine.getPhaseView().showNextPhaseInfo("edit");
		MapService mapService = new MapService(map);

		while (gameEngine.getPhase().equals("edit")) {
			// Enter map edit phase
			mapService.main(null);
			commandHandler.handlePlayerCommand("end", null);

			// Enter start up phase
			StartUpGameService StartupService = new StartUpGameService(gameEngine);
			StartupService.main(null);
			mapService.setD_map(gameEngine.getGameMap());
		}

		// Enter game play phase
		while (gameEngine.getPhase().equals("play")) {
			gameEngine.assignReinforcements();

			gameEngine.playerIssueOrdersInTurn();

			if (gameEngine.executeAllCommittedOrders() == "gameOver")
				break;
		}

		// End game play
	}
}
