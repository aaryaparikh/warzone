package Controller;

import org.junit.Test;

import Models.Continent;
import Models.Country;
import Models.GameMap;
import Utils.MapEditor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class ValidatemapTest(){

    // Validates a valid map with at least one country and one continent.
	@Test
    public void test_valid_map_with_one_country_and_one_continent() {
        // Arrange
        List<Continent> continents = new ArrayList<>();
        continents.add(new Continent(1, 5));

        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1, 1));

        GameMap gameMap = new GameMap();
        gameMap.setContinents(continents);
        gameMap.setCountries(countries);

        MapEditor mapEditor = new MapEditor(gameMap);

        // Act
        boolean result = mapEditor.validateMap();

        // Assert
        assertTrue(result);
    }

	// Validates a map with multiple continents and countries.
	@Test
    public void test_valid_map_with_multiple_continents_and_countries() {
        // Arrange
        List<Continent> continents = new ArrayList<>();
        continents.add(new Continent(1, 5));
        continents.add(new Continent(2, 3));

        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1, 1));
        countries.add(new Country(2, 1));
        countries.add(new Country(3, 2));

        GameMap gameMap = new GameMap();
        gameMap.setContinents(continents);
        gameMap.setCountries(countries);

        MapEditor mapEditor = new MapEditor(gameMap);

        // Act
        boolean result = mapEditor.validateMap();

        // Assert
        assertTrue(result);
    }

	// Validates a map with all countries having at least one neighboring country.
	@Test
    public void test_valid_map_with_all_countries_having_neighbors(){
        // Arrange
        List<Continent> continents = new ArrayList<>();
        continents.add(new Continent(1, 5));

        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1, 1));
        countries.add(new Country(2, 1));

        List<Integer> neighbors = new ArrayList<>();
        neighbors.add(2);

        countries.get(1).setNeighborCountries(neighbors);

        GameMap gameMap = new GameMap();
        gameMap.setContinents(continents);
        gameMap.setCountries(countries);

        MapEditor mapEditor = new MapEditor(gameMap);

        // Act
        boolean result = mapEditor.validateMap();

        // Assert
        assertTrue(result);
    }

	// Empty Map.
	@Test
    public void test_empty_map() {
        // Arrange
        List<Continent> continents = new ArrayList<>();
        List<Country> countries = new ArrayList<>();

        GameMap gameMap = new GameMap();
        gameMap.setContinents(continents);
        gameMap.setCountries(countries);

        MapEditor mapEditor = new MapEditor(gameMap);

        // Act
        boolean result = mapEditor.validateMap();

        // Assert
        assertFalse(result);
    }

	// Duplicate Continents.
	@Test
    public void test_duplicate_continents() {
        // Arrange
        List<Continent> continents = new ArrayList<>();
        continents.add(new Continent(1, 5));
        continents.add(new Continent(1, 3));

        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1, 1));

        GameMap gameMap = new GameMap();
        gameMap.setContinents(continents);
        gameMap.setCountries(countries);

        MapEditor mapEditor = new MapEditor(gameMap);

        // Act
        boolean result = mapEditor.validateMap();

        // Assert
        assertFalse(result);
    }
}
