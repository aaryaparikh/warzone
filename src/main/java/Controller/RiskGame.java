package Controller;

import Models.*;

public class RiskGame {
    public static void main(String[] args) {
        
    	/*
    	 * Start-up Phase:
    	 * 1. Load the map created in Map editor
    	 * 2. Show map
    	 * 3. Add players and assign countries randomly
    	 */
    	
    	GameMap map = new GameMap();
        //String filename = "src/main/resources/risk.txt";
        
        map.addContinent("1", 3);
        map.addContinent("2", 5);
        
        map.addCountry("01", "1");
        map.addCountry("02", "1");
        map.addCountry("86", "2");
        
        //map.addNeighbor("01", "02");
        //map.addNeighbor("02", "01");
        //map.addNeighbor("86", "01");
        //map.addNeighbor("01", "86");
        	
        GameEngine gameEngine = new GameEngine(map);
        
        ((GameMap) map).showMap();
        
        
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        gameEngine.addPlayer(player1);
        gameEngine.addPlayer(player2);

        gameEngine.assignCountriesRandomly();

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
