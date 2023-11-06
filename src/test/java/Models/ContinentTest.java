package Models;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for Continent class.
 *
 * @author Dev
 */
public class ContinentTest {

	private Continent d_continent;

	/**
	 * sets up <i>d_continent</i> variable of type Continent and initialize it with
	 * predefined values
	 */
	@BeforeEach
	public void setup() {
		this.d_continent = new Continent(1, 5);
	}

	/**
	 * tears down the <i>d_continent</i> variable by setting it to null
	 */
	@AfterEach
	public void teardown() {
		this.d_continent = null;
	}

	/**
	 * tests the getter <i>getContinentId</i> and test that it returns valid value
	 */
	@Test
	public void should_ReturnContinentID() {
		// given
		int l_expectedContinentID = 1;

		// then
		assertEquals(l_expectedContinentID, d_continent.getContinentId());
	}

	/**
	 * tests the getter <i>getContinentValue</i> and test that it returns valid
	 * value
	 */
	@Test
	public void should_ReturnContinentValue() {
		// given
		int l_expectedContinentValue = 5;

		// then
		assertEquals(l_expectedContinentValue, d_continent.getContinentValue());
	}

}
