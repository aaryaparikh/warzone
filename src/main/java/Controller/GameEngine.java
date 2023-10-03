package Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Constants.GameConstants;
import Models.Country;
import Models.Map;
import Models.Player;
import Utils.MapCommandHandler;
import Views.PhaseView;

/**
 * Represents the game engine core for the strategy game.
 */
public class GameEngine {
	private List<Player> d_players;
    private Map d_map;
    private MapCommandHandler d_commandHandler;
    private String d_phase;
    private PhaseView d_phaseView;

    /**
     * Constructor for GameEngine.
     *
     * @param map The game map.
     */
    public GameEngine(Map map) {
        this.d_map = map;
        this.d_players = new ArrayList<Player>();
        this.d_commandHandler = new MapCommandHandler(this);
        this.setPhaseView(new PhaseView(this));
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
    public Map getGameMap() {
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
            List<Country> ownedCountries = player.getD_countries();
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
        return GameConstants.DEFAULT_PLAYER_REINFORCEMENT;
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
            if (player.getD_countries().size() == d_map.getCountries().size()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get the current game phase.
     *
     * @return Phase.
     */
	public String getPhase() {
		return d_phase;
	}

    /**
     * Set the current game phase.
     *
     * @param Phase.
     */	
	public void setphase(String d_phase) {
		this.d_phase = d_phase;
	}

    /**
     * Get the phase view.
     *
     * @return PhaseView.
     */
	public PhaseView getPhaseView() {
		return d_phaseView;
	}
	
    /**
     * Set the phase view.
     *
     * @param PhaseView.
     */	
	public void setPhaseView(PhaseView d_phaseView) {
		this.d_phaseView = d_phaseView;
	}

    // Other game phases and methods
}
