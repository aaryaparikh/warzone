package DemoDriver;

import Controller.GameEngine;
import Models.*;
import Models.Strategy.AggressivePlayerStrategy;
import Models.Strategy.BenevolentPlayerStrategy;
import Utils.DeepCopyList;

/**
 * Build3 Demo: 1. Initialize the map 2. Enter map edit phase 3. Enter startup
 * game phase 4. Main loop for game play
 * 
 * @author YURUI
 */
@SuppressWarnings("unused")
public class Build3Demo {

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
		defaultGameMap(l_map);
		GameEngine l_gameEngine = new GameEngine(l_map);
		l_gameEngine.getPhaseView().showGameInfo();

		Player p1 = new Player("Aarya", l_gameEngine);
		Player p2 = new Player("Yurui", l_gameEngine);
        
		l_gameEngine.addPlayer(p1);
		l_gameEngine.addPlayer(p2);
		l_gameEngine.assignCountriesRandomly();
		
		p1.setD_strategy(new BenevolentPlayerStrategy(p1, DeepCopyList.deepCopy(l_map.getCountries()), l_gameEngine.getD_logEntryBuffer()));

		l_gameEngine.start();
		// EndGamePhase game play
	}
}