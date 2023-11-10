package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Constants.GameConstants;
import Controller.GameEngine;
import Models.Orders.Order;

/**
 * Represents a player in the game.
 *
 * @author Dev
 */
public class Player {

	/**
	 * Player name
	 */
	private String d_name;

	/**
	 * List of the Countries of the player
	 */
	private List<Country> d_countries;

	/**
	 * List of Orders assigned to the Player
	 */
	private List<Order> d_orders;

	/**
	 * Reinforcement pool of the player
	 */
	private int d_reinforcementPool;

	/**
	 * Cards hold by the player
	 */
	private HashMap<String, Integer> d_cardsOwned;

	/**
	 * List of Negotiated Player
	 */
	private List<Player> d_negotiatedPlayers;

	/**
	 * GameEngine instance
	 */
	private GameEngine d_gameEngine;

	/**
	 * check point for commit
	 */
	private Boolean d_ifSignified;

	/**
	 * Creates a new player with the specified name.
	 *
	 * @param p_name       The name of the player.
	 * @param p_gameEngine The game engine
	 */
	public Player(String p_name, GameEngine p_gameEngine) {
		this.d_name = p_name;
		this.d_countries = new ArrayList<>();
		this.d_orders = new ArrayList<>();
		this.d_reinforcementPool = 0;
		this.d_negotiatedPlayers = new ArrayList<>();
		this.d_gameEngine = p_gameEngine;

		// Initialize card owned list
		this.d_cardsOwned = new HashMap<String, Integer>();
		this.d_cardsOwned.put("bomb", 1);
		this.d_cardsOwned.put("blockade", 1);
		this.d_cardsOwned.put("airlift", 1);
		this.d_cardsOwned.put("negotiate", 1);
	}

	/**
	 * Adds a country to the player's list of controlled countries.
	 *
	 * @param p_country The country to be added.
	 */
	public void addCountry(Country p_country) {
		d_countries.add(p_country);
	}

	/**
	 * Resets a country information.
	 *
	 */
	public void resetCountry() {
		d_countries = new ArrayList<>();
	}

	/**
	 * Adds an order to the player's order list.
	 *
	 * @param p_order The order to be added.
	 */
	public void addOrder(Order p_order) {
		if (p_order.getOrderType() == "Deploy")
			for (int l_i = 0; l_i < d_orders.size(); l_i++)
				if (d_orders.get(l_i).getOrderType() != "Deploy") {
					d_orders.add(l_i, p_order);
					return;
				}
		d_orders.add(p_order);
	}

	/**
	 * Adds an order to the first position of player's order list.
	 *
	 * @param p_order The order to be added.
	 */
	public void addOrderAtFirstPosition(Order p_order) {
		d_orders.add(0, p_order);
	}

	/**
	 * Issues an order to the player.
	 *
	 */
	public void issueOrder() {
		Scanner l_scanner = d_gameEngine.d_sc;
		String l_userInput, l_response = "";

		while (!(l_response == "nextPlayer")) {
			l_userInput = l_scanner.nextLine();
			String l_commands[] = l_userInput.split(" ");

			l_response = d_gameEngine.executeCommand(l_commands, this);
			if (l_response == "gameEnd")
				break;
		}
	}

	/**
	 * Retrieves and removes the next order from the player's order list.
	 *
	 * @return The next order, or null if no orders are available.
	 */
	public Order nextOrder() {
		if (!d_orders.isEmpty()) {
			Order order = d_orders.get(0);
			d_orders.remove(0);
			return order;
		}
		return null;
	}

	/**
	 * Gets the list of countries controlled by the player.
	 *
	 * @return The list of countries.
	 */
	public List<Country> getD_countries() {
		return d_countries;
	}

	/**
	 * Assigns reinforcements to the player.
	 *
	 * @param p_numReinforcements The number of reinforcements to assign.
	 */
	public void assignReinforcements(int p_numReinforcements) {
		d_reinforcementPool += p_numReinforcements;
	}

	/**
	 * Gets the list of players negotiated with by this player.
	 *
	 * @return The list of negotiated players.
	 */
	public List<Player> getNegotiatedPlayers() {
		return d_negotiatedPlayers;
	}

