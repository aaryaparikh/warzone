package Models.Orders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.GameEngine;
import Models.Country;
import Models.GameMap;
import Models.Player;

/**
 * JUnit 5 test class for BlockadeOrder class.
 *
 * @author Dev
 */
public class DiplomacyOrderTest {

	/**
	 * Default Game map maker
	 *
	 * @param p_gameMap
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
	 * DiplomacyOrder instance
	 */
	private DiplomacyOrder d_dipOrder;

	/**
	 * BombOrder instance
	 */
	private BombOrder d_bombOrder;

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
	public void setup() {
		d_map = new GameMap();
		defaultGameMap(d_map);
		d_gameEngine = new GameEngine(d_map);
		d_p1 = new Player("Dev", d_gameEngine);
		d_p2 = new Player("Aarya", d_gameEngine);
		d_gameEngine.addPlayer(d_p1);
		d_gameEngine.addPlayer(d_p2);
		d_gameEngine.assignCountriesRandomly();
	}

	/**
	 * tears down the objects created in setup method
	 */
	@AfterEach
	public void teardown() {
		d_map = null;
		d_gameEngine = null;
		d_p1 = null;
		d_p2 = null;
		d_bombOrder = null;
		d_depOrder = null;
		d_dipOrder = null;
	}

	/**
	 * Checks whether the players negotiated properly
	 */
	@Test
	public void shouldCheckIfBothPlayersNegotiated() {
		String l_expected = "Player \"" + d_p2.getName() + "\" manage to negotiate with player \"" + d_p1.getName()
				+ "\".";

		d_depOrder = new DeployOrder(d_p1, getSource(), d_p1.getD_reinforcementPool());
		d_depOrder.execute(d_gameEngine);

		d_depOrder = new DeployOrder(d_p2, getInvalidSource(), d_p2.getD_reinforcementPool());
		d_depOrder.execute(d_gameEngine);

		d_dipOrder = new DiplomacyOrder(d_p2, d_p1);
		String l_returned = d_dipOrder.execute(d_gameEngine);

		assertEquals(l_expected, l_returned);

	}

	/**
	 * Checks if the player is able to bomb other player after negotiating
	 */
	@Test
	void shouldNotBomb() {
		String l_expected = "Can't bomb, there is a negotiation between \"" + d_p1.getName() + "\" and \""
				+ d_p2.getName() + "\".";

		d_depOrder = new DeployOrder(d_p1, getSource(), d_p1.getD_reinforcementPool());
		d_depOrder.execute(d_gameEngine);

		d_depOrder = new DeployOrder(d_p2, getInvalidSource(), d_p2.getD_reinforcementPool());
		d_depOrder.execute(d_gameEngine);

		d_dipOrder = new DiplomacyOrder(d_p2, d_p1);
		d_dipOrder.execute(d_gameEngine);

		d_bombOrder = new BombOrder(d_p1, getInvalidSource());
		String l_returned = (d_bombOrder.execute(d_gameEngine));

		assertEquals(l_expected, l_returned);
	}

}
