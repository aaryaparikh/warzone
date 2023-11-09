package Models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Models.Continent;
import Models.Country;
import Models.GameMap;

/**
 * JUnit 5 test class for GameMap class.
 *
 * @author Dev
 */
public class GameMapTest {
	private GameMap d_map;

	/**
	 * setup test
	 */
	@BeforeEach
	public void setup() {
		this.d_map = new GameMap();
	}

	/**
	 * after test
	 */
	@AfterEach
	public void teardown() {
		this.d_map = null;
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

		this.d_map.addContinent(3, 10);
		this.d_map.addContinent(2, 5);
		this.d_map.addContinent(1, 8);

		// then
		ArrayList<Continent> l_continents = this.d_map.getContinents();
		assertAll(() -> assertEquals(l_expectedContinents, this.d_map.getD_continentCount()),
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

		this.d_map.addContinent(3, 10);
		this.d_map.addContinent(2, 5);
		this.d_map.addContinent(1, 8);
		this.d_map.addContinent(4, 6);
		this.d_map.removeContinent(3);

		// then
		ArrayList<Continent> l_continents = this.d_map.getContinents();
		assertAll(() -> assertEquals(l_expectedContinents, this.d_map.getD_continentCount()),
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

		d_map.addCountry(14, 3);
		d_map.addCountry(1, 1);
		d_map.addCountry(4, 2);
		d_map.addCountry(3, 2);
		d_map.addCountry(2, 1);

		// then
		ArrayList<Country> l_countries = d_map.getCountries();
		assertEquals(l_expectedCountryCount, d_map.getD_countryCount());
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

		d_map.addCountry(14, 3);
		d_map.addCountry(1, 1);
		d_map.addCountry(4, 2);
		d_map.addCountry(3, 2);
		d_map.addCountry(2, 1);
		d_map.removeCountry(3);

		// then
		ArrayList<Country> l_countries = d_map.getCountries();
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
		d_map.addCountry(1, 1);
		d_map.addCountry(2, 2);
		d_map.addCountry(3, 2);
		d_map.addCountry(4, 2);

		d_map.addNeighbor(1, 2);
		d_map.addNeighbor(1, 3);
		d_map.addNeighbor(1, 4);

		// then
		ArrayList<Country> l_countries = d_map.getCountries();
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

		d_map.addCountry(1, 1);
		d_map.addCountry(2, 2);
		d_map.addCountry(3, 2);
		d_map.addCountry(4, 2);

		d_map.addNeighbor(1, 2);
		d_map.addNeighbor(1, 3);
		d_map.addNeighbor(1, 4);
		d_map.removeNeighbor(1, 3);

		// then
		ArrayList<Country> l_countries = d_map.getCountries();
		assertAll(() -> assertEquals(l_expectedNeighborCount, l_countries.get(0).getNeighborCountries().size()),
				() -> assertArrayEquals(l_neighborList, l_countries.get(0).getNeighborCountries().toArray()));
	}
}
