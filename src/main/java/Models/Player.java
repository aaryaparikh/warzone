package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Controller.GameEngine;
import Models.Orders.Order;
import Utils.PlayerCommandHandler;

/**
 * Represents a player in the game.
 */
public class Player {
    private String d_name;
    private List<Country> d_countries;
    private List<Order> d_orders;
    private int d_reinforcementPool;
    private PlayerCommandHandler d_playerCommandHandler;

    /**
     * Creates a new player with the specified name.
     *
     * @param d_name The name of the player.
     */
    public Player(String p_name, GameEngine p_gameEngine) {
        this.d_name = p_name;
        this.d_countries = new ArrayList<Country>();
        this.d_orders = new ArrayList<Order>();
        this.d_reinforcementPool = 0;
        this.d_playerCommandHandler = new PlayerCommandHandler(p_gameEngine);
    }

    /**
     * Adds a country to the player's list of controlled countries.
     *
     * @param country The country to be added.
     */
    public void addCountry(Country p_country) {
        d_countries.add(p_country);
    }
    
    /**
     * Adds a country to the player's list of controlled countries.
     *
     * @param country The country to be added.
     */
    public void resetCountry() {
        d_countries = new ArrayList<Country>();
    }    
    
    /**
     * Adds a country to the player's list of controlled countries.
     *
     * @param country The country to be added.
     */
    public void addOrder(Order p_order) {
        d_orders.add(p_order);
    }   

    /**
     * Issues an order to the player.
     *
     * @param order The order to be issued.
     */
    public void issueOrder() {
        @SuppressWarnings("resource")
		Scanner l_scanner = new Scanner(System.in);
        String l_userInput, l_response = "";
        
        while (l_response != "nextPlayer") {
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
     * @param numReinforcements The number of reinforcements to assign.
     */
    public void assignReinforcements(int numReinforcements) {
        d_reinforcementPool += numReinforcements;
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
     * Decreases the reinforcement pool by a specified number of armies.
     *
     * @param decreaseArmiesNumber The number of armies to decrease the reinforcement pool by.
     * @return The updated reinforcement pool value.
     */
    public int decreaseReinforcementPool(int decreaseArmiesNumber) {
        return d_reinforcementPool -= decreaseArmiesNumber;
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
