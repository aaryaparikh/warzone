package Services;

import Controller.GameEngine;
import Models.*;
import Utils.MapCommandHandler;

public class Build1Demo {
    public static void main(String[] args) {
        
    	/*
    	 * Start-up Phase:
    	 * 1. Load the map created in Map editor
    	 * 2. Show map
    	 * 3. Add players and assign countries randomly
    	 */
    	
    	MapEditor map = new MapEditor();
        //String filename = "src/main/resources/risk.txt";
        
        map.addContinent(1, 3);
        map.addContinent(2, 5);
        
        map.addCountry(1, 1);
        map.addCountry(2, 1);
        map.addCountry(86, 2);
        
        map.addNeighbor(1, 2);
        map.addNeighbor(2, 1);
        map.addNeighbor(86, 1);
        map.addNeighbor(1, 86);
        	
        GameEngine gameEngine = new GameEngine(map);
        MapCommandHandler commandHandler = new MapCommandHandler(gameEngine);
        gameEngine.getPhaseView().showGameInfo();
        
        gameEngine.setphase("edit");
        gameEngine.getPhaseView().showNextPhaseInfo("edit");
        
        map.showMap();
        MapService mapService = new MapService(map);
        mapService.main(null);
        
        commandHandler.handlePlayerCommand("end", null);
        
        Player player1 = new Player("Player 1", gameEngine);
        Player player2 = new Player("Player 2", gameEngine);
        gameEngine.addPlayer(player1);
        gameEngine.addPlayer(player2);

        gameEngine.assignCountriesRandomly();

        commandHandler.handlePlayerCommand("end", null);
        
        /*
    	 * Main Game Loop:
    	 * 1. Assign reinforcements
    	 * 2. Issue orders
    	 * 3. Execute orders
    	 */

        while (true) {
            gameEngine.assignReinforcements();

            gameEngine.playerIssueOrdersInTurn();

            if (gameEngine.executeAllCommittedOrders() == "gameOver")
            	break;
            
            // Perform other game phases and checks
        }
    }
}
