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
 * JUnit 5 test class for player class.
 *
 * @author Dev
 */
public class OrderTest {

	private DeployOrder d_depOrder;
	private GameMap d_map;
	private MapEditor d_lmap;
	private Player d_player;
	private GameEngine d_gameEngine;

	/**
	 * Set up
	 */
	@BeforeEach
	public void setUp() {
		d_map = new GameMap();
		d_lmap = new MapEditor(d_map);
		d_map = d_lmap.loadMap("right");
		d_gameEngine = new GameEngine(d_map);
		d_player = new Player("Dev", d_gameEngine);
		d_player.addCountry(new Country(1, 1));
		d_depOrder = new DeployOrder(d_player, new Country(1, 1), 5);

	}

	/**
	 * Tear Down
	 */
	@AfterEach
	public void tearDown() {
		d_lmap = null;
		d_map = null;
		d_player = null;
		d_depOrder = null;
	}

	/**
	 * checks whether the Player's army is successfully deployed
	 */
	@Test
	public void should_DeployPlayersArmy() {
		// given
		String l_expectedString = "Player \"Dev\" deployed \"5\" armies to country \"1\"";

		// when
		String l_returnedString = d_depOrder.execute(d_gameEngine);

		// then
		assertEquals(l_expectedString, l_returnedString);
	}

}
