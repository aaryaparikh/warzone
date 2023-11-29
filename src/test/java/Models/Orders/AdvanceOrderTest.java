package Models.Orders;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.GameEngine;
import Models.Country;
import Models.GameMap;
import Models.Player;

/**
 * JUnit 5 test class for AdvanceOrder class.
 *
 * @author Dev
 */
public class AdvanceOrderTest {
	/**
	 * Default Game map maker
	 *
	 * @param p_gameMap game map
	 */
	private void defaultGameMap(GameMap p_gameMap) {
		p_gameMap.addContinent(1, 3);
		p_gameMap.addContinent(2, 5);

		p_gameMap.addCountry(1, 1);
		p_gameMap.addCountry(2, 1);
		p_gameMap.addCountry(7, 2);

		p_gameMap.addNeighbor(1, 2);
		p_gameMap.addNeighbor(2, 1);
		p_gameMap.addNeighbor(7, 1);
		p_gameMap.addNeighbor(1, 7);
	}

	/**
	 * DeployOrder instance
	 */
	private DeployOrder d_depOrder;

	/**
	 * AdvanceOrder instance
	 */
	private AdvanceOrder d_advOrder;

	/**
	 * GameMap instance
	 */
	private GameMap d_map;

	/**
	 * GameEngine instance
	 */
	private GameEngine d_gameEngine;

	/**
	 * Player instance
	 */
	private Player d_p1;

	/**
	 * Player instance
	 */
	private Player d_p2;

	/**
	 * sets up some objects and initialize it with some predefined values
	 */
	@BeforeEach
	public void setup() {
		d_map = new GameMap();
		defaultGameMap(d_map);
		d_gameEngine = new GameEngine(d_map);
		d_p1 = new Player("Dev", d_gameEngine);
		d_p2 = new Player("Aarya", d_gameEngine);
		d_gameEngine.addPlayer(d_p1);
		d_gameEngine.addPlayer(d_p2);
		d_gameEngine.assignCountriesRandomly();
		d_p1.assignReinforcements(10);
		d_p2.assignReinforcements(5);
	}

	/**
	 * tears down the objects created in setup method
	 */
	@AfterEach
	public void teardown() {
		d_map = null;
		d_gameEngine = null;
		d_p1 = null;
		d_advOrder = null;
		d_depOrder = null;
	}

	/**
	 * Used to get the source country
	 *
	 * @return The correct source Country
	 */
	public Country getSource() {
		return d_gameEngine.getPlayers().get(0).getD_countries().get(0);
	}

	/**
	 * Used to get the invalid source country
	 *
	 * @return The incorrect source Country object
	 */
	public Country getInvalidSource() {
		return d_p2.getD_countries().get(0);
	}

	/**
	 * Used to get the valid target country
	 *
	 * @return The correct Target Country object
	 */
	public Country getValidTarget() {
		Country l_targetCountry = getSource();
		int l_target = getSource().getNeighborCountries().get(0);

		for (Country c : d_gameEngine.getGameMap().getCountries()) {
			if (c.getCountryId() == l_target) {
				l_targetCountry = c;
				break;
			}
		}
		return l_targetCountry;
	}

	/**
	 * Checks whether the Player object is added to GameEngine
	 */
	@Test
	public void shouldSetOwnerCorrectly() {
		// asserts the instantiation of Player in GameEngine
		assertTrue(d_gameEngine.checkPlayerInList(d_p1));
		// asserts if reinforcement is added to the player
		assertEquals(10, d_gameEngine.getPlayers().get(0).getD_reinforcementPool());
	}

	/**
	 * checks whether <i>advance</i> order works fine
	 */
	@Test
	public void shouldReturnString_For_SuccessfulAdvancement() {

		Country l_source = getSource();
		Country l_target = getValidTarget();
		int l_armies = 5;
		String l_expected_When_SameOwner = "\"" + l_armies + "\" armies are moved from country \""
				+ l_source.getCountryId() + "\" to country \"" + l_target.getCountryId() + "\"";
		String l_expected_When_DiffOwner = "\"" + l_armies + "\" armies are moved from country \""
				+ l_source.getCountryId() + "\" to country \"" + l_target.getCountryId() + "\", \"" + d_p1.getName()
				+ "\" occupy country \"" + l_target.getCountryId() + "\", \"" + l_armies + "\" armies remain.";

		// deploy
		d_depOrder = new DeployOrder(d_p1, l_source, d_p1.getD_reinforcementPool());
		d_depOrder.execute(d_gameEngine);

		// advance
		d_advOrder = new AdvanceOrder(d_p1, l_source, l_target, l_armies);

		if (l_target.getOwner() == d_p1) {
			assertEquals(l_expected_When_SameOwner, d_advOrder.execute(d_gameEngine));
		} else {
			assertEquals(l_expected_When_DiffOwner, d_advOrder.execute(d_gameEngine));
		}
	}

	/**
	 * checks whether <i>advance</i> give valid string when passed incorrect source
	 * country
	 */
	@Test
	public void shouldReturn_ValidString_For_IncorrectCountry() {
		Country l_correctSource = getSource();
		Country l_incorrectSource = getInvalidSource();
		Country l_target = getValidTarget();
		String l_expected = "Player \"" + d_p1.getName() + "\" does not control country \""
				+ l_incorrectSource.getCountryId() + "\", thus armies cannot be moved.";

		int l_armies = d_p1.getD_reinforcementPool();

		d_depOrder = new DeployOrder(d_p1, l_correctSource, l_armies);
		d_depOrder.execute(d_gameEngine);

		d_advOrder = new AdvanceOrder(d_p1, l_incorrectSource, l_target, l_armies);

		assertEquals(l_expected, d_advOrder.execute(d_gameEngine));
	}

	/**
	 * checks whether the number of country
	 */
	@Test
	public void shouldMaintainIntigrity() {
		Country l_source = getSource();
		Country l_target = getValidTarget();
		int l_armies = 5;
		int l_expected_armies = 5;

		// deploy
		d_depOrder = new DeployOrder(d_p1, l_source, d_p1.getD_reinforcementPool());
		d_depOrder.execute(d_gameEngine);

		d_advOrder = new AdvanceOrder(d_p1, l_source, l_target, l_armies);
		d_advOrder.execute(d_gameEngine);

		assertAll(() -> assertEquals(l_expected_armies, l_source.getArmies()),
				() -> assertEquals(d_p1.getD_reinforcementPool(), l_source.getArmies() + l_target.getArmies()));

	}

	/**
	 * Test the scenario where the target country is not a neighbor of the source
	 * country.
	 * 
	 * @author Virag
	 */

	@Test
	public void shouldReturnString_For_NonNeighborTargetCountry() {
		Country source = getSource();
		Country nonNeighborTarget = d_gameEngine.getGameMap().getCountries().stream()
				.filter(c -> !source.getNeighborCountries().contains(c.getCountryId())).findFirst().orElseThrow(); // Assumes
																													// there
																													// is
																													// at
																													// least
																													// one
																													// non-neighbor
																													// country

		int armies = 5;

		d_depOrder = new DeployOrder(d_p1, source, armies);
		d_depOrder.execute(d_gameEngine);

		d_advOrder = new AdvanceOrder(d_p1, source, nonNeighborTarget, armies);
		String result = d_advOrder.execute(d_gameEngine);

		assertEquals(String.format(
				"Armies can't be moved from country \"%d\" to country \"%d\" because they are not neighbors",
				source.getCountryId(), nonNeighborTarget.getCountryId()), result);
	}

	/**
	 * Test the scenario where the target country is neutral.
	 * 
	 * @author Virag
	 */
	@Test
	public void shouldConquerNeutralTargetCountry() {
		Country source = getSource();

		// Find a neutral country or create one if none is present
		Country neutralTarget = d_gameEngine.getGameMap().getCountries().stream().filter(c -> c.getOwner() == null)
				.findFirst().orElseGet(() -> {
					Country newNeutralCountry = new Country(1, 1);
					d_gameEngine.getGameMap().addCountry(1, 1);
					return newNeutralCountry;
				});

		int armies = source.getArmies();

		d_depOrder = new DeployOrder(d_p1, source, armies);
		d_depOrder.execute(d_gameEngine);

		d_advOrder = new AdvanceOrder(d_p1, source, neutralTarget, armies);
		String result = d_advOrder.execute(d_gameEngine);

		assertFalse(result.contains(String.format("\"%d\" armies are moved from country \"%d\" to country \"%d\"",
				armies, source.getCountryId(), neutralTarget.getCountryId())));

	}

}