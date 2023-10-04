package Models;

import java.util.*;

import Utils.MapEditor;
import Views.MapView;

/**
 * Represents a map containing continents and countries.
 */
public class GameMap {
	private ArrayList<Continent> d_continents = new ArrayList<Continent>();
	private ArrayList<Country> d_countries = new ArrayList<Country>();
	private int d_continentCount = 0;
	private int d_countryCount = 0;
	public MapEditor d_mapEditor;
	private MapView d_mapView;

	/**
	 * Constructor for GameMap
	 */
	public GameMap() {
		d_mapEditor = new MapEditor(this);
		d_mapView = new MapView(this);
	}

	/**
	 * Reset GameMap
	 */
	public void mapReset() {
		d_continents = new ArrayList<Continent>();
		d_countries = new ArrayList<Country>();
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
	 * @param map view object
	 */
	public void setD_mapView(MapView d_mapView) {
		this.d_mapView = d_mapView;
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
	 * @param d_countries The list of countries to set.
	 */
	public void setCountries(ArrayList<Country> d_countries) {
		this.d_countries = d_countries;
	}

	/**
	 * Gets the list of countries on the map.
	 *
	 * @return The list of countries.
	 */
	public ArrayList<Continent> getContinents() {
		return d_continents;
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
	 * Removes a continent from the map by its identifier.
	 *
	 * @param p_continentId The identifier of the continent to remove.
	 */
	public void removeContinent(int p_continentId) {
		for (int l_i = 0; l_i < d_continentCount; l_i++) {
			if (d_continents.get(l_i).getContinentId() == p_continentId) {
				d_continents.remove(l_i);
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