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

public class DeployOrderTest {

	public static DeployOrder d_depOrder;
	public static GameMap d_map;
	public static MapEditor d_lmap;
	public static Player d_player;
	public static GameEngine d_gameEngine;

	@BeforeEach
	public void setUp() {
//		d_gameMap = new GameMap();
//		defaultGameMap(d_gameMap);
//		d_gameEngine = new GameEngine(d_gameMap);
//		d_player = new Player("Dev", d_gameEngine);
//		d_country = new Country(1, 1);
//		d_player.addCountry(d_country);
//		d_armies = 3;
//		d_depOrder = new DeployOrder(d_player, d_country, d_armies);

		d_map = new GameMap();
		d_lmap = new MapEditor(d_map);
		d_map = d_lmap.loadMap("right");
		d_gameEngine = new GameEngine(d_map);
		d_player = new Player("Dev", d_gameEngine);
		d_player.addCountry(new Country(1, 1));
		d_gameEngine.addPlayer(d_player);
		d_depOrder = new DeployOrder(d_player, new Country(1, 1), 5);
	}

	@AfterEach
	public void tearDown() {
		d_lmap = null;
		d_map = null;
		d_player = null;
		d_depOrder = null;
	}

	@Test
	public void should_HaveProperPlayerName() {
		// given
		String l_expectedString = "Dev";

		// when
		String l_returnedString = d_depOrder.getPlayer().getName();

		// then
		assertEquals(l_expectedString, l_returnedString);
	}

}
