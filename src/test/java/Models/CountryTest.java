package Models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for Country class.
 *
 * @author Dev
 */
public class CountryTest {

	private Country d_country;

	/**
	 * sets up <i>d_country</i> variable of type Country and initialize it with
	 * predefined values
	 */
	@BeforeEach
	public void setup() {
		this.d_country = new Country(15, 1);
	}

	/**
	 * tears down the <i>d_country</i> variable by setting it to null
	 */
	@AfterEach
	public void teardown() {
		this.d_country = null;
	}

	/**
	 * checks whether <i>getCountryId()</i> function works fine
	 */
	@Test
	public void should_ReturnCountryId() {
		// given
		int l_expectedCountryId = 15;

		// then
		assertEquals(l_expectedCountryId, d_country.getCountryId());
	}

	/**
	 * checks whether <i>getContinentId()</i> function works fine
	 */
	@Test
	public void should_ReturnContinentId() {
		// given
		int l_expectedContinentId = 1;

		// then
		assertEquals(l_expectedContinentId, d_country.getContinentId());
	}

	/**
	 * checks whether the countryId added as the neighbor are correctly done
	 */
	@Test
	public void should_ReturnValidNumberOfNeighbor_When_AddedNeighbors() {
		// given
		int l_expectedNeighbors = 6;
		Object[] test_arr = { 2, 3, 4, 5, 6, 7 };

		this.d_country.addNeighbor(2);
		this.d_country.addNeighbor(3);
		this.d_country.addNeighbor(4);
		this.d_country.addNeighbor(5);
		this.d_country.addNeighbor(6);
		this.d_country.addNeighbor(7);

		// then
		assertAll(() -> assertEquals(l_expectedNeighbors, this.d_country.getNeighborCountries().size()),
				() -> assertArrayEquals(test_arr, this.d_country.getNeighborCountries().toArray()));
	}

	/**
	 * checks whether the countryId removed is reflected properly
	 */
	@Test
	public void should_ReturnValidNumberOfNeighbor_When_NeighborRemoved() {
		// given
		int l_expectedNeighbors = 5;
		Object[] l_test_arr = { 2, 3, 4, 6, 7 };

		this.d_country.addNeighbor(2);
		this.d_country.addNeighbor(3);
		this.d_country.addNeighbor(4);
		this.d_country.addNeighbor(5);
		this.d_country.addNeighbor(6);
		this.d_country.addNeighbor(7);
		this.d_country.removeNeighbor(5);

		// then
		assertAll(() -> assertEquals(l_expectedNeighbors, this.d_country.getNeighborCountries().size()),
				() -> assertArrayEquals(l_test_arr, this.d_country.getNeighborCountries().toArray()));

	}

	/**
	 * checks whether add and subtract Armies works fine
	 */
	@Test
	public void shoudl_ReturnValidNoOfArmies_AfterAddingandRemoving() {
		// given
		int l_expectedArmyAfterAdd = 10;
		int l_expectedArmyAfterSub = 5;

		// when
		d_country.setArmies(5);
		d_country.addArmies(5);

		// then
		assertEquals(l_expectedArmyAfterAdd, d_country.getArmies());

		// when
		d_country.subtractArmies(5);

		//
		assertEquals(l_expectedArmyAfterSub, d_country.getArmies());

	}

}
