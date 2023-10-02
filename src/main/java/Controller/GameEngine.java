package Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Models.Country;
import Models.GameMap;
import Models.Player;

/**
 * Represents the game engine core for the strategy game.
 */
public class GameEngine {
    private List<Player> d_players;
    private GameMap d_map;
    private CommandHandler d_commandHandler;

    /**
     * Constructor for GameEngine.
     *
     * @param map The game map.
     */
    public GameEngine(GameMap map) {
        this.d_map = map;
        this.d_players = new ArrayList<Player>();
        this.d_commandHandler = new CommandHandler(this);
    }

    /**
     * Get the list of players.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        return this.d_players;
    }

    /**
     * Get the game map.
     *
     * @return The game map.
     */
    public GameMap getGameMap() {
        return this.d_map;
    }

    /**
     * Add a player to the game.
     *
     * @param player The player to add.
     */
    public void addPlayer(Player player) {
        d_players.add(player);
    }

    /**
     * Assign countries to players randomly.
     */
    public void assignCountriesRandomly() {
        List<Country> unassignedCountries = new ArrayList<Country>(d_map.getCountries());

        Collections.shuffle(unassignedCountries);

        for (int i = 0; i < unassignedCountries.size(); i++) {
            Player player = d_players.get(i % d_players.size());
            Country country = unassignedCountries.get(i);
            player.addCountry(country);
            country.setOwner(player);
        }
    }

    /**
     * Assign reinforcements to players.
     */
    public void assignReinforcements() {
        for (Player player : d_players) {
            List<Country> ownedCountries = player.getCountries();
            int reinforcementArmies = calculateReinforcementArmies(player, ownedCountries);
            player.assignReinforcements(reinforcementArmies);
        }
    }

    /**
     * Calculate reinforcement armies for a player.
     *
     * @param player         The player for whom to calculate reinforcements.
     * @param ownedCountries The list of countries owned by the player.
     * @return The number of reinforcement armies.
     */
    private int calculateReinforcementArmies(Player player, List<Country> ownedCountries) {
        // Implement reinforcement calculation logic based on game rules
        // For simplicity, returning a constant value for demonstration
        return 5;
    }

    /**
     * Allow players to issue orders in their turn.
     */
    public void playerIssueOrdersInTurn() {
        IssueOrders l_issueOrderPhase = new IssueOrders(this, d_commandHandler); // Method parameters start with p_
        l_issueOrderPhase.issueOrders();
    }

    /**
     * Execute all committed orders and return the result.
     *
     * @return The result of executing orders.
     */
    public String executeAllCommittedOrders() {
        ExecuteOrders l_executeOrderPhase = new ExecuteOrders(this, d_commandHandler); // Method parameters start with p_
        return l_executeOrderPhase.executeOrders();
    }

    /**
     * Check if the game is over.
     *
     * @return True if the game is over, otherwise false.
     */
    public Boolean checkIfGameIsOver() {
        for (Player player : d_players) {
            if (player.getCountries().size() == d_map.getCountries().size()) {
                return true;
            }
        }
        return false;
    }

    // Other game phases and methods
}