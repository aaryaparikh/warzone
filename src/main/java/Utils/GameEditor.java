package Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Controller.GameEngine;
import Models.Continent;
import Models.Country;
import Models.GameMap;
import Models.Player;
import Models.Orders.Order;

/**
 * EditMapPhase the map information
 *
 * @author Aarya
 */
public class GameEditor {
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
            saveConqueringPlayers(gameEngine, writer);
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
            
            List<Country> l_countries = l_player.getD_countries();
            List<Integer> l_countryIds = new ArrayList<>();

            for (Country l_country : l_countries) {
                l_countryIds.add(l_country.getCountryId());
            }
            writer.write("Countries: " + l_countryIds + "\n");
            
			Order l_order = l_player.nextOrder();
			while (l_order != null) {
				saveOrder(l_order, writer);
				l_order = l_player.nextOrder();
			}
            
			if (l_player.getD_strategy() == null)
	            writer.write("Player Strategy: " + "Models.Strategy.Human" + "\n");
			else
				writer.write("Player Strategy: " + l_player.getD_strategy().getClass().getName() + "\n");
            writer.write("Cards: " + l_player.getCardsOwned() + "\n");
        }
        writer.write("\n");
    }
    
    private static void saveOrder(Order order, BufferedWriter writer) throws IOException {
        // Save conquering player data
    	writer.write("Order type: " + order.getOrderType() + "\n");
        writer.write("\n");
    }

    private static void saveConqueringPlayers(GameEngine gameEngine, BufferedWriter writer) throws IOException {
        // Save conquering player data
    	writer.write("Conquering players: " + gameEngine.getPlayerConquerInTurn() + "\n");
        writer.write("\n");
    }

	/**
	 * Loads a map from a file with the given filename.
	 *
	 * @param p_fileName The name of the file to load the map from.
	 * @return The loaded map.
	 */
    public static GameEngine loadGameFromFile(String filePath) {
        GameEngine gameEngine = new GameEngine(null);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Load GameEngine data
            loadPlayers(gameEngine, reader);
            loadMap();
            loadConqueringPlayers();
            loadGamePhase();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameEngine;
    }

    private static void loadPlayers(GameEngine gameEngine, BufferedReader reader) throws IOException {
        List<Player> players = new ArrayList<>();

        // Read player data
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            // Parse and create Player objects
            String playerName = line.substring("Player Name: ".length());
            Player player = new Player(playerName, gameEngine); // You need to modify the Player constructor accordingly
            // Add more player data parsing as needed

            players.add(player);
        }

        // Set players in GameEngine
        gameEngine.setPlayers(players);
    }

    private static void loadMap() throws IOException {
        // Load map data
        // Add code to parse and set map details
    }

    private static void loadConqueringPlayers() throws IOException {
        // Load conquering player data
        // Add code to parse and set conquering player details
    }

    private static void loadGamePhase() throws IOException {
        // Load game phase data
        // Add code to parse and set game phase details
    }
}
