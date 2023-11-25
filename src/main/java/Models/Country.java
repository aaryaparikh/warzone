package Models;

import java.util.ArrayList;

/**
 * Represents a country in the game.
 *
 * @author Dev
 */
public class Country {
	/**
	 * Country Id
	 */
	private int d_countryId;

	/**
	 * Continent Id
	 */
	private int d_continentId;

	/**
	 * Armies held by Country
	 */
	private int d_armies;

	/**
	 * Owner of the Country
	 */
	private Player d_owner;

	/**
	 * Name of the Country
	 */
	public String d_countryName;

	/**
	 * Coutry's x postion
	 */
	public String d_xAxis;

	/**
	 * Coutry's y postion
	 */
	public String d_yAxis;

	/**
	 * Neighbor Countries of Country
	 */
	private ArrayList<Integer> d_neighborCountries = new ArrayList<>();

	/**
	 * Neighbor Country names
	 */
	public ArrayList<Integer> d_neighborNames = new ArrayList<>();

	/**
	 * Creates a new country with the specified country and continent IDs.
	 *
	 * @param p_countryId   The unique identifier for the country.
	 * @param p_continentId The identifier for the continent to which this country
	 *                      belongs.
	 */
	public Country(int p_countryId, int p_continentId) {
		this.d_countryId = p_countryId;
		d_continentId = p_continentId;
	}

	/**
	 * Creates a new country with the specified country and continent IDs.
	 *
	 * @param p_countryId   The unique identifier for the country.
	 * @param p_continentId The identifier for the continent to which this country
	 *                      belongs.
	 */
	public Country(int p_countryId, int p_continentId, String p_countryName) {
		this.d_countryId = p_countryId;
		this.d_continentId = p_continentId;
		this.d_countryName = p_countryName;
	}

	/**
	 * Creates a new country with the specified country and continent IDs.
	 *
	 * @param p_countryId   The unique identifier for the country.
	 * @param p_continentId The identifier for the continent to which this country
	 *                      belongs.
	 */
	public Country(int p_countryId, int p_continentId, String p_countryName, String p_xAxis, String p_yAxis) {
		this.d_countryId = p_countryId;
		this.d_continentId = p_continentId;
		this.d_countryName = p_countryName;
		this.d_xAxis = p_xAxis;
		this.d_yAxis = p_yAxis;
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
	 * @param p_neighborCountryId The identifier of the neighboring country to be
	 *                            removed.
	 */
	public void removeNeighbor(int p_neighborCountryId) {
		for (int l_i = 0; l_i < d_neighborCountries.size(); l_i++) {
			if (d_neighborCountries.get(l_i) == p_neighborCountryId) {
				d_neighborCountries.remove(l_i);
				break;
			}

		}
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
	 * Sets a list of neighboring country IDs.
	 *
	 * @param p_neighborCountries The list of neighboring country IDs.
	 */
	public void setNeighborCountries(ArrayList<Integer> p_neighborCountries) {
		d_neighborCountries = p_neighborCountries;
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
	 * @param p_armies The number of armies to set.
	 */
	public void setArmies(int p_armies) {
		this.d_armies = p_armies;
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
	 * @param p_owner The player who owns the country.
	 */
	public void setOwner(Player p_owner) {
		this.d_owner = p_owner;
	}

	/**
	 * Adds armies to this country.
	 *
	 * @param p_numArmies The number of armies to add.
	 */
	public void addArmies(int p_numArmies) {
		this.d_armies += p_numArmies;
	}

	/**
	 * Subtracts armies from this country.
	 *
	 * @param p_numArmies The number of armies to subtract.
	 */
	public void subtractArmies(int p_numArmies) {
		this.d_armies -= p_numArmies;
	}
}
