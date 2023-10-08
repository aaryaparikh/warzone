package Utils;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Models.Continent;
import Models.Country;
import Models.GameMap;

/**
 * Edit the map information
 *
 * @author Aarya
 */
public class MapEditor {
	private List<Continent> d_continents;
	private List<Country> d_countries;
	private GameMap d_gameMap;

	/**
	 * Constructor for map editor
	 *
	 * @param p_gameMap Current map in game engine
	 */
	public MapEditor(GameMap p_gameMap) {
		this.d_continents = p_gameMap.getContinents();
		this.d_countries = p_gameMap.getCountries();
		this.d_gameMap = p_gameMap;
	}

	/**
	 * Writes the map information to a file with the given filename.
	 *
	 * @param p_fileName The name of the file to write to.
	 */
	public void write(String p_fileName) {
		if (!validateMap()) {
			System.out.println("Since invalid Map, it can't be saved.");
			return;
		}
		try {
			String l_text;
			l_text = "[Continents]\n";
			for (int l_i = 0; l_i < d_continents.size(); l_i++) {
				l_text = l_text + d_continents.get(l_i).getContinentId() + " "
						+ d_continents.get(l_i).getContinentValue() + "\n";
			}
			l_text = l_text + "\n[Countries]\n";
			for (int l_i = 0; l_i < d_countries.size(); l_i++) {
				l_text = l_text + d_countries.get(l_i).getCountryId() + " " + d_countries.get(l_i).getContinentId()
						+ "\n";
			}
			l_text = l_text + "\n[Borders]\n";
			for (int l_i = 0; l_i < d_countries.size(); l_i++) {
				l_text = l_text + d_countries.get(l_i).getCountryId();
				for (int l_j = 0; l_j < d_countries.get(l_i).getNeighborCountries().size(); l_j++) {
					l_text += " " + d_countries.get(l_i).getNeighborCountries().get(l_j);
				}
				l_text += "\n";
			}
			FileWriter l_writer = new FileWriter("src/main/resources/" + p_fileName + ".txt");
			l_writer.write(l_text);
			l_writer.close();
			System.out.println("File Saved Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads a map from a file with the given filename.
	 *
	 * @param p_fileName The name of the file to load the map from.
	 * @return The loaded map.
	 */
	public GameMap editMap(String p_fileName) {
		String l_text = "";
		GameMap l_map = new GameMap();

		try {
			File l_mapFile = new File("src/main/resources/" + p_fileName + ".txt");

			// Check whether the file exist in the path
			if (!l_mapFile.exists()) {
				System.out.println("Map file dosen't exist, a new map is created from scratch.");
				l_mapFile.createNewFile();
				l_text = "[Continents]\n";
				l_text = l_text + "\n[Countries]\n";
				l_text = l_text + "\n[Borders]\n";
				FileWriter l_writer = new FileWriter("src/main/resources/" + p_fileName + ".txt");
				l_writer.write(l_text);
				l_writer.close();
			} else {
				@SuppressWarnings("resource")
				Scanner l_reader = new Scanner(l_mapFile);
				while (l_reader.hasNextLine()) {
					l_text += l_reader.nextLine();
					l_text += "\n";
				}

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

				// validate the loaded map
				d_countries.clear();
				d_countries.addAll(l_map.getCountries());
				d_continents.clear();
				d_continents.addAll(l_map.getContinents());
				validateMap();
			}
		} catch (Exception e) {
			System.out.println("Map file is in unknown format, so it can't be loaded or edited.");
			return d_gameMap;
		}
		d_gameMap.setCountries(l_map.getCountries());
		d_gameMap.setContinents(l_map.getContinents());
		return l_map;
	}

	/**
	 * Loads a map from a file with the given filename.
	 *
	 * @param p_fileName The name of the file to load the map from.
	 * @return The loaded map.
	 */
	public GameMap loadMap(String p_fileName) {
		String l_text = "";

		try {
			File l_mapFile = new File("src/main/resources/" + p_fileName + ".txt");

			// Check whether the file exist in the path
			if (!l_mapFile.exists()) {
				System.out.println("Map file dosen't exist, load map fails.");
			} else {
				@SuppressWarnings("resource")
				Scanner l_reader = new Scanner(l_mapFile);
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

				// validate the loaded map
				d_countries = l_map.getCountries();
				d_continents = l_map.getContinents();
				if (validateMap() == false)
					return d_gameMap;
				else
					return l_map;
			}
		} catch (Exception e) {
			System.out.println("Map file is in unknown format, so it can't be loaded or edited.");
			return d_gameMap;
		}
		return d_gameMap;
	}

	/**
	 * Validates the game map.
	 *
	 * @return true if the map is valid, false otherwise.
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
