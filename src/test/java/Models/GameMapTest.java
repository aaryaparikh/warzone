package Models;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for Gamemap class.
 * 
 * @author Virag
 */
public class GameMapTest {
	private GameMap t_map;

	/**
	 * setup test
	 */
	@BeforeEach
	public void setup() {
		this.t_map = new GameMap();
	}

	/**
	 * after test
	 */
	@AfterEach
	public void teardown() {
		this.t_map = null;
	}

	/**
	 * checks whether continent added is sorted and continent count is same as
	 * expected
	 */
	@Test
	public void should_AddContinent() {
		// given
		int l_expectedContinents = 3;
		int[] l_arr = { 1, 2, 3 };
		int[] l_troops = { 8, 5, 10 };

		this.t_map.addContinent(3, 10);
		this.t_map.addContinent(2, 5);
		this.t_map.addContinent(1, 8);

		// then
		ArrayList<Continent> l_continents = this.t_map.getContinents();
		assertAll(() -> assertEquals(l_expectedContinents, this.t_map.getD_continentCount()),
				() -> assertEquals(l_arr[0], l_continents.get(0).getContinentId()),
				() -> assertEquals(l_troops[0], l_continents.get(0).getContinentValue()),
				() -> assertEquals(l_arr[1], l_continents.get(1).getContinentId()),
				() -> assertEquals(l_troops[1], l_continents.get(1).getContinentValue()),
				() -> assertEquals(l_arr[2], l_continents.get(2).getContinentId()),
				() -> assertEquals(l_troops[2], l_continents.get(2).getContinentValue()));
	}

	/**
	 * checks whether continent removed from the object is reflected
	 */
	@Test
	public void should_RemoveContinent() {
		// given
		int l_expectedContinents = 3;
		int[] l_arr = { 1, 2, 4 };
		int[] l_troops = { 8, 5, 6 };

		this.t_map.addContinent(3, 10);
		this.t_map.addContinent(2, 5);
		this.t_map.addContinent(1, 8);
		this.t_map.addContinent(4, 6);
		this.t_map.removeContinent(3);

		// then
		ArrayList<Continent> l_continents = this.t_map.getContinents();
		assertAll(() -> assertEquals(l_expectedContinents, this.t_map.getD_continentCount()),
				() -> assertEquals(l_arr[0], l_continents.get(0).getContinentId()),
				() -> assertEquals(l_arr[1], l_continents.get(1).getContinentId()),
				() -> assertEquals(l_arr[2], l_continents.get(2).getContinentId()),
				() -> assertEquals(l_troops[0], l_continents.get(0).getContinentValue()),
				() -> assertEquals(l_troops[1], l_continents.get(1).getContinentValue()),
				() -> assertEquals(l_troops[2], l_continents.get(2).getContinentValue()));
	}

	/**
	 * checks whether country are stored and sorted and also number of country
	 * stored is equal
	 */
	@Test
	public void should_AddCountry() {
		// given
		int l_expectedCountryCount = 5;
		int[] l_expCountry = { 1, 2, 3, 4, 14 };
		int[] l_expContinent = { 1, 1, 2, 2, 3 };

		t_map.addCountry(14, 3);
		t_map.addCountry(1, 1);
		t_map.addCountry(4, 2);
		t_map.addCountry(3, 2);
		t_map.addCountry(2, 1);

		// then
		ArrayList<Country> l_countries = t_map.getCountries();
		assertEquals(l_expectedCountryCount, t_map.getD_countryCount());
		for (int i = 0; i < 5; i++) {
			assertEquals(l_expCountry[i], l_countries.get(i).getCountryId());
			assertEquals(l_expContinent[i], l_countries.get(i).getContinentId());
		}
	}

	/**
	 * checks whether the country is properly removed from the ArrayList
	 */
	@Test
	public void should_RemoveCountry() {
		// given
		int l_expectedCountryCount = 4;
		int[] l_expCountry = { 1, 2, 4, 14 };
		int[] l_expContinent = { 1, 1, 2, 3 };

		t_map.addCountry(14, 3);
		t_map.addCountry(1, 1);
		t_map.addCountry(4, 2);
		t_map.addCountry(3, 2);
		t_map.addCountry(2, 1);
		t_map.removeCountry(3);

		// then
		ArrayList<Country> l_countries = t_map.getCountries();
		assertEquals(l_expectedCountryCount, l_countries.size());
		assertFalse(l_countries.contains(new Country(3, 2)));
		for (int i = 0; i < 4; i++) {
			assertEquals(l_expCountry[i], l_countries.get(i).getCountryId());
			assertEquals(l_expContinent[i], l_countries.get(i).getContinentId());
		}
	}

	/**
	 * checks whether the neighbors are added properly and is reflected in each
	 * country that involves
	 */
	@Test
	public void should_AddNeighbors() {
		// given neighbor list
		int l_expectedNeighborCount = 3;
		Object[] l_neighborList = { 2, 3, 4 };
		Object[] l_oppositeList = { 1 };

		t_map.addCountry(1, 1);
		t_map.addCountry(2, 2);
		t_map.addCountry(3, 2);
		t_map.addCountry(4, 2);

		t_map.addNeighbor(1, 2);
		t_map.addNeighbor(1, 3);
		t_map.addNeighbor(1, 4);

		// then
		ArrayList<Country> l_countries = t_map.getCountries();
		assertAll(() -> assertArrayEquals(l_neighborList, l_countries.get(0).getNeighborCountries().toArray()),
				() -> assertEquals(l_expectedNeighborCount, l_countries.get(0).getNeighborCountries().size()));
	}

	/**
	 * checks whether the neighbors are added properly and is reflected in the
	 * country
	 */
	@Test
	public void should_RemoveNeighbor() {
		// given
		int l_expectedNeighborCount = 2;
		Object[] l_neighborList = { 2, 4 };

		t_map.addCountry(1, 1);
		t_map.addCountry(2, 2);
		t_map.addCountry(3, 2);
		t_map.addCountry(4, 2);

		t_map.addNeighbor(1, 2);
		t_map.addNeighbor(1, 3);
		t_map.addNeighbor(1, 4);
		t_map.removeNeighbor(1, 3);

		// then
		ArrayList<Country> l_countries = t_map.getCountries();
		assertAll(() -> assertEquals(l_expectedNeighborCount, l_countries.get(0).getNeighborCountries().size()),
				() -> assertArrayEquals(l_neighborList, l_countries.get(0).getNeighborCountries().toArray()));
	}

}
