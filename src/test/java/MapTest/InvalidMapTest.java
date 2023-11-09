package MapTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Models.Continent;
import Models.Country;
import Models.GameMap;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * JUnit 5 test class for checking wether the map is invalid or not.
 *
 * @author Virag
 */
public class InvalidMapTest {

	private File d_testMapFile;
	private String d_testfileName = "temp.txt";
	private List<Continent> d_continents;
	private List<Country> d_countries;

	/**
	 * Reading the map file from the system
	 */
	@BeforeEach
	public void readMap() {
		d_testMapFile = new File("src/main/resources/" + d_testfileName + ".txt");
	}

	/**
	 * Reading the contents of the file and creating a map and checking wether it is
	 * valid or not
	 */
	@Test
	public void isInvalidMap() {
		String l_text = "";
		@SuppressWarnings("resource")
		Scanner l_reader = new Scanner(System.in);
		try {
			l_reader = new Scanner(d_testMapFile);
		} catch (FileNotFoundException e) {

		}
		while (l_reader.hasNextLine()) {
			l_text += l_reader.nextLine();
			l_text += "\n";
		}

		GameMap l_map = new GameMap();

		// Read map information form the file
		String l_continents[] = l_text.split("\n\n")[0].split("\n");
		String l_countries[] = l_text.split("\n\n")[1].split("\n");
		String l_neigbors[] = l_text.split("\n\n")[2].split("\n");

		for (int l_i = 1; l_i < l_continents.length; l_i++) {
			int l_continentId = Integer.parseInt(l_continents[l_i].split(" ")[0]);
			int l_continentValue = Integer.parseInt(l_continents[l_i].split(" ")[1]);
			l_map.addContinent(l_continentId, l_continentValue);
		}

		for (int l_i = 1; l_i < l_countries.length; l_i++) {
			int l_countryId = Integer.parseInt(l_countries[l_i].split(" ")[0]);
			int l_continentId = Integer.parseInt(l_countries[l_i].split(" ")[1]);
			String l_neighborsOfCountry[] = l_neigbors[l_i].split(" ");
			l_map.addCountry(l_countryId, l_continentId);

			for (int l_j = 1; l_j < l_neighborsOfCountry.length; l_j++) {
				int l_neighbor = Integer.parseInt(l_neighborsOfCountry[l_j]);
				l_map.addNeighbor(l_countryId, l_neighbor);
			}
		}

		d_countries = l_map.getCountries();
		d_continents = l_map.getContinents();

		assertTrue(validateMap());

	}

	/**
	 * Method to check wether the map is valid or not
	 * 
	 * @return boolean Wether the map is valid or not
	 */
	public boolean validateMap() {
		HashMap<Integer, Integer> l_counter = new HashMap<>();

		// Step 0: Check for empty map
		if (d_countries.size() == 0 || d_continents.size() == 0) {
			System.out.println("Invalid Map: at least one country and one continent.");
			return false;
		}

		// Step 1: Check for duplicate continents and validate continent values.
		for (int l_i = 0; l_i < d_continents.size(); l_i++) {
			if (!l_counter.containsKey(d_continents.get(l_i).getContinentId())) {
				l_counter.put(d_continents.get(l_i).getContinentId(), 1);
			} else {
				System.out.println("Invalid Map: Duplicate Continents: " + d_continents.get(l_i).getContinentId());
				return false;
			}
			if (d_continents.get(l_i).getContinentValue() <= 0) {
				System.out
						.println("Invalid Continent Value for Continent ID: " + d_continents.get(l_i).getContinentId());
				return false;
			}
		}
		l_counter.clear();

		// Step 2: Check for duplicate countries, unreachable countries, and continent
		// assignments.
		for (int l_i = 0; l_i < d_countries.size(); l_i++) {

			// Step 3: Check for duplicate countries
			if (!l_counter.containsKey(d_countries.get(l_i).getCountryId())) {
				l_counter.put(d_countries.get(l_i).getContinentId(), 1);
			} else {
				System.out.println("Invalid Map: Duplicate Countries: " + d_countries.get(l_i).getCountryId());
				return false;
			}

			// Step 4: Check for unreachable countries (no neighboring countries assigned)
			if (d_countries.get(l_i).getNeighborCountries().isEmpty()) {
				System.out.println("Invalid Map: Unreachable Country: No Neighbouring countries assigned to "
						+ d_countries.get(l_i).getCountryId());
				return false;
			}

			// Step 5: Check if the country is assigned to a valid continent
			int l_check = 0;
			for (int l_j = 0; l_j < d_continents.size(); l_j++) {
				if (d_countries.get(l_i).getContinentId() == d_continents.get(l_j).getContinentId()) {
					l_check = 1;
					break;
				}
			}
			if (l_check == 0) {
				System.out.println("Invalid Map: Country Assigned to a Continent that does not exist");
				break;
			}

			// Step 6: Check if a country is its own neighbor
			for (Integer l_neighbors : d_countries.get(l_i).getNeighborCountries()) {
				if (l_neighbors == d_countries.get(l_i).getCountryId()) {
					System.out
							.println("Invalid Map: Country cannot be a neighbor of itself. Country ID: " + l_neighbors);
					return false;
				}

				// Step 7: Check for duplicate neighbors
				int l_neighborCounter = 0;
				for (Integer l_checker : d_countries.get(l_i).getNeighborCountries()) {
					if (l_checker == l_neighbors) {
						l_neighborCounter++;
					}
				}
				if (l_neighborCounter > 1) {
					System.out.println(
							"Invalid Map: Duplicate Neighbors for Country: " + d_countries.get(l_i).getCountryId());
					return false;
				}

				// Step 8: Check if neighboring country exists
				int l_checker = 0;
				for (int l_j = 0; l_j < d_countries.size(); l_j++) {
					if (d_countries.get(l_j).getCountryId() == l_neighbors) {
						l_checker = 1;
					}
				}
				if (l_checker == 0) {
					System.out.println("Invalid Map: Neighboring country does not exist: " + l_neighbors);
					return false;
				}
			}

			// Step 9: Check if continent exists
			int l_checker = 0;
			for (int l_j = 0; l_j < d_continents.size(); l_j++) {
				if (d_countries.get(l_i).getContinentId() == d_continents.get(l_j).getContinentId()) {
					l_checker = 1;
				}
			}
			if (l_checker == 0) {
				System.out.println(
						"Invalid Map: Country assigned to a continent that does not exist: " + d_countries.get(l_i));
				return false;
			}
		}
		l_counter.clear();

		return true;
	}
}
