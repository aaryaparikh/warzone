package comp691.project.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GameEngine {
    private List<Player> players;
    private GameMap map;

    public GameEngine(GameMap map) {
        this.map = map;
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void assignCountriesRandomly() {
        List<Country> unassignedCountries = new ArrayList<>(map.getCountries());
        Collections.shuffle(unassignedCountries);

        for (int i = 0; i < unassignedCountries.size(); i++) {
            Player player = players.get(i % players.size());
            Country country = unassignedCountries.get(i);
            player.addCountry(country);
            country.setOwner(player);
        }
    }

    public void assignReinforcements() {
        for (Player player : players) {
            List<Country> ownedCountries = player.getCountries();
            int reinforcementArmies = calculateReinforcementArmies(player, ownedCountries);
            player.assignReinforcements(reinforcementArmies);
        }
    }

    private int calculateReinforcementArmies(Player player, List<Country> ownedCountries) {
        // Implement reinforcement calculation logic based on game rules
        // For simplicity, returning a constant value for demonstration
        return 5;
    }

    // Other game phases and methods

    public List<Player> getPlayers() {
        return players;
    }
}