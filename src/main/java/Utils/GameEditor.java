package Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Controller.GameEngine;
import Controller.LogEntryBuffer;
import Models.Continent;
import Models.Country;
import Models.GameMap;
import Models.Player;
import Models.Strategy.*;

/**
 * The GameEditor class is responsible for editing and saving map information in
 * the game. It provides methods to write the map information to a file,
 * including the game engine data, player turns, map details, player
 * information, and issued orders.
 * 
 * @author Yurui
 */
public class GameEditor {

	/**
	 * Game Engine
	 */
	private static GameEngine d_gameEngine;

	/**
	 * Constructor for game editor
	 *
	 * @param p_gameEngine Current game engine
	 */
	public GameEditor(GameEngine p_gameEngine) {
	}

	/**
	 * Writes the game information to a file with the given filename.
	 *
	 * @param p_filePath   The name of the file to write to.
	 * @param p_playerName The name of the player.
	 */
	public static void saveGameToFile(GameEngine p_gameEngine, String p_filePath, String p_playerName) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(p_filePath))) {
			// Save GameEngine data
			saveGamePhase(p_gameEngine, writer);
			if (p_playerName != null)
				savePlayerTurn(p_playerName, writer);
			saveMap(p_gameEngine.getGameMap(), writer);
			savePlayers(p_gameEngine.getPlayers(), writer);
			saveOrderIssued(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the current game phase data to the specified writer.
	 *
	 * @param p_gameEngine The game engine containing the current game phase.
	 * @param p_writer     The writer to save the data to.
	 * @throws IOException If an I/O error occurs.
	 */
	private static void saveGamePhase(GameEngine p_gameEngine, BufferedWriter p_writer) throws IOException {
		// Save game phase data
		p_writer.write("Game Phase: " + p_gameEngine.getPhase().getClass().getCanonicalName() + "\n");
		p_writer.write("\n");
	}

	/**
	 * Saves the player turn information to the specified writer.
	 *
	 * @param playerName The name of the player whose turn it is.
	 * @param p_writer   The writer to save the data to.
	 * @throws IOException If an I/O error occurs.
	 */
	private static void savePlayerTurn(String playerName, BufferedWriter p_writer) throws IOException {
		// Save game phase data
		p_writer.write("Player turn: " + playerName + "\n");
		p_writer.write("\n");
	}

	/**
	 * Saves the map data to the specified writer.
	 *
	 * @param p_map    The game map to save.
	 * @param p_writer The writer to save the data to.
	 * @throws IOException If an I/O error occurs.
	 */
	private static void saveMap(GameMap p_map, BufferedWriter p_writer) throws IOException {
		// Save map data
		saveContinents(p_map.getContinents(), p_writer);
		saveCountries(p_map.getCountries(), p_writer);
		p_writer.write("\n");
	}

	/**
	 * Saves the list of continents to the specified writer.
	 *
	 * @param p_continents The list of continents to save.
	 * @param p_writer     The writer to save the data to.
	 * @throws IOException If an I/O error occurs.
	 */
	private static void saveContinents(List<Continent> p_continents, BufferedWriter p_writer) throws IOException {
		// Save map data
		for (Continent l_continent : p_continents) {
			p_writer.write("Continent Id: " + l_continent.getContinentId() + "\n");
			p_writer.write("Continent Value: " + l_continent.getContinentValue() + "\n");
		}
		p_writer.write("\n");
	}

	/**
	 * Saves the list of countries to the specified writer.
	 *
	 * @param p_countries The list of countries to save.
	 * @param p_writer    The writer to save the data to.
	 * @throws IOException If an I/O error occurs.
	 */
	private static void saveCountries(List<Country> p_countries, BufferedWriter p_writer) throws IOException {
		// Save player data
		for (Country l_country : p_countries) {
			p_writer.write("Country Id: " + l_country.getCountryId() + "\n");
			p_writer.write("Continent Id: " + l_country.getContinentId() + "\n");
			p_writer.write("Owner Name: " + l_country.getOwner().getName() + "\n");
			p_writer.write("Neighbors: " + l_country.getNeighborCountries() + "\n");
			p_writer.write("Armies: " + l_country.getArmies() + "\n");
		}
		p_writer.write("\n");
	}

	/**
	 * Saves the list of players to the specified writer.
	 *
	 * @param p_players The list of players to save.
	 * @param p_writer  The writer to save the data to.
	 * @throws IOException If an I/O error occurs.
	 */
	private static void savePlayers(List<Player> p_players, BufferedWriter p_writer) throws IOException {
		// Save player data
		for (Player l_player : p_players) {
			p_writer.write("Player Name: " + l_player.getName() + "\n");

			// save country id
			List<Country> l_countries = l_player.getD_countries();
			List<Integer> l_countryIds = new ArrayList<>();
			for (Country l_country : l_countries) {
				l_countryIds.add(l_country.getCountryId());
			}
			p_writer.write("Countries: " + l_countryIds + "\n");

			if (l_player.getD_strategy() == null)
				p_writer.write("Player Strategy: " + "Models.Strategy.Human" + "\n");
			else
				p_writer.write("Player Strategy: " + l_player.getD_strategy().getClass().getName() + "\n");
			p_writer.write("Cards: " + l_player.getCardsOwned() + "\n");
		}
		p_writer.write("\n");
	}

	/**
	 * Saves the list of issued orders to the specified writer.
	 *
	 * @param p_writer The writer to save the data to.
	 * @throws IOException If an I/O error occurs.
	 */
	private static void saveOrderIssued(BufferedWriter p_writer) throws IOException {
		p_writer.write("Orders:\n");
		try (BufferedReader l_reader = new BufferedReader(new FileReader("src/main/resources/orders.txt"))) {
			// Load GameEngine data
			String l_line;
			while ((l_line = l_reader.readLine()) != null && !l_line.isEmpty()) {
				p_writer.write(l_line + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads a saved game from the specified file path and resumes the game state.
	 *
	 * @param p_filePath The path to the saved game file.
	 * @return A string indicating the success or failure of the operation.
	 */
	public static String loadGameFromFile(String p_filePath) {
		GameEngine l_gameEngine = new GameEngine(null); // Assuming a default constructor for GameEngine

		d_gameEngine = l_gameEngine;

		try (BufferedReader l_reader = new BufferedReader(new FileReader(p_filePath))) {
			// Read game phase
			String l_phaseName = loadGamePhase(l_reader);

			// Resume Game
			if (l_phaseName.equals("PlaySetupPhase")) {
				l_gameEngine.setGameMap(loadMap(l_reader));

				l_gameEngine.setPlayers(loadPlayers(l_reader));

				// Update country owner to be real player
				for (Country l_country : l_gameEngine.getGameMap().getCountries()) {
					for (Player l_player : l_gameEngine.getPlayers())
						if (l_player.getName().equals(l_country.getOwner().getName()))
							l_country.setOwner(l_player);
				}

				l_gameEngine.resumeToSetupPhase();
			}

			if (l_phaseName.equals("IssueOrderPhase")) {
				String l_currentPlayer = loadPlayerTurn(l_reader);

				l_gameEngine.setGameMap(loadMap(l_reader));

				l_gameEngine.setPlayers(loadPlayers(l_reader));

				// Update country owner to be real player
				for (Country l_country : l_gameEngine.getGameMap().getCountries()) {
					for (Player l_player : l_gameEngine.getPlayers())
						if (l_player.getName().equals(l_country.getOwner().getName()))
							l_country.setOwner(l_player);
				}

				loadOrderIssued(l_reader, "src/main/resources/orders.txt");

				l_gameEngine.resumeToPlayPhase(l_currentPlayer);
			}

		} catch (IOException | ClassNotFoundException | NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return "error";
	}

	/**
	 * Loads the game phase information from the specified reader.
	 *
	 * @param p_reader The reader from which to load the data.
	 * @return The name of the loaded game phase class.
	 * @throws IOException If an I/O error occurs.
	 */
	private static String loadGamePhase(BufferedReader p_reader) throws IOException {
		// Load game phase data
		String l_line = p_reader.readLine();
		String[] l_classList = l_line.substring("Game Phase: ".length()).split("\\.");
		String l_phaseClassName = l_classList[l_classList.length - 1];

		return l_phaseClassName;
	}

	/**
	 * Loads the player turn information from the specified reader.
	 *
	 * @param p_reader The reader from which to load the data.
	 * @return The name of the player whose turn it is.
	 * @throws IOException If an I/O error occurs.
	 */
	private static String loadPlayerTurn(BufferedReader p_reader) throws IOException {
		// Load player turn data
		String l_line;
		while ((l_line = p_reader.readLine()) != null && l_line.trim().isEmpty()) {
			// Skip empty lines
		}

		return l_line.substring("Player turn: ".length()).trim();
	}

	/**
	 * Loads the map information from the specified reader.
	 *
	 * @param p_reader The reader from which to load the data.
	 * @return The loaded game map.
	 * @throws IOException If an I/O error occurs.
	 */
	private static GameMap loadMap(BufferedReader p_reader) throws IOException {
		// Load map data
		GameMap l_map = new GameMap();

		// Assuming a default constructor for GameMap
		loadContinents(l_map.getContinents(), p_reader);
		loadCountries(l_map.getCountries(), p_reader);

		l_map.setD_continentCount(l_map.getContinents().size());
		l_map.setD_countryCount(l_map.getCountries().size());

		// Skip the empty line
		p_reader.readLine();
		return l_map;
	}

	/**
	 * Loads the list of continents from the specified reader.
	 *
	 * @param p_continents The list to which to add the loaded continents.
	 * @param p_reader     The reader from which to load the data.
	 * @throws IOException If an I/O error occurs.
	 */
	private static void loadContinents(List<Continent> p_continents, BufferedReader p_reader) throws IOException {
		// Load continent data
		String l_line;
		while ((l_line = p_reader.readLine()) != null && l_line.isEmpty()) {
			// Skip empty lines
		}

		while ((l_line != null) && !l_line.isEmpty()) {
			HashMap<String, String> l_continentData = parseKeyValuePairs(l_line);
			int l_continentId = (Integer.parseInt(l_continentData.get("Continent Id")));

			l_line = p_reader.readLine();
			l_continentData = parseKeyValuePairs(l_line);
			int l_continentValue = (Integer.parseInt(l_continentData.get("Continent Value")));
			Continent l_continent = new Continent(l_continentId, l_continentValue);
			p_continents.add(l_continent);
			l_line = p_reader.readLine();
		}
	}

	/**
	 * Loads the list of countries from the specified reader.
	 *
	 * @param p_countries The list to which to add the loaded countries.
	 * @param p_reader    The reader from which to load the data.
	 * @throws IOException If an I/O error occurs.
	 */
	private static void loadCountries(List<Country> p_countries, BufferedReader p_reader) throws IOException {
		// Load country data
		String l_line;
		while ((l_line = p_reader.readLine()) != null && l_line.isEmpty()) {
			// Skip empty lines
		}

		// Load Country data
		while (l_line != null && !l_line.isEmpty()) {
			// Load Country id
			HashMap<String, String> l_countryData = parseKeyValuePairs(l_line);
			int l_countryId = (Integer.parseInt(l_countryData.get("Country Id")));

			// Load Continent id
			l_line = p_reader.readLine();
			l_countryData = parseKeyValuePairs(l_line);
			int l_continentId = (Integer.parseInt(l_countryData.get("Continent Id")));

			Country l_country = new Country(l_countryId, l_continentId);

			// Load Owner
			l_line = p_reader.readLine();
			l_countryData = parseKeyValuePairs(l_line);
			l_country.setOwner(new Player(l_countryData.get("Owner Name"), d_gameEngine));

			// Load Neighbor
			l_line = p_reader.readLine();
			ArrayList<Integer> l_neighbors = parseList(l_line);
			l_country.setNeighborCountries(l_neighbors);

			// Load armies
			l_line = p_reader.readLine();
			l_countryData = parseKeyValuePairs(l_line);
			l_country.setArmies(Integer.parseInt(l_countryData.get("Armies")));

			p_countries.add(l_country);
			l_line = p_reader.readLine();
		}
	}

	/**
	 * Parses a comma-separated list of integers from the specified line.
	 *
	 * @param p_line The line containing the list of integers.
	 * @return An ArrayList of integers parsed from the line.
	 */
	private static ArrayList<Integer> parseList(String p_line) {
		ArrayList<Integer> l_elements = new ArrayList<>();

		// Find the substring within square brackets
		int l_startIndex = p_line.indexOf('[');
		int l_endIndex = p_line.indexOf(']');

		if (l_startIndex != -1 && l_endIndex != -1 && l_endIndex > l_startIndex) {
			String l_listString = p_line.substring(l_startIndex + 1, l_endIndex);

			// Split the substring using commas as separators
			String[] l_elementTokens = l_listString.split(", ");

			// Convert each token to an integer and add it to the list
			for (String l_token : l_elementTokens) {
				l_elements.add(Integer.parseInt(l_token));
			}
		}

		return l_elements;
	}

	/**
	 * Loads the list of players from the specified reader.
	 *
	 * @param p_reader The reader from which to load the data.
	 * @return The list of loaded players.
	 * @throws IOException               If an I/O error occurs.
	 * @throws ClassNotFoundException    If the player class cannot be found.
	 * @throws NoSuchMethodException     If the player class lacks the required
	 *                                   constructor.
	 * @throws SecurityException         If there are security violations while
	 *                                   reflecting on the player class.
	 * @throws InstantiationException    If an instantiation error occurs while
	 *                                   creating the player instance.
	 * @throws IllegalAccessException    If access to the player class or
	 *                                   constructor is denied.
	 * @throws IllegalArgumentException  If an illegal argument is passed to the
	 *                                   player constructor.
	 * @throws InvocationTargetException If an error occurs while invoking the
	 *                                   player constructor.
	 */
	private static List<Player> loadPlayers(BufferedReader p_reader)
			throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Load player data
		List<Player> l_players = new ArrayList<>();
		String l_line;
		while ((l_line = p_reader.readLine()) != null && !l_line.isEmpty()) {
			// Load player name
			HashMap<String, String> l_playerData = parseKeyValuePairs(l_line);
			String l_playerName = l_playerData.get("Player Name");

			Player l_player = new Player(l_playerName, d_gameEngine);

			// Load country
			l_line = p_reader.readLine();
			ArrayList<Integer> l_countryList = parseList(l_line);
			for (Integer l_countryId : l_countryList) {
				for (Country l_country : d_gameEngine.getGameMap().getCountries())
					if (l_country.getCountryId() == l_countryId)
						l_player.addCountry(l_country);
			}

			// Load strategy
			l_line = p_reader.readLine();
			l_playerData = parseKeyValuePairs(l_line);
			String l_strategy = l_playerData.get("Player Strategy");
			if (l_strategy.equals("Models.Strategy.Human"))
				l_player.setD_strategy(null);
			else {
				Class<?> l_strategyClass = Class.forName(l_strategy);

				// Get the constructor of the strategy class
				Constructor<?> l_constructor = l_strategyClass.getDeclaredConstructor(Player.class, List.class,
						LogEntryBuffer.class);

				// Enable access to the constructor
				l_constructor.setAccessible(true);

				// Create an instance using the constructor

				l_player.setD_strategy((PlayerStrategy) l_constructor.newInstance(l_player,
						DeepCopyList.deepCopy(d_gameEngine.getGameMap().getCountries()),
						d_gameEngine.getD_logEntryBuffer()));
			}

			// Load Cards
			l_line = p_reader.readLine();
			HashMap<String, Integer> l_cardsOwned = parseCards(l_line);
			l_player.setCardsOwned(l_cardsOwned);

			l_players.add(l_player);
		}
		return l_players;
	}

	/**
	 * Parses a HashMap of card names and their counts from the specified line.
	 *
	 * @param p_line The line containing the card information.
	 * @return A HashMap representing the cards owned by a player.
	 */
	private static HashMap<String, Integer> parseCards(String p_line) {
		HashMap<String, Integer> l_cards = new HashMap<>();

		// Find the substring within curly brackets
		int l_startIndex = p_line.indexOf('{');
		int l_endIndex = p_line.indexOf('}');

		if (l_startIndex != -1 && l_endIndex != -1 && l_endIndex > l_startIndex) {
			String l_cardsString = p_line.substring(l_startIndex + 1, l_endIndex);

			// Split the substring using commas as separators
			String[] l_cardTokens = l_cardsString.split(", ");

			// Iterate over each token and extract card name and count
			for (String l_token : l_cardTokens) {
				String[] l_parts = l_token.split("=");
				if (l_parts.length == 2) {
					String l_cardName = l_parts[0];
					int l_cardCount = Integer.parseInt(l_parts[1]);
					l_cards.put(l_cardName, l_cardCount);
				}
			}
		}

		return l_cards;
	}

	/**
	 * Parses key-value pairs from the specified line into a HashMap.
	 *
	 * @param p_line The line containing key-value pairs.
	 * @return A HashMap representing the parsed key-value pairs.
	 */
	private static HashMap<String, String> parseKeyValuePairs(String p_line) {
		// Parse key-value pairs in the format "Key: Value"
		HashMap<String, String> l_keyValuePairs = new HashMap<>();
		String[] l_pairs = p_line.split(": ");
		for (int l_i = 0; l_i < l_pairs.length - 1; l_i += 2) {
			l_keyValuePairs.put(l_pairs[l_i], l_pairs[l_i + 1]);
		}
		return l_keyValuePairs;
	}

	/**
	 * Loads the list of issued orders from the specified reader and writes them to
	 * a file.
	 *
	 * @param p_reader   The reader from which to load the data.
	 * @param p_filePath The path to the file where orders will be written.
	 * @throws IOException If an I/O error occurs.
	 */
	private static void loadOrderIssued(BufferedReader p_reader, String p_filePath) throws IOException {
		// Truncate the file to make it empty
		try {
			FileWriter l_logFile = new FileWriter(("src/main/resources/orders.txt"), false);
			l_logFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String l_line;
		while ((l_line = p_reader.readLine()) != null && !l_line.isEmpty()) {
			if (l_line.equals("Orders:"))
				continue;
			// Write the loaded order to the same file
			try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(p_filePath, true))) {
				l_writer.write(l_line + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
