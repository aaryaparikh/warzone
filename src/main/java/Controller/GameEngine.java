package Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Constants.GameConstants;
import Models.Continent;
import Models.Country;
import Models.GameMap;
import Models.Player;
import Utils.MapCommandHandler;
import Views.PhaseView;

/**
 * Represents the game engine core for the strategy game.
 * 
 * @author YURUI
 */
public class GameEngine {
	private List<Player> d_players;
	private GameMap d_map;
	private MapCommandHandler d_commandHandler;
	private String d_phase;
	private PhaseView d_phaseView;
	private List<Player> d_playerConquerInTurn;

	/**
	 * Constructor for GameEngine.
	 *
	 * @param p_map The game map.
	 */
	public GameEngine(GameMap p_map) {
		this.d_map = p_map;
		this.d_players = new ArrayList<>();
		this.d_commandHandler = new MapCommandHandler(this);
		this.setPhaseView(new PhaseView(this));
		this.d_playerConquerInTurn = new ArrayList<>();
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
	 * Set the game map.
	 *
	 * @param p_map The game map.
	 */
	public void setGameMap(GameMap p_map) {
		this.d_map = p_map;
	}

	/**
	 * Add a player to the game.
	 *
	 * @param p_player The player to add.
	 * 
	 * @return whether it successes to add a player
	 */
	public boolean addPlayer(Player p_player) {
		if (checkPlayerInList(p_player))
			return false;
		else {
			d_players.add(p_player);
			return true;
		}
	}

	/**
	 * Remove a player to the game.
	 *
	 * @param p_player The player to remove.
	 * 
	 * @return whether it successes to remove a player
	 */
	public boolean removePlayer(Player p_player) {
		if (!checkPlayerInList(p_player))
			return false;
		else {
			for (Player l_player : d_players)
				if (l_player.getName().equals(p_player.getName())) {

					// remove country owner
					for (Country l_country : d_map.getCountries())
						if (l_country.getOwner().equals(l_player))
							l_country.setOwner(null);
					d_players.remove(l_player);
					break;
				}
			return true;
		}
	}

	/**
	 * Check whether a player in current list.
	 *
	 * @param p_player The player to check.
	 * 
	 * @return whether the player is in the list
	 */
	public boolean checkPlayerInList(Player p_player) {
		for (Player l_player : d_players)
			if (l_player.getName().equals(p_player.getName()))
				return true;
		return false;
	}

	/**
	 * Assign countries to players randomly.
	 */
	public void assignCountriesRandomly() {
		List<Country> l_unassignedCountries = new ArrayList<>(d_map.getCountries());

		Collections.shuffle(l_unassignedCountries);

		// reset owner of countries before assign
		for (Player l_player : d_players) {
			l_player.resetCountry();
		}

		// assign country
		for (int l_i = 0; l_i < l_unassignedCountries.size(); l_i++) {
			Player l_player = d_players.get(l_i % d_players.size());
			Country l_country = l_unassignedCountries.get(l_i);
			l_player.addCountry(l_country);
			l_country.setOwner(l_player);
		}
	}

	/**
	 * Assign reinforcements to players.
	 */
	public void assignReinforcements() {
		for (Player l_player : d_players) {
			List<Country> l_ownedCountries = l_player.getD_countries();
			int l_reinforcementArmies = calculateReinforcementArmies(l_player, l_ownedCountries);
			l_player.assignReinforcements(l_reinforcementArmies);
		}
	}

	/**
	 * Calculate reinforcement armies for a player.
	 *
	 * @param player         The player for whom to calculate reinforcements.
	 * @param ownedCountries The list of countries owned by the player.
	 * @return The number of reinforcement armies.
	 */
	private int calculateReinforcementArmies(Player p_player, List<Country> p_ownedCountries) {
		// Implement reinforcement calculation logic based on game rules
		int l_assignedArmies = p_ownedCountries.size()/3;

		for (Continent l_continent : d_map.getContinents()) {
			List <Country> l_countryList = new ArrayList<Country>();
			for (Country l_country : d_map.getCountries()) {
				if (l_country.getContinentId() == l_continent.getContinentId())
					l_countryList.add(l_country);
			}
			if (p_ownedCountries.containsAll(l_countryList))
				l_assignedArmies += l_continent.getContinentValue();
		}
		
		if (l_assignedArmies > GameConstants.MINIMUN_PLAYER_REINFORCEMENT)
			return l_assignedArmies;
		else
			return GameConstants.MINIMUN_PLAYER_REINFORCEMENT;
	}

	/**
	 * Allow players to issue orders in their turn.
	 */
	public void playerIssueOrdersInTurn() {
		IssueOrders l_issueOrderPhase = new IssueOrders(this, d_commandHandler);
		l_issueOrderPhase.issueOrders();
	}

	/**
	 * Execute all committed orders and return the result.
	 *
	 * @return The result of executing orders.
	 */
	public String executeAllCommittedOrders() {
		ExecuteOrders l_executeOrderPhase = new ExecuteOrders(this, d_commandHandler);

		return l_executeOrderPhase.executeOrders();
	}

	/**
	 * Check if the game is over.
	 *
	 * @return True if the game is over, otherwise false.
	 */
	public Boolean checkIfGameIsOver() {
		for (Player l_player : d_players) {
			if (l_player.getD_countries().size() == d_map.getCountries().size()) {
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
	 * @param p_phase the phase to set
	 */
	public void setPhase(String p_phase) {
		this.d_phase = p_phase;
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
	 * @param p_phaseView the phase needs to view
	 */
	public void setPhaseView(PhaseView p_phaseView) {
		this.d_phaseView = p_phaseView;
	}

	/**
	 * Get the list of players who conquer at least one territory in this turn
	 * 
	 * @return the d_playerConquerInTurn
	 */
	public List<Player> getPlayerConquerInTurn() {
		return d_playerConquerInTurn;
	}

	/**
	 * Set the list of players who conquer at least one territory in this turn
	 * 
	 * @param p_playerConquerInTurn the d_playerConquerInTurn to set
	 */
	public void setPlayerConquerInTurn(Player p_player) {
		this.d_playerConquerInTurn.add(p_player);
	}

	/**
	 * Reset the list of players who conquer at least one territory in this turn
	 */
	public void resetPlayerConquerInTurn() {
		this.d_playerConquerInTurn.clear();
		;
	}

	// Other game phases and methods
}
