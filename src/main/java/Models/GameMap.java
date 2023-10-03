package Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains the common methods that will be used in the game start-up and main game play <br>
 * 1. show map containing the current information <br>
 * 		- owner of each countries
 * 		- number of armies for each countries [after each players' turn]
 * 2. Parse the map from Map Editor class and prepare the map for the main game <br>
 */
class GameMap {
    private List<Country> countries;
    private List<Continent> continents;
    private Map<String, Country> countryNameMap;

    public GameMap() {
        this.countries = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.countryNameMap = new HashMap<>(); 
    }

    public void showMap() {
        System.out.println("Map Contents:");
        System.out.println(" ");
        for (Continent continent : continents) {
            System.out.println("  Continent: " + continent.getName() + " " + continent.getControlId());

	        for (Country country : countries) {
	        	if (country.getCountryContinent() == continent.getName()) {
	        	System.out.println("    Country:" + country.getName());
	            System.out.println("      - Continent: " + country.getCountryContinent());
 	            System.out.println("      - Owner: " + country.getOwner().getName());
 	            System.out.println("      - Number of Armies: " + country.getOwner().getReinforcementPool());
 	            System.out.print("      - Neighbors: ");
 	            	for (Country neighbor : country.getNeighbors()) {
 	            		System.out.print(" " + neighbor.getName() + " ");
 	            	}
	            	System.out.println(" ");
	             	System.out.println(" ");
 	        	}
        	}
        
         }
    }

    public void loadMapFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            countries.clear();
            continents.clear();
            countryNameMap.clear();

            String line;
            Continent currentContinent = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[Continents]")) {
                	while ((line = reader.readLine()) != null && !line.isEmpty()) {
                     	String[] values = line.split(" ");
                    	if (values.length == 2) {
                			int continentName = Integer.parseInt(values[0]);
                			int controlId = Integer.parseInt(values[1]);
                			currentContinent = new Continent(continentName, controlId);
                			continents.add(currentContinent);
                    	}
                    }
                } else if (line.startsWith("[Countries]")) {
                	while ((line = reader.readLine()) != null && !line.isEmpty()) {
                		String[] values = line.split(" ");
	                	if (values.length == 2) {
		                	int countryName = Integer.parseInt(values[0]);
		                    int continentName = Integer.parseInt(values[1]);
		                    Country country = new Country(countryName);
		                    countries.add(country);
		                	country.setCountryContinent(continentName);
		                	countryNameMap.put(countryName + "", country);
	                	}
                	}
                } else if (line.startsWith("[Borders]")) {
                	while ((line = reader.readLine()) != null && !line.isEmpty()) {
                		String[] values = line.split(" ");

                 		int countryName = Integer.parseInt(values[0]);
                 		Country country = countryNameMap.get(countryName + "");
 
                		for (int i = 0; i < values.length ; i++) {
                			int neighbors = Integer.parseInt(values[i]);
                 			Country neighbor = countryNameMap.get(neighbors + "");
                 			country.addNeighbor(neighbor);

                		}
                		
                	}
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading the map from file: " + e.getMessage());
        }
    }

    public List<Country> getCountries() {
        return countries;
    }
}
