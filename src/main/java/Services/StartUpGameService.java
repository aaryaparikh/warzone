package Services;

import java.util.List;
import java.util.Map;

/**
 * Start-up Phase: <br>
 * 1. Load the map for game play <br>
 * 2. Show map <br>
 * 3. Add players and assign countries randomly
 */
public class StartUpGameService {
    public static void main(String[] args) {
        
    	//load map from file saved from mapEditor
    	GameMap d_map = new GameMap();
        String d_filename = "your_map_file.txt";
        ((GameMap) d_map).loadMapFromFile(d_filename);
        
        GameEngine d_gameEngine = new GameEngine(d_map);
        
        // add players
        Player d_player1 = new Player("Player 1");
        d_player1.setName("NewPlayer 1");
        Player d_player2 = new Player("Player 2");
        d_player2.setName("NewPlayer 2");
        Player d_player3 = new Player("Player 3");
        d_player3.setName("NewPlayer 3");
        d_gameEngine.addPlayer(d_player1);
        d_gameEngine.addPlayer(d_player2);
        d_gameEngine.addPlayer(d_player3);

        // assign countries randomly for each players
        d_gameEngine.assignCountriesRandomly();
        
        // assign reinforcements for each players
        d_gameEngine.assignReinforcements();;
        
        // show map
        ((GameMap) d_map).showMap();
        
    }
}
