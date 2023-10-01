package comp691.project.map;

import java.util.List;
import java.util.Map;

public class RiskGame {
    public static void main(String[] args) {
        
    	/*
    	 * Start-up Phase:
    	 * 1. Load the map created in Map editor
    	 * 2. Show map
    	 * 3. Add players and assign countries randomly
    	 */
    	
    	GameMap map = new GameMap();
        String filename = "your_map_file.txt";
        ((GameMap) map).loadMapFromFile(filename);
        
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

            for (Player player : gameEngine.getPlayers()) {
                List<Country> ownedCountries = player.getCountries();
                for (Country country : ownedCountries) {
                    int numReinforcements = player.getReinforcementPool();
                    if (numReinforcements > 0) {
                        DeployOrder deployOrder = new DeployOrder(country, 1);
                        player.issueOrder(deployOrder);
                    }
                }
            }

            for (Player player : gameEngine.getPlayers()) {
                while (true) {
                    Order order = player.nextOrder();
                    if (order == null) {
                        break;
                    }
                    order.execute();
                }
            }

            // End game condition (one player owns all countries)
            // Perform other game phases and checks
        }
    }
}
