package mapEditor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameMap {
    private List<Country> countries;
    private List<Continent> continents;
    private Map<String, Country> countryNameMap;

    public GameMap() {
        this.countries = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.countryNameMap = new HashMap<>(); 
    }

    public void addContinent(String continentID, int continentValue) {
        continents.add(new Continent(continentID, continentValue));
    }

    public void removeContinent(String continentID) {
        continents.removeIf(continent -> continent.getContinentName().equals(continentID));
    }

    public void addCountry(String countryID, String continentID) {
        Country country = new Country(countryID);
        countries.add(country);
        countryNameMap.put(countryID, country);
    }

    public void removeCountry(String countryID) {
        countries.removeIf(country -> country.getName().equals(countryID));
        countryNameMap.remove(countryID);
    }

    public void addNeighbor(String countryID, String neighborCountryID) {
        Country country = getCountryByName(countryID);
        Country neighbor = getCountryByName(neighborCountryID);

        if (country != null && neighbor != null) {
            country.addNeighbor(neighbor);
            neighbor.addNeighbor(country);
        }
    }

    public void removeNeighbor(String countryID, String neighborCountryID) {
        Country country = getCountryByName(countryID);
        Country neighbor = getCountryByName(neighborCountryID);

        if (country != null && neighbor != null) {
            country.removeNeighbor(neighbor);
            neighbor.removeNeighbor(country);
        }
    }

    public void showMap() {
        System.out.println("Map Contents:");
        for (Continent continent : continents) {
            System.out.println("Continent: " + continent.getContinentName() + " (Value: " + continent.getControlValue() + ")");
            for (Country country : countries) {
                if (country.getContinent() == continent) {
                    System.out.println("- Country: " + country.getName());
                    System.out.print("  Neighbors: ");
                    for (Country neighbor : country.getNeighbors()) {
                        System.out.print(neighbor.getName() + " ");
                    }
                    System.out.println();
                }
            }
        }
    }

    public void saveMapToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Continent continent : continents) {
                writer.println("[Continent]");
                writer.println("ContinentName=" + continent.getContinentName());
                writer.println("ControlValue=" + continent.getControlValue());
                writer.println();
            }

            for (Country country : countries) {
                writer.println("[Territory]");
                writer.println("TerritoryName=" + country.getName());
                writer.println("ContinentName=" + country.getContinent().getContinentName());
                writer.print("AdjacentTerritories=");
                for (Country neighbor : country.getNeighbors()) {
                    writer.print(neighbor.getName() + ",");
                }
                writer.println();
                writer.println();
            }
        } catch (IOException e) {
            System.err.println("Error saving the map to file: " + e.getMessage());
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
                if (line.startsWith("[Continent]")) {
                    String continentName = reader.readLine().split("=")[1];
                    int controlValue = Integer.parseInt(reader.readLine().split("=")[1]);
                    currentContinent = new Continent(continentName, controlValue);
                    continents.add(currentContinent);
                } else if (line.startsWith("[Territory]")) {
                    String territoryName = reader.readLine().split("=")[1];
                    String continentName = reader.readLine().split("=")[1];
                    String[] neighbors = reader.readLine().split("=")[1].split(",");
                    Country country = new Country(territoryName);
                    countries.add(country);
                    countryNameMap.put(territoryName, country);

                    for (String neighborName : neighbors) {
                        Country neighbor = getCountryByName(neighborName);
                        if (neighbor != null) {
                            country.addNeighbor(neighbor);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading the map from file: " + e.getMessage());
        }
    }

    public boolean validateMap() {
        // Add map validation logic here
        return true; // Placeholder
    }

    private Country getCountryByName(String countryName) {
        return countryNameMap.get(countryName);
    }
    
    public List<Country> getCountries() {
        return countries;
    }
}