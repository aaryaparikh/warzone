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

class OrderTest {

	private DeployOrder t_depOrder;
	private GameMap t_map;
	private MapEditor t_lmap;
	private Player t_player;
	private GameEngine t_gameEngine;

	@BeforeEach
	public void setUp() {
		t_map = new GameMap();
		t_lmap = new MapEditor(t_map);
		t_map = t_lmap.loadMap("right");
		t_gameEngine = new GameEngine(t_map);
		t_player = new Player("Dev", t_gameEngine);
		t_player.addCountry(new Country(1, 1));
		t_depOrder = new DeployOrder(t_player, new Country(1, 1), 5);

	}

	@AfterEach
	public void tearDown() {
		t_lmap = null;
		t_map = null;
		t_player = null;
		t_depOrder = null;
	}

	@Test
	public void should_DeployPlayersArmy() {
		// given
		String l_expectedString = "Player \"Dev\" deployed \"5\" armies to country \"1\"";

		// when
		String l_returnedString = t_depOrder.execute(t_gameEngine);

		// then
		assertEquals(l_expectedString, l_returnedString);
	}

}
