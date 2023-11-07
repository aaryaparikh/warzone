package Models.Orders;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.GameEngine;
import Models.Country;
import Models.GameMap;
import Models.Player;
import Utils.MapEditor;

/**
 * JUnit 5 test class for DeployOrder class.
 *
 * @author Dev
 */
public class DeployOrderTest {

	public static DeployOrder d_depOrder;
	public static GameMap d_map;
	public static MapEditor d_lmap;
	public static Player d_p1;
	public static Player d_p2;
	public static GameEngine d_gameEngine;

	public void defaultGameMap(GameMap p_gameMap) {
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
		return d_gameEngine.getPlayers().get(1).getD_countries().get(0);
	}

	/**
	 * sets up some objects and initialize it with some predefined values
	 */
	@BeforeEach
	public void setUp() {
		d_map = new GameMap();
		defaultGameMap(d_map);
		d_gameEngine = new GameEngine(d_map);
		d_p1 = new Player("Dev", d_gameEngine);
		d_p2 = new Player("Aarya", d_gameEngine);
		d_gameEngine.addPlayer(d_p1);
		d_gameEngine.addPlayer(d_p2);
		d_gameEngine.assignCountriesRandomly();
		d_p1.assignReinforcements(10);
		d_p2.assignReinforcements(4);
	}

	/**
	 * tears down the objects created in setup method
	 */
	@AfterEach
	public void tearDown() {
		d_lmap = null;
		d_map = null;
		d_p1 = null;
		d_p2 = null;
		d_depOrder = null;
	}

	/**
	 * checks whether <i>DeployOrder</i> deploys the armies to valid country
	 */
	@Test
	public void shouldDeployArmies() {
		// given
		String l_expected = "";

		// when
		Country l_sourceCountry = getSource();
		d_depOrder = new DeployOrder(d_p1, l_sourceCountry, 5);
		l_expected = "Player \"" + d_depOrder.getPlayer().getName() + "\" deployed \"" + 5 + "\" armies to country \""
				+ l_sourceCountry.getCountryId() + "\"";
		// then
		assertEquals(l_expected, d_depOrder.execute(d_gameEngine));
	}

	/**
	 * checks whether <i>DeployOrder</i> generate appropriate message for invalid
	 * country
	 */
	@Test
	public void shouldNotDeployArmies() {
		// given
		String l_expected = "";

		// when
		Country l_sourceCountry = getInvalidSource();
		d_depOrder = new DeployOrder(d_p1, l_sourceCountry, 5);
		l_expected = "Player \"Dev\" can\'t deploy to country \"5\", since it is not his territory.";

		// then
		assertEquals(l_expected, d_depOrder.execute(d_gameEngine));
	}
}
