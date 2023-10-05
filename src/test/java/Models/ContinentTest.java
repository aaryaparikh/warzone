package Models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * JUnit 5 test class for Continent class.
 * 
 * @author Dev
 */
public class ContinentTest {

	private Continent t_continent;

	/**
	 * sets up <i>t_continent</i> variable of type Continent and initialize it with
	 * predefined values
	 */
	@BeforeEach
	public void setup() {
		this.t_continent = new Continent(1, 5);
	}

	/**
	 * tears down the <i>t_continent</i> variable by setting it to null
	 */
	@AfterEach
	public void teardown() {
		this.t_continent = null;
	}

	/**
	 * tests the getter <i>getContinentId</i> and test that it returns valid value
	 */
	@Test
	public void should_ReturnContinentID() {
		// given
		int l_expectedContinentID = 1;

		// then
		assertEquals(l_expectedContinentID, t_continent.getContinentId());
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
		assertEquals(l_expectedContinentValue, t_continent.getContinentValue());
	}

}
