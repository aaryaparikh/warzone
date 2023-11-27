package Models;

import java.util.ArrayList;

import Views.MapView;

/**
 * Represents a map containing continents and countries.
 *
 * @author Aarya
 */
public class GameMap {
	/**
	 * Map information
	 */
	public String d_mapInfo;

	/**
	 * List of Continents in the Game
	 */
	private ArrayList<Continent> d_continents = new ArrayList<>();

	/**
	 * List of COuntries in the Game
	 */
	private ArrayList<Country> d_countries = new ArrayList<>();

	/**
	 * Continent Count
	 */
	private int d_continentCount = 0;

	/**
	 * Country Count
	 */
	private int d_countryCount = 0;

	/**
	 * Map View for the map
	 */
	private MapView d_mapView;

	/**
	 * Constructor for GameMap
	 */
	public GameMap() {
		d_mapView = new MapView(this);
	}

	/**
	 * Gets the count of Continent
	 *
	 * @return d_continentCount The count of continent
	 */
	public int getD_continentCount() {
		return d_continentCount;
	}

	/**
	 * Sets the count of Continent
	 *
	 * @param p_continentCount The continent count
	 */
	public void setD_continentCount(int p_continentCount) {
		this.d_continentCount = p_continentCount;
	}

	/**
	 * Gets the country count
	 *
	 * @return countryCount
	 */
	public int getD_countryCount() {
		return d_countryCount;
	}

	/**
	 * Gets the country Count
	 *
	 * @param p_countryCount Country counter
	 */
	public void setD_countryCount(int p_countryCount) {
		this.d_countryCount = p_countryCount;
	}

	/**
	 * Reset GameMap
	 */
	public void mapReset() {
		d_continents = new ArrayList<>();
		d_countries = new ArrayList<>();
		d_continentCount = 0;
		d_countryCount = 0;
	}

	/**
	 * Gets the map view object
	 *
	 * @return The map view object
	 */
	public MapView getD_mapView() {
		return d_mapView;
	}

	/**
	 * Sets the map view object
	 *
	 * @param p_mapView view object
	 */
	public void setD_mapView(MapView p_mapView) {
		this.d_mapView = p_mapView;
	}

	/**
	 * Gets the list of countries on the map.
	 *
	 * @return The list of countries.
	 */
	public ArrayList<Country> getCountries() {
		return d_countries;
	}

	/**
	 * Sets the list of countries on the map.
	 *
	 * @param p_countries The list of countries to set.
	 */
	public void setCountries(ArrayList<Country> p_countries) {
		d_countries.clear();
		d_countries.addAll(p_countries);
	}

	/**
	 * Gets the list of continents on the map.
	 *
	 * @return The list of continents.
	 */
	public ArrayList<Continent> getContinents() {
		return d_continents;
	}

	/**
	 * Sets the list of continents on the map.
	 *
	 * @param p_continents list of continents
	 */
	public void setContinents(ArrayList<Continent> p_continents) {
		d_continents.clear();
		d_continents.addAll(p_continents);
	}

	/**
	 * Adds a new continent to the map.
	 *
	 * @param p_continentId    The identifier of the continent.
	 * @param p_continentValue The value of the continent.
	 */
	public void addContinent(int p_continentId, int p_continentValue) {
		d_continents.add(new Continent(p_continentId, p_continentValue));
		d_continentCount++;
		d_continents.sort((o1, o2) -> o1.getContinentId() - o2.getContinentId());
	}

	/**
	 * Adds a new continent to the map.
	 *
	 * @param p_continentId    The identifier of the continent.
	 * @param p_continentValue The value of the continent.
	 * @param p_continentName	The name of the continent
	 */
	public void addContinent(int p_continentId, int p_continentValue, String p_continentName) {
		d_continents.add(new Continent(p_continentId, p_continentValue, p_continentName));
		d_continentCount++;
		d_continents.sort((o1, o2) -> o1.getContinentId() - o2.getContinentId());
	}

