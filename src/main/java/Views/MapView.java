package Views;

import java.util.List;

import Models.Continent;
import Models.Country;
import Models.GameMap;

/**
 * Map View class
 */
public class MapView {
	/**
	 * continent list
	 */
	private List<Continent> d_continents;

	/**
	 * country list
	 */
	private List<Country> d_countries;

	/**
	 * Constructor for map editor
	 *
	 * @param p_gameMap Game map
	 */
	public MapView(GameMap p_gameMap) {
		this.d_continents = p_gameMap.getContinents();
		this.d_countries = p_gameMap.getCountries();
	}

	/**
	 * Displays a summary of the map, including continents, countries, and their
	 * borders.
	 */
	public void showMap() {
		System.out.println("[Map]");
		System.out.println("\t[Continents]");
		System.out.println("\tId\tValue");

		for (Continent d_continent : d_continents) {
			System.out.println("\t" + d_continent.getContinentId() + "\t" + d_continent.getContinentValue());
		}

		System.out.println("\n\t[Countries]");
		System.out.println("\t" + "Id\tContinent");

		for (Country element : d_countries) {
			System.out.println("\t" + element.getCountryId() + "\t" + element.getContinentId());
		}

		System.out.println("\n\t[Borders]");
		System.out.println("\t" + "Country\tNeighbors");

		for (Country element : d_countries) {
			System.out.println("\t" + element.getCountryId() + "\t" + element.getNeighborCountries());
		}

		System.out.println("");

	}

	/**
	 * Displays a summary of the game map, including continents, countries, armies,
	 * and owners.
	 */
	public void showGameMap() {
		System.out.println("[Map]");
		System.out.println("\t[Continents]");
		System.out.println("\tId\tValue");

		for (Continent d_continent : d_continents) {
			System.out.println("\t" + d_continent.getContinentId() + "\t" + d_continent.getContinentValue());
		}

		System.out.println("\n\t[Countries]");
		System.out.println("\tId\tContinent\tArmies\tOwner");

		for (Country element : d_countries) {
			if (element.getOwner() == null)
				System.out.println("\t" + element.getCountryId() + "\t" + element.getContinentId() + "\t\t"
						+ element.getArmies() + "\t" + "Neutral");
			else
				System.out.println("\t" + element.getCountryId() + "\t" + element.getContinentId() + "\t\t"
						+ element.getArmies() + "\t" + element.getOwner().getName());
		}

		System.out.println("\n\t[Borders]");
		System.out.println("\tCountry\tNeighbors");

		for (Country element : d_countries) {
			System.out.println("\t" + element.getCountryId() + "\t" + element.getNeighborCountries());
		}

		System.out.println(" ");

	}
}
