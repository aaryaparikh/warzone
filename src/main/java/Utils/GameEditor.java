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
 * EditMapPhase the map information
 *
 * @author Yurui
 */
public class GameEditor {

	private static GameEngine d_gameEngine;

	/**
	 * Constructor for map editor
	 *
	 * @param p_gameMap Current map in game engine
	 */
	public GameEditor(GameEngine p_gameEngine) {
	}

	/**
	 * Writes the map information to a file with the given filename.
	 *
	 * @param p_fileName The name of the file to write to.
	 */
	public static void saveGameToFile(GameEngine gameEngine, String filePath, String playerName) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			// Save GameEngine data
			saveGamePhase(gameEngine, writer);
			if (playerName != null)
				savePlayerTurn(playerName, writer);
			saveMap(gameEngine.getGameMap(), writer);
			savePlayers(gameEngine.getPlayers(), writer);
			saveOrderIssued(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void saveGamePhase(GameEngine gameEngine, BufferedWriter writer) throws IOException {
		// Save game phase data
		writer.write("Game Phase: " + gameEngine.getPhase().getClass().getCanonicalName() + "\n");
		writer.write("\n");
	}

	private static void savePlayerTurn(String playerName, BufferedWriter writer) throws IOException {
		// Save game phase data
		writer.write("Player turn: " + playerName + "\n");
		writer.write("\n");
	}

	private static void saveMap(GameMap map, BufferedWriter writer) throws IOException {
		// Save map data
		saveContinents(map.getContinents(), writer);
		saveCountries(map.getCountries(), writer);
		writer.write("\n");
	}

	private static void saveContinents(List<Continent> continents, BufferedWriter writer) throws IOException {
		// Save map data
		for (Continent continent : continents) {
			writer.write("Continent Id: " + continent.getContinentId() + "\n");
			writer.write("Continent Value: " + continent.getContinentValue() + "\n");
		}
		writer.write("\n");
	}

	private static void saveCountries(List<Country> countries, BufferedWriter writer) throws IOException {
		// Save player data
		for (Country country : countries) {
			writer.write("Country Id: " + country.getCountryId() + "\n");
			writer.write("Continent Id: " + country.getContinentId() + "\n");
			writer.write("Owner Name: " + country.getOwner().getName() + "\n");
			writer.write("Neighbors: " + country.getNeighborCountries() + "\n");
			writer.write("Armies: " + country.getArmies() + "\n");
		}
		writer.write("\n");
	}

	private static void savePlayers(List<Player> players, BufferedWriter writer) throws IOException {
		// Save player data
		for (Player l_player : players) {
			writer.write("Player Name: " + l_player.getName() + "\n");

			// save country id
			List<Country> l_countries = l_player.getD_countries();
			List<Integer> l_countryIds = new ArrayList<>();
			for (Country l_country : l_countries) {
				l_countryIds.add(l_country.getCountryId());
			}
			writer.write("Countries: " + l_countryIds + "\n");

			if (l_player.getD_strategy() == null)
				writer.write("Player Strategy: " + "Models.Strategy.Human" + "\n");
			else
				writer.write("Player Strategy: " + l_player.getD_strategy().getClass().getName() + "\n");
			writer.write("Cards: " + l_player.getCardsOwned() + "\n");
		}
		writer.write("\n");
	}

	private static void saveOrderIssued(BufferedWriter writer) throws IOException {
		writer.write("Orders:\n");
		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/orders.txt"))) {
			// Load GameEngine data
			String line;
			while ((line = reader.readLine()) != null && !line.isEmpty()) {
				writer.write(line + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String loadGameFromFile(String filePath) {
		GameEngine gameEngine = new GameEngine(null); // Assuming a default constructor for GameEngine

		d_gameEngine = gameEngine;

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			// Read game phase
			String l_phaseName = loadGamePhase(reader);

			// Resume Game
			if (l_phaseName.equals("PlaySetupPhase")) {
				gameEngine.setGameMap(loadMap(reader));

				gameEngine.setPlayers(loadPlayers(reader));
				
				// Update country owner to be real player
				for (Country l_country : gameEngine.getGameMap().getCountries()) {
					for (Player l_player : gameEngine.getPlayers())
						if (l_player.getName().equals(l_country.getOwner().getName()))
							l_country.setOwner(l_player);
				}
				
				gameEngine.resumeToSetupPhase();
			}

			if (l_phaseName.equals("IssueOrderPhase")) {
				String currentPlayer = loadPlayerTurn(reader);

				gameEngine.setGameMap(loadMap(reader));

				gameEngine.setPlayers(loadPlayers(reader));
				
				// Update country owner to be real player
				for (Country l_country : gameEngine.getGameMap().getCountries()) {
					for (Player l_player : gameEngine.getPlayers())
						if (l_player.getName().equals(l_country.getOwner().getName()))
							l_country.setOwner(l_player);
				}
				
				loadOrderIssued(reader, "src/main/resources/orders.txt");

				gameEngine.resumeToPlayPhase(currentPlayer);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "error";
	}

	private static String loadGamePhase(BufferedReader reader) throws IOException {
		// Load game phase data
		String line = reader.readLine();
		String[] classList = line.substring("Game Phase: ".length()).split("\\.");
		String phaseClassName = classList[classList.length - 1];

		return phaseClassName;
	}

	private static String loadPlayerTurn(BufferedReader reader) throws IOException {
		// Load player turn data
		String line;
		while ((line = reader.readLine()) != null && line.trim().isEmpty()) {
			// Skip empty lines
		}

		return line.substring("Player turn: ".length()).trim();
	}

	private static GameMap loadMap(BufferedReader reader) throws IOException {
		// Load map data
		GameMap map = new GameMap(); // Assuming a default constructor for GameMap
		loadContinents(map.getContinents(), reader);
		loadCountries(map.getCountries(), reader);

		map.setD_continentCount(map.getContinents().size());
		map.setD_countryCount(map.getCountries().size());
		reader.readLine(); // Skip the empty line
		return map;
	}

	private static void loadContinents(List<Continent> continents, BufferedReader reader) throws IOException {
		// Load continent data
		String line;
		while ((line = reader.readLine()) != null && line.isEmpty()) {
			// Skip empty lines
		}

		while ((line != null) && !line.isEmpty()) {
			HashMap<String, String> continentData = parseKeyValuePairs(line);
			int l_continentId = (Integer.parseInt(continentData.get("Continent Id")));

			line = reader.readLine();
			continentData = parseKeyValuePairs(line);
			int l_continentValue = (Integer.parseInt(continentData.get("Continent Value")));
			Continent continent = new Continent(l_continentId, l_continentValue);
			continents.add(continent);
			line = reader.readLine();
		}
	}

	private static void loadCountries(List<Country> countries, BufferedReader reader) throws IOException {
		// Load country data
		String line;
		while ((line = reader.readLine()) != null && line.isEmpty()) {
			// Skip empty lines
		}

		// Load Country data
		while (line != null && !line.isEmpty()) {
			// Load Country id
			HashMap<String, String> countryData = parseKeyValuePairs(line);
			int l_countryId = (Integer.parseInt(countryData.get("Country Id")));

			// Load Continent id
			line = reader.readLine();
			countryData = parseKeyValuePairs(line);
			int l_continentId = (Integer.parseInt(countryData.get("Continent Id")));

			Country country = new Country(l_countryId, l_continentId);

			// Load Owner
			line = reader.readLine();
			countryData = parseKeyValuePairs(line);
			country.setOwner(new Player(countryData.get("Owner Name"), d_gameEngine));

			// Load Neighbor
			line = reader.readLine();
			ArrayList<Integer> neighbors = parseList(line);
			country.setNeighborCountries(neighbors);

			// Load armies
			line = reader.readLine();
			countryData = parseKeyValuePairs(line);
			country.setArmies(Integer.parseInt(countryData.get("Armies")));

			countries.add(country);
			line = reader.readLine();
		}
	}

	private static ArrayList<Integer> parseList(String line) {
		ArrayList<Integer> l_elements = new ArrayList<>();

		// Find the substring within square brackets
		int startIndex = line.indexOf('[');
		int endIndex = line.indexOf(']');

		if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
			String l_listString = line.substring(startIndex + 1, endIndex);

			// Split the substring using commas as separators
			String[] l_elementTokens = l_listString.split(", ");

			// Convert each token to an integer and add it to the list
			for (String token : l_elementTokens) {
				l_elements.add(Integer.parseInt(token));
			}
		}

		return l_elements;
	}

	private static List<Player> loadPlayers(BufferedReader reader)
			throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Load player data
		List<Player> players = new ArrayList<>();
		String line;
		while ((line = reader.readLine()) != null && !line.isEmpty()) {
			// Load player name
			HashMap<String, String> l_playerData = parseKeyValuePairs(line);
			String l_playerName = l_playerData.get("Player Name");

			Player l_player = new Player(l_playerName, d_gameEngine);

			// Load country
			line = reader.readLine();
			ArrayList<Integer> l_countryList = parseList(line);
			for (Integer l_countryId : l_countryList) {
				for (Country l_country : d_gameEngine.getGameMap().getCountries())
					if (l_country.getCountryId() == l_countryId)
						l_player.addCountry(l_country);
			}

			// Load strategy
			line = reader.readLine();
			l_playerData = parseKeyValuePairs(line);
			String l_strategy = l_playerData.get("Player Strategy");
			if (l_strategy.equals("Models.Strategy.Human"))
				l_player.setD_strategy(null);
			else {
				Class<?> l_strategyClass = Class.forName(l_strategy);

				// Get the constructor of the strategy class
				Constructor<?> constructor = l_strategyClass.getDeclaredConstructor(Player.class, List.class,
						LogEntryBuffer.class);

				// Enable access to the constructor
				constructor.setAccessible(true);

				// Create an instance using the constructor

				l_player.setD_strategy((PlayerStrategy) constructor.newInstance(l_player,
						DeepCopyList.deepCopy(d_gameEngine.getGameMap().getCountries()),
						d_gameEngine.getD_logEntryBuffer()));
			}

			// Load Cards
			line = reader.readLine();
			HashMap<String, Integer> l_cardsOwned = parseCards(line);
			l_player.setCardsOwned(l_cardsOwned);

			players.add(l_player);
		}
		return players;
	}

	private static HashMap<String, Integer> parseCards(String line) {
		HashMap<String, Integer> cards = new HashMap<>();

		// Find the substring within curly brackets
		int startIndex = line.indexOf('{');
		int endIndex = line.indexOf('}');

		if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
			String cardsString = line.substring(startIndex + 1, endIndex);

			// Split the substring using commas as separators
			String[] cardTokens = cardsString.split(", ");

			// Iterate over each token and extract card name and count
			for (String token : cardTokens) {
				String[] parts = token.split("=");
				if (parts.length == 2) {
					String cardName = parts[0];
					int cardCount = Integer.parseInt(parts[1]);
					cards.put(cardName, cardCount);
				}
			}
		}

		return cards;
	}

	private static HashMap<String, String> parseKeyValuePairs(String line) {
		// Parse key-value pairs in the format "Key: Value"
		HashMap<String, String> keyValuePairs = new HashMap<>();
		String[] pairs = line.split(": ");
		for (int i = 0; i < pairs.length - 1; i += 2) {
			keyValuePairs.put(pairs[i], pairs[i + 1]);
		}
		return keyValuePairs;
	}
	
    private static void loadOrderIssued(BufferedReader reader, String filePath) throws IOException {
        // Truncate the file to make it empty
		try {
			FileWriter d_logFile = new FileWriter(("src/main/resources/orders.txt"), false);
			d_logFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            if (line.equals("Orders:"))
            	continue;
            // Write the loaded order to the same file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(line + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