	/**
	 * Removes a continent from the map by its identifier.
	 *
	 * @param p_continentId The identifier of the continent to remove.
	 */
	public void removeContinent(int p_continentId) {
		for (int l_i = 0; l_i < d_continentCount; l_i++) {
			if (d_continents.get(l_i).getContinentId() == p_continentId) {
				d_continents.remove(l_i);
				for (int l_j = 0; l_j < d_countryCount; l_j++) {
					if (d_countries.get(l_j).getContinentId() == p_continentId) {
						removeCountry(d_countries.get(l_j).getCountryId());
					}
				}
				d_continentCount--;
				break;
			}
		}
	}

	/**
	 * Adds a new country to the map.
	 *
	 * @param p_countryId   The identifier of the country.
	 * @param p_continentId The identifier of the continent to which the country
	 *                      belongs.
	 */
	public void addCountry(int p_countryId, int p_continentId) {
		d_countries.add(new Country(p_countryId, p_continentId));
		d_countries.sort((o1, o2) -> o1.getCountryId() - o2.getCountryId());
		d_countryCount++;
	}

	/**
	 * Adds a new country to the map.
	 *
	 * @param p_countryId   The identifier of the country.
	 * @param p_continentId The identifier of the continent to which the country
	 *                      belongs.
	 * @param p_countryName	The name of the country                  
	 */
	public void addCountry(int p_countryId, int p_continentId, String p_countryName) {
		d_countries.add(new Country(p_countryId, p_continentId, p_countryName));
		d_countries.sort((o1, o2) -> o1.getCountryId() - o2.getCountryId());
		d_countryCount++;
	}

	/**
	 * Adds a new country to the map.
	 *
	 * @param p_countryId   The identifier of the country.
	 * @param p_continentId The identifier of the continent to which the country
	 *                      belongs.
	 * @param p_countryName Country name string
	 * @param p_xAxis		x coordinate
	 * @param p_yAxis		y coordinate  
	 */
	public void addCountry(int p_countryId, int p_continentId, String p_countryName, String p_xAxis, String p_yAxis) {
		d_countries.add(new Country(p_countryId, p_continentId, p_countryName, p_xAxis, p_yAxis));
		d_countries.sort((o1, o2) -> o1.getCountryId() - o2.getCountryId());
		d_countryCount++;
	}

	/**
	 * Removes a country from the map by its identifier.
	 *
	 * @param p_countryId The identifier of the country to remove.
	 */
	public void removeCountry(int p_countryId) {
		for (int l_i = 0; l_i < d_countryCount; l_i++) {
			if (d_countries.get(l_i).getCountryId() == p_countryId) {
				d_countries.remove(l_i);
				d_countryCount--;
				break;
			} else {
				removeNeighbor(d_countries.get(l_i).getCountryId(), p_countryId);

			}
		}
	}

	/**
	 * Adds a neighboring country to an existing country on the map.
	 *
	 * @param p_countryId         The identifier of the country to which the
	 *                            neighbor is added.
	 * @param p_neighborCountryId The identifier of the neighboring country to add.
	 */
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		for (int l_i = 0; l_i < d_countryCount; l_i++) {
			if (d_countries.get(l_i).getCountryId() == p_countryId) {
				d_countries.get(l_i).addNeighbor(p_neighborCountryId);
			}
		}
	}

	/**
	 * Removes a neighboring country from an existing country on the map.
	 *
	 * @param p_countryId         The identifier of the country from which the
	 *                            neighbor is removed.
	 * @param p_neighborCountryId The identifier of the neighboring country to
	 *                            remove.
	 */
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		for (int l_i = 0; l_i < d_countryCount; l_i++) {
			if (d_countries.get(l_i).getCountryId() == p_countryId) {
				d_countries.get(l_i).removeNeighbor(p_neighborCountryId);
			}
		}
	}

}