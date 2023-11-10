package Models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.GameEngine;

/**
 * JUnit 5 test class for Player class.
 *
 * @author Dev
 */
public class PlayerTest {

	/**
	 * Player instance
	 */
	private Player d_player;

	/**
	 * set up test
	 */
	@BeforeEach
	public void setUp() {
		this.d_player = new Player("Dev", new GameEngine(new GameMap()));
	}

	/**
	 * after test
	 */
	@AfterEach
	public void teardown() {
		this.d_player = null;
	}

	/**
	 * checks whether the Player's name is returned appropriately
	 */
	@Test
	public void should_ReturnName() {
		assertEquals("Dev", d_player.getName());
	}

	/**
	 * checks whether the reinforcement is
	 */
	@Test
	public void should_ReturnValidReinforment_AfterAddingAndRemoving() {
		// given
		int l_expectedReinforcement = 4;

		// when
		d_player.assignReinforcements(2);
		d_player.assignReinforcements(4);
		d_player.decreaseReinforcementPool(2);

		// then
		assertEquals(l_expectedReinforcement, d_player.getD_reinforcementPool());
	}

	/**
	 * checks whether the Country list is maintained properly
	 */
	@Test
	public void should_ReturnValidCountry() {
		// given
		int l_expectedCountry = 4;
		Country[] l_country = new Country[4];
		l_country[0] = new Country(1, 1);
		l_country[1] = new Country(2, 2);
		l_country[2] = new Country(3, 2);
		l_country[3] = new Country(4, 2);

		// when
		d_player.addCountry(l_country[0]);
		d_player.addCountry(l_country[1]);
		d_player.addCountry(l_country[2]);
		d_player.addCountry(l_country[3]);

		// then
		assertAll(() -> assertEquals(l_expectedCountry, d_player.getD_countries().size()),
				() -> assertEquals(l_country[0].getCountryId(), d_player.getD_countries().get(0).getCountryId()));

		// when
		d_player.resetCountry();

		// then
		assertEquals(0, d_player.getD_countries().size());
	}

}
