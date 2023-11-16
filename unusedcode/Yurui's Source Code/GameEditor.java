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
import Models.Orders.AdvanceOrder;
import Models.Orders.AirliftOrder;
import Models.Orders.BlockadeOrder;
import Models.Orders.BombOrder;
import Models.Orders.DeployOrder;
import Models.Orders.DiplomacyOrder;
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
    public static void saveGameToFile(GameEngine gameEngine, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Save GameEngine data
            saveMap(gameEngine.getGameMap(), writer);
            savePlayers(gameEngine.getPlayers(), writer);
            saveConqueringPlayers(gameEngine, writer);
            saveGamePhase(gameEngine, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        	writer.write("Ref: " + country + "\n");
        	writer.write("Country Id: " + country.getCountryId() + "\n");
    		writer.write("Continent Id: " + country.getContinentId() + "\n");
    		writer.write("Owner Name: " + country.getOwner().getName() + "\n");
    		writer.write("Arimies: " + country.getArmies() + "\n");
    		writer.write("Neighbors: " + country.getNeighborCountries() + "\n");
        }
        writer.write("\n");
    }
    
    private static void savePlayers(List<Player> players, BufferedWriter writer) throws IOException {
        // Save player data
        for (Player player : players) {
        	writer.write("Ref: " + player + "\n");
            writer.write("Player Name: " + player.getName() + "\n");
            writer.write("Countries: " + player.getD_countries() + "\n");
            writer.write("Player Name: " + player.getD_strategy() + "\n");
            writer.write("Cards: " + player.getCardsOwned() + "\n");
            writer.write("Negotiated Players: " + player.getNegotiatedPlayers() + "\n");
            writer.write("Reinforcement: " + player.getD_reinforcementPool() + "\n");
            writer.write("If Signified: " + player.getIfSignified() + "\n");
            saveOrders(player.getD_orders(), writer);
        }
        writer.write("\n");
    }
    
    private static void saveOrders(List<Order> orders, BufferedWriter writer) throws IOException {
        // Save player data
        for (Order order : orders) {
        	switch (order.getOrderType()) {
    		case "deploy":
    			writer.write("Order type: " + order.getOrderType() + "\n");
    			writer.write("Target Country: " + ((DeployOrder) order).getD_country().getCountryId() + "\n");
    			writer.write("Armies: " + ((DeployOrder) order).getD_armies() + "\n");
    			break;
    		case "advance":
    			writer.write("Order type: " + order.getOrderType() + "\n");    			
    			writer.write("Source Country: " + ((AdvanceOrder) order).getD_resourceCountry() + "\n");
    			writer.write("Target Country: " + ((AdvanceOrder) order).getD_targetCountry() + "\n");
    			writer.write("Armies: " + ((AdvanceOrder) order).getD_armies() + "\n");
    			break;
    		case "bomb":
    			writer.write("Order type: " + order.getOrderType() + "\n");
    			writer.write("Target Country: " + ((BombOrder) order).getD_targetCountry() + "\n");
    			break;
    		case "blockade":
    			writer.write("Order type: " + order.getOrderType() + "\n");
    			writer.write("Target Country: " + ((BlockadeOrder) order).getD_targetCountry() + "\n");
    			break;
    		case "airlift":
    			writer.write("Order type: " + order.getOrderType() + "\n");
    			writer.write("Source Country: " + ((AirliftOrder) order).getD_resourceCountry() + "\n");
    			writer.write("Target Country: " + ((AirliftOrder) order).getD_targetCountry() + "\n");
    			writer.write("Armies: " + ((AirliftOrder) order).getD_armies() + "\n");
    			break;
    		case "negotiate":
    			writer.write("Order type: " + order.getOrderType() + "\n");
    			writer.write("Target Player: " + ((DiplomacyOrder) order).getD_targetPlayer() + "\n");
    			break;
        	}		
        }
        writer.write("\n");
    }

    private static void saveConqueringPlayers(GameEngine gameEngine, BufferedWriter writer) throws IOException {
        // Save conquering player data
    	writer.write("Conquering players: " + gameEngine.getPlayerConquerInTurn() + "\n");
        writer.write("\n");
    }

    private static void saveGamePhase(GameEngine gameEngine, BufferedWriter writer) throws IOException {
        // Save game phase data
    	writer.write("Game Phase: " + gameEngine.getPhase() + "\n");
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
