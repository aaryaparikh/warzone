package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Controller.GameEngine;
import Models.Orders.Order;
import Utils.PlayerCommandHandler;

/**
 * Represents a player in the game.
 * 
 * @author Dev
 */
public class Player {
	private String d_name;
	private List<Country> d_countries;
	private List<Order> d_orders;
	private int d_reinforcementPool;
	private HashMap<String, Integer> d_cardsOwned;
	private PlayerCommandHandler d_playerCommandHandler;

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
		this.d_playerCommandHandler = new PlayerCommandHandler(p_gameEngine);
		
		// Initialize card owned list
		this.d_cardsOwned = new HashMap<String, Integer>();
		this.d_cardsOwned.put("bomb", 1);
		this.d_cardsOwned.put("blockade", 1);
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
	 * Adds a country to the player's list of controlled countries.
	 *
	 * @param p_order The country to be added.
	 */
	public void addOrder(Order p_order) {
		d_orders.add(p_order);
	}

	/**
	 * Issues an order to the player.
	 *
	 */
	public void issueOrder() {
		@SuppressWarnings("resource")
		Scanner l_scanner = new Scanner(System.in);
		String l_userInput, l_response = "";

		while (!l_response.equals("nextPlayer")) {
			l_userInput = l_scanner.nextLine();
			l_response = d_playerCommandHandler.handlePlayerCommand(l_userInput, this);
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
	 * Gets the current reinforcement pool for the player.
	 *
	 * @return The current reinforcement pool.
	 */
	public int getD_reinforcementPool() {
		return d_reinforcementPool;
	}

	
	public HashMap<String, Integer> getCardsOwned() {
		return d_cardsOwned;
	}
	
	public void setCardsOwned(HashMap<String, Integer> p_cardsOwned) {
		this.d_cardsOwned = p_cardsOwned;
	}
	
	public void addCardsOwned(String p_cardType) {
		this.d_cardsOwned.replace(p_cardType, this.d_cardsOwned.get(p_cardType)+1);
	}
	
	public void deleteCardsOwned(String p_cardType) {
		this.d_cardsOwned.replace(p_cardType, this.d_cardsOwned.get(p_cardType)-1);
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
}
