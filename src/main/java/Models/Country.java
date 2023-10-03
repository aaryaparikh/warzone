package Models;
import java.util.ArrayList;

/**
 * Represents a country in the game.
 */
public class Country {
    private int d_countryId;
    private int d_continentId;
    private int d_armies;
    private Player d_owner;

    private ArrayList<Integer> d_neighborCountries = new ArrayList<Integer>();

    /**
     * Creates a new country with the specified country and continent IDs.
     *
     * @param p_countryId   The unique identifier for the country.
     * @param p_continentId The identifier for the continent to which this country belongs.
     */
    public Country(int p_countryId, int p_continentId) {
        this.d_countryId = p_countryId;
        d_continentId = p_continentId;
    }

    /**
     * Gets the unique identifier of the country.
     *
     * @return The country's unique identifier.
     */
    public int getCountryId() {
        return d_countryId;
    }

    /**
     * Gets the identifier of the continent to which this country belongs.
     *
     * @return The continent identifier.
     */
    public int getContinentId() {
        return d_continentId;
    }

    /**
     * Adds a neighboring country to the list of neighbors.
     *
     * @param p_neighborCountryId The identifier of the neighboring country.
     */
    public void addNeighbor(int p_neighborCountryId) {
        d_neighborCountries.add(p_neighborCountryId);
    }

    /**
     * Removes a neighboring country from the list of neighbors.
     *
     * @param p_neighborCountryId The identifier of the neighboring country to be removed.
     */
    public void removeNeighbor(int p_neighborCountryId) {
        d_neighborCountries.remove(p_neighborCountryId);
    }

    /**
     * Gets a list of neighboring country IDs.
     *
     * @return The list of neighboring country IDs.
     */
    public ArrayList<Integer> getNeighborCountries() {
        return d_neighborCountries;
    }

    /**
     * Gets the number of armies in this country.
     *
     * @return The number of armies in the country.
     */
    public int getArmies() {
        return d_armies;
    }

    /**
     * Sets the number of armies in this country.
     *
     * @param d_armies The number of armies to set.
     */
    public void setArmies(int d_armies) {
        this.d_armies = d_armies;
    }

    /**
     * Gets the player who owns this country.
     *
     * @return The player who owns the country.
     */
    public Player getOwner() {
        return d_owner;
    }

    /**
     * Sets the player who owns this country.
     *
     * @param d_owner The player who owns the country.
     */
    public void setOwner(Player d_owner) {
        this.d_owner = d_owner;
    }

    /**
     * Adds armies to this country.
     *
     * @param numArmies The number of armies to add.
     */
    public void addArmies(int numArmies) {
        this.d_armies += numArmies;
    }

    /**
     * Subtracts armies from this country.
     *
     * @param numArmies The number of armies to subtract.
     */
    public void subtractArmies(int numArmies) {
        this.d_armies -= numArmies;
    }
}
