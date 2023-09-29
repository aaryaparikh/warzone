import java.util.List;
import java.util.Map;

/**
 * Start-up Phase: <br>
 * 1. Load the map created in Map editor <br>
 * 2. Show map <br>
 * 3. Add players and assign countries randomly
 */
public class StartUpGame {
    public static void main(String[] args) {
            	
    	GameMap d_map = new GameMap();
        String d_filename = "your_map_file.txt";
        ((GameMap) d_map).loadMapFromFile(d_filename);
        
        GameEngine d_gameEngine = new GameEngine(d_map);
        
        ((GameMap) d_map).showMap();
                
        Player d_player1 = new Player("Player 1");
        Player d_player2 = new Player("Player 2");
        d_gameEngine.addPlayer(d_player1);
        d_gameEngine.addPlayer(d_player2);

        d_gameEngine.assignCountriesRandomly();

    }
}
