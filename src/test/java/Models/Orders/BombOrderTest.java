package Models.Orders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.GameEngine;
import Models.Country;
import Models.GameMap;
import Models.Player;

public class BombOrderTest {

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
	public BombOrder d_bombOrder;
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
	 * Checks whether player can bomb on his own country
	 */
	@Test
	public void shouldReturnCantBomb() {
		// given
		String l_expected = "Player \"" + d_p1.getName() + "\" control target country \"" + getSource().getCountryId()
				+ "\", can't bomb.";

		// when
		Country l_source = getSource();
		d_depOrder = new DeployOrder(d_p1, l_source, d_p1.getD_reinforcementPool());
		d_depOrder.execute(d_gameEngine);

		d_bombOrder = new BombOrder(d_p1, l_source);
		assertEquals(l_expected, d_bombOrder.execute(d_gameEngine));
	}

	/**
	 * should bomb the target country
	 */
//	@Test
//	public void test() {
//		String l_expected = "";
//		Country l_source = getSource();
//		Country l_target = getInvalidSource();
//
//		d_depOrder = new DeployOrder(d_p1, l_source, d_p1.getD_reinforcementPool());
//		d_depOrder.execute(d_gameEngine);
//
//		d_depOrder = new DeployOrder(d_p2, getInvalidSource(), d_p2.getD_reinforcementPool());
//		d_depOrder.execute(d_gameEngine);
//
//		if (d_p1.getD_countries().get(0).getNeighborCountries().contains(l_target.getCountryId())) {
//			int l_remaining = l_target.getArmies() - l_target.getArmies() / 2;
//			l_expected = "Player \"" + d_p1.getName() + "\" bombed country \"" + l_target.getCountryId() + "\", kill \""
//					+ l_remaining + "\" armies.";
//			d_bombOrder = new BombOrder(d_p1, l_target);
//			System.out.println(d_p1.getD_countries().get(0).getNeighborCountries());
//			System.out.println(l_target.getCountryId());
//
//			String l_returned = d_bombOrder.execute(d_gameEngine);
//			System.out.println(l_returned);
//			System.out.println(l_expected);
//			System.out.println("if");
//			assertEquals(l_expected, l_returned);
//		} else {
//			l_expected = "Player \"" + d_p1.getName() + "\" is not adjacent to target country \""
//					+ l_target.getCountryId() + "\", can't bomb.";
//			d_bombOrder = new BombOrder(d_p1, l_target);
//			String l_returned = d_bombOrder.execute(d_gameEngine);
//			System.out.println(l_returned);
//			System.out.println(l_expected);
//			System.out.println("else");
//			assertEquals(l_expected, l_returned);
//		}
//	}
}
