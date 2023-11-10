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

		for (int l_i = 0; l_i < d_continents.size(); l_i++) {
			System.out.println(
					"\t" + d_continents.get(l_i).getContinentId() + "\t" + d_continents.get(l_i).getContinentValue());
		}

		System.out.println("\n\t[Countries]");
		System.out.println("\t" + "Id\tContinent");

		for (int l_i = 0; l_i < d_countries.size(); l_i++) {
			System.out
					.println("\t" + d_countries.get(l_i).getCountryId() + "\t" + d_countries.get(l_i).getContinentId());
		}

		System.out.println("\n\t[Borders]");
		System.out.println("\t" + "Country\tNeighbors");

		for (int l_i = 0; l_i < d_countries.size(); l_i++) {
			System.out.println(
					"\t" + d_countries.get(l_i).getCountryId() + "\t" + d_countries.get(l_i).getNeighborCountries());
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

		for (int l_i = 0; l_i < d_continents.size(); l_i++) {
			System.out.println(
					"\t" + d_continents.get(l_i).getContinentId() + "\t" + d_continents.get(l_i).getContinentValue());
		}

		System.out.println("\n\t[Countries]");
		System.out.println("\tId\tContinent\tArmies\tOwner");

		for (int l_i = 0; l_i < d_countries.size(); l_i++) {
			if (d_countries.get(l_i).getOwner() == null)
				System.out.println(
						"\t" + d_countries.get(l_i).getCountryId() + "\t" + d_countries.get(l_i).getContinentId()
								+ "\t\t" + d_countries.get(l_i).getArmies() + "\t" + "Neutral");
			else
				System.out.println("\t" + d_countries.get(l_i).getCountryId() + "\t"
						+ d_countries.get(l_i).getContinentId() + "\t\t" + d_countries.get(l_i).getArmies() + "\t"
						+ d_countries.get(l_i).getOwner().getName());
		}

		System.out.println("\n\t[Borders]");
		System.out.println("\tCountry\tNeighbors");

		for (int l_i = 0; l_i < d_countries.size(); l_i++) {
			System.out.println(
					"\t" + d_countries.get(l_i).getCountryId() + "\t" + d_countries.get(l_i).getNeighborCountries());
		}

		System.out.println(" ");

	}
}
