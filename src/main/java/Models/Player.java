package Models;

import java.util.ArrayList;
import java.util.List;

import Models.Orders.Order;

/**
 * Represents a player in the game.
 */
public class Player {
    private String name;
    private List<Country> countries;
    private List<Order> orders;
    private int reinforcementPool;

    /**
     * Creates a new player with the specified name.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.countries = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.reinforcementPool = 0;
    }

    /**
     * Adds a country to the player's list of controlled countries.
     *
     * @param country The country to be added.
     */
    public void addCountry(Country country) {
        countries.add(country);
    }

    /**
     * Issues an order to the player.
     *
     * @param order The order to be issued.
     */
    public void issueOrder(Order order) {
        orders.add(order);
    }

    /**
     * Retrieves and removes the next order from the player's order list.
     *
     * @return The next order, or null if no orders are available.
     */
    public Order nextOrder() {
        if (!orders.isEmpty()) {
            Order order = orders.get(0);
            orders.remove(0);
            return order;
        }
        return null;
    }

    /**
     * Gets the list of countries controlled by the player.
     *
     * @return The list of countries.
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * Assigns reinforcements to the player.
     *
     * @param numReinforcements The number of reinforcements to assign.
     */
    public void assignReinforcements(int numReinforcements) {
        reinforcementPool += numReinforcements;
    }

    /**
     * Gets the current reinforcement pool for the player.
     *
     * @return The current reinforcement pool.
     */
    public int getReinforcementPool() {
        return reinforcementPool;
    }

    /**
     * Decreases the reinforcement pool by a specified number of armies.
     *
     * @param decreaseArmiesNumber The number of armies to decrease the reinforcement pool by.
     * @return The updated reinforcement pool value.
     */
    public int decreaseReinforcementPool(int decreaseArmiesNumber) {
        return reinforcementPool -= decreaseArmiesNumber;
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return this.name;
    }
}