	/**
	 * Sets the list of players negotiated with by this player.
	 *
	 * @param p_negotiatedPlayers The list of negotiated players to set.
	 */
	public void setNegotiatedPlayers(List<Player> p_negotiatedPlayers) {
		this.d_negotiatedPlayers = p_negotiatedPlayers;
	}

	/**
	 * Adds a player to the list of players negotiated with by this player.
	 *
	 * @param p_negotiatedPlayers The player to add to the list of negotiated
	 *                            players.
	 */
	public void addNegotiatedPlayers(Player p_negotiatedPlayers) {
		this.d_negotiatedPlayers.add(p_negotiatedPlayers);
	}

	/**
	 * Removes a player from the list of players negotiated with by this player.
	 *
	 * @param p_negotiatedPlayers The player to remove from the list of negotiated
	 *                            players.
	 */
	public void removeNegotiatedPlayers(Player p_negotiatedPlayers) {
		this.d_negotiatedPlayers.remove(p_negotiatedPlayers);
	}

	/**
	 * Resets the list of players negotiated with by this player.
	 */
	public void resetNegotiatedPlayers() {
		this.d_negotiatedPlayers.clear();
	}

	/**
	 * Gets the current reinforcement pool for the player.
	 *
	 * @return The current reinforcement pool.
	 */
	public int getD_reinforcementPool() {
		return d_reinforcementPool;
	}

	/**
	 * Gets the list of cards owned by this player along with their counts.
	 *
	 * @return A HashMap representing the cards owned by the player, where the keys
	 *         are card types and values are the counts.
	 */
	public HashMap<String, Integer> getCardsOwned() {
		return d_cardsOwned;
	}

	/**
	 * Sets the list of cards owned by this player.
	 *
	 * @param p_cardsOwned A HashMap representing the cards owned by the player.
	 */
	public void setCardsOwned(HashMap<String, Integer> p_cardsOwned) {
		this.d_cardsOwned = p_cardsOwned;
	}

	/**
	 * Adds a card to the list of cards owned by this player.
	 *
	 * @param p_cardType The type of card to add.
	 */
	public void addCardsOwned(String p_cardType) {
		String l_cardType = p_cardType;

		// Get a random card
		if (l_cardType.equals("random")) {
			List<String> l_cardList = new ArrayList<>();
			l_cardList.addAll(GameConstants.GAME_CARD_LIST);
			Collections.shuffle(l_cardList);
			l_cardType = l_cardList.get(0);
		}

		this.d_cardsOwned.replace(l_cardType, this.d_cardsOwned.get(l_cardType) + 1);
		d_gameEngine.getD_logEntryBuffer()
				.setString("Add one \"" + l_cardType + "\" card to Player \"" + this.getName() + "\"");
		System.out.println("Add one \"" + l_cardType + "\" card to Player \"" + this.getName() + "\"");
	}

	/**
	 * Deletes a card from the list of cards owned by this player.
	 *
	 * @param p_cardType The type of card to delete.
	 */
	public void deleteCardsOwned(String p_cardType) {
		this.d_cardsOwned.replace(p_cardType, this.d_cardsOwned.get(p_cardType) - 1);
	}

	/**
	 * Decreases the reinforcement pool by a specified number of armies.
	 *
	 * @param p_decreaseArmiesNumber The number of armies to decrease the
	 *                               reinforcement pool by.
	 * @return The updated reinforcement pool value.
	 */
	public int decreaseReinforcementPool(int p_decreaseArmiesNumber) {
		if (p_decreaseArmiesNumber > this.d_reinforcementPool) {
			return this.d_reinforcementPool;
		}
		return d_reinforcementPool -= p_decreaseArmiesNumber;
	}

	/**
	 * Gets the name of the player.
	 *
	 * @return The name of the player.
	 */
	public String getName() {
		return this.d_name;
	}

	/**
	 * Gets whether this player signified
	 *
	 * @return the d_ifSignified
	 */
	public Boolean getIfSignified() {
		return d_ifSignified;
	}

	/**
	 * Sets whether this player signified
	 *
	 * @param d_ifSignified the d_ifSignified to set
	 */
	public void setIfSignified(Boolean d_ifSignified) {
		this.d_ifSignified = d_ifSignified;
	}
}
