import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contains the common methods that will be used in the game start-up and main game play <br>
 * 1. add players <br>
 * 2. assign countries randomly to each players <br>
 * 3. calculate number of armies after each order execution
 */
public class GameEngine {
    private List<Player> d_players;
    private GameMap d_map;

    public GameEngine(GameMap d_map) {
        this.d_map = d_map;
        this.d_players = new ArrayList<>();
    }

    public void addPlayer(Player d_player) {
        d_players.add(d_player);
    }

    public void assignCountriesRandomly() {
        List<Country> unassignedCountries = new ArrayList<>(d_map.getCountries());
        Collections.shuffle(unassignedCountries);

        for (int i = 0; i < unassignedCountries.size(); i++) {
            Player d_player = d_players.get(i % d_players.size());
            Country country = unassignedCountries.get(i);
            d_player.addCountry(country);
            country.setOwner(d_player);
        }
    }

    public void assignReinforcements() {
        for (Player d_player : d_players) {
            List<Country> ownedCountries = d_player.getCountries();
            int reinforcementArmies = calculateReinforcementArmies(d_player, ownedCountries);
            d_player.assignReinforcements(reinforcementArmies);
        }
    }

    private int calculateReinforcementArmies(Player d_player, List<Country> ownedCountries) {
        // Implement reinforcement calculation logic based on game rules
        // For simplicity, returning a constant value for demonstration
    	
    	// Main Game Loop Team will update this
        return 5;
    }

    // Other game phases and methods
    
    // Main Game Loop Team will update this (if there are any)

    public List<Player> getPlayers() {
        return d_players;
    }
}
