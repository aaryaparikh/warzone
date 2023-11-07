package Models.Orders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.GameEngine;
import Models.Country;
import Models.GameMap;
import Models.Player;

class AirliftOrderTest {

	/**
	 * JUnit 5 test class for AdvanceOrder class.
	 *
	 * @author Dev
	 */
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

	// Creating data members required to execute tests
	public AirliftOrder d_airLift;
	public DeployOrder d_depOrder;
	public GameMap d_map;
	public GameEngine d_gameEngine;
	public Player d_p1;
	public Player d_p2;

	/**
	 * Method to get the country object for player
	 *
	 * @return source country for player1
	 */
	public Country getSourceForPlayer1() {
		return d_gameEngine.getPlayers().get(0).getD_countries().get(0);
	}

	/**
	 * Method to get the country object for player
	 *
	 * @return source country for player2
	 */
	public Country getSourceForPlayer2() {
		return d_gameEngine.getPlayers().get(1).getD_countries().get(0);
	}

	/**
	 * Method to get the valid target country object for player
	 *
	 * @return source country for player1
	 */
	public Country getValidTarget(Country c1) {
		Country l_targetCountry = getSourceForPlayer1();
		int l_target = getSourceForPlayer1().getNeighborCountries().get(0);

		for (Country c : d_gameEngine.getGameMap().getCountries()) {
			if (c != c1 && c.getOwner() == d_p1) {
				l_targetCountry = c;
				break;
			}
		}
		return l_targetCountry;
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
	 * Checks whether <i>airlift</i> order works fine
	 */
	@Test
	void shouldReturnValidString_When_Airlifted() {
		Country l_source1 = getSourceForPlayer1();
		Country l_source2 = getSourceForPlayer2();
		int l_armies = 4;
		d_depOrder = new DeployOrder(d_p1, l_source1, d_p1.getD_reinforcementPool());
		if (d_p1.getD_countries().size() > 1) {
			Country l_target = getValidTarget(d_p1.getD_countries().get(0));

			d_airLift = new AirliftOrder(d_p1, l_source1, l_target, l_armies);
			String l_expected1 = "Armies successfully airlifted from country \"" + l_source1.getCountryId()
					+ "\" to country \"" + d_p1.getD_countries().get(1).getCountryId() + "\"";
			assertEquals(l_expected1, d_airLift.execute(d_gameEngine));

			d_airLift = new AirliftOrder(d_p2, l_source1, l_target, l_armies);
			l_expected1 = "Player \"" + d_p2.getName() + "\" does not control resource country \""
					+ l_source1.getCountryId() + "\", hence armies cannot be airlifted.";
			assertEquals(l_expected1, d_airLift.execute(d_gameEngine));
		} else {
			Country l_target = getSourceForPlayer2();
			String l_expected = "Player \"" + d_p1.getName() + "\" does not control resource country \""
					+ l_target.getCountryId() + "\", hence armies cannot be airlifted.";
			d_airLift = new AirliftOrder(d_p1, l_source1, l_target, l_armies);
			System.out.println(d_airLift.execute(d_gameEngine));
			assertEquals(l_expected, d_airLift.execute(d_gameEngine));
		}
	}

	/**
	 * Checks if <i>airlift</i> order return appropriate string when armies are in
	 * sufficient
	 */
	@Test
	void shouldReturnValidStringForLessArmies() {
		// given
		int l_expectedArmies = d_p1.getD_reinforcementPool();

		// creating source, target and armies to operate on
		Country l_source1 = getSourceForPlayer1();
		Country l_target = getValidTarget(l_source1);
		int l_armies = d_p1.getD_reinforcementPool() + 3;

		// Deployment
		d_depOrder = new DeployOrder(d_p1, l_source1, d_p1.getD_reinforcementPool());
		d_depOrder.execute(d_gameEngine);

		// when trying to airlift more armies
		d_airLift = new AirliftOrder(d_p1, l_source1, l_target, l_armies);
		d_airLift.execute(d_gameEngine);

		// then
		assertEquals(l_expectedArmies, d_airLift.getD_armies());
	}

}
