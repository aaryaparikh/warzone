package Models.Orders;

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

	private AdvanceOrder d_advOrder;
	private GameMap d_map;
	private GameEngine d_gameEngine;
	private Player d_player1;

	/**
	 * sets up some objects and initialize it with some predefined values
	 */
	@BeforeEach
	public void setup() {
		d_map = new GameMap();
		defaultGameMap(d_map);
		d_gameEngine = new GameEngine(d_map);
		d_player1 = new Player("Dev", d_gameEngine);
		d_gameEngine.addPlayer(d_player1);
		d_gameEngine.assignCountriesRandomly();
		d_player1.assignReinforcements(10);
	}

	/**
	 * tears down the objects created in setup method
	 */
	@AfterEach
	public void teardown() {
		d_map = null;
		d_gameEngine = null;
		d_player1 = null;
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
		Country l_invalidSource = getSource();

		for (Country c : d_gameEngine.getGameMap().getCountries()) {
			if (!d_gameEngine.getPlayers().get(0).getD_countries().contains(c)) {
				l_invalidSource = c;
			}
		}
		System.out.println(l_invalidSource.getCountryId());
		return l_invalidSource;
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
		assertTrue(d_gameEngine.checkPlayerInList(d_player1));
		// asserts if reinforcement is added to the player
		assertEquals(10, d_gameEngine.getPlayers().get(0).getD_reinforcementPool());
	}

	/**
	 * checks whether <i>advance</i> order works fine
	 */
	@Test
	public void shouldReturnString_For_SuccessfulAdvancement() {
		// given
		String l_expected = "";

		// when
		Country l_sourceCountry = getSource();
		int l_resource = l_sourceCountry.getCountryId();
		System.out.println(l_resource);
		Country l_targetCountry = getValidTarget();
		int l_target = l_targetCountry.getCountryId();

		l_expected = "Armies successfully moved from country \"" + l_resource + "\" to country \"" + l_target + "\"";

		d_advOrder = new AdvanceOrder(d_player1, l_sourceCountry, l_targetCountry, 10);

		// then
		assertEquals(l_expected, d_advOrder.execute(d_gameEngine));
	}
}
