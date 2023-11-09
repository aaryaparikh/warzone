package Models.Orders;

import static org.junit.Assert.assertNull;
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
class BlockadeOrderTest {

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

	public DeployOrder d_depOrder;
	public AdvanceOrder d_advOrder;
	public BlockadeOrder d_blockeOrder;
	public GameMap d_map;
	public GameEngine d_gameEngine;
	public Player d_p1;
	public Player d_p2;

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
		d_p2 = null;
	}

	/**
	 * Checks whether Blockade returns valid String
	 */
	@Test
	public void shouldReturnValidString_For_Blockade() {
		// given
		String l_expected = null;

		// when
		Country l_source = getSource();
		d_depOrder = new DeployOrder(d_p1, l_source, d_p1.getD_reinforcementPool());
		d_depOrder.execute(d_gameEngine);

		d_p1.getD_countries().stream().forEach(ele -> System.out.println(ele.getCountryId()));
		d_p1.getD_countries().stream().forEach(ele -> System.out.println(ele.getArmies()));
		// then
		d_blockeOrder = new BlockadeOrder(d_p1, l_source);
		String returned = d_blockeOrder.execute(d_gameEngine);
		l_expected = "Player \"" + d_p1.getName() + "\" blockade country \"" + l_source.getCountryId() + "\", it has \""
				+ l_source.getArmies() + "\" armies and become neutral.";

		assertEquals(l_expected, returned);
	}

	/**
	 * Checks integrity of the owner being set to null
	 */
	@Test
	public void shouldMaintainIntegrity_Of_Blockade() {
		// when
		Country l_source = getSource();
		d_depOrder = new DeployOrder(d_p1, l_source, d_p1.getD_reinforcementPool());
		d_depOrder.execute(d_gameEngine);

		// then
		d_blockeOrder = new BlockadeOrder(d_p1, l_source);
		d_blockeOrder.execute(d_gameEngine);

		assertNull(l_source.getOwner());
	}
}