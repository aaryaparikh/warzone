package Controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Models.Player;
import Models.Orders.DeployOrder;
import Models.*;
import Utils.MapCommandHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit 5 test class for ExecuteOrders class.
 * 
 * @author YURUI
 */
public class ExecuteOrdersTest {

	private ExecuteOrders d_executeOrders;
	private GameEngine d_gameEngine;
	private MapCommandHandler d_commandHandler;
	private GameMap d_gameMap;

	/**
	 * Set up test
	 */
	@BeforeEach
	public void setUp() {
		// Set up game map
		d_gameMap = new GameMap();
		d_gameMap.addContinent(1, 3);
		d_gameMap.addCountry(1, 1);

		d_gameEngine = new GameEngine(d_gameMap);
		d_commandHandler = Mockito.mock(MapCommandHandler.class);
		d_executeOrders = new ExecuteOrders(d_gameEngine, d_commandHandler);
	}

	/**
	 * Test the executeOrders method.
	 */
	@Test
	public void testExecuteOrders() {
		// Create a list of players
		List<Player> l_playerPool = new ArrayList<>();
		Player l_player1 = new Player("Player1", d_gameEngine);
		Player l_player2 = new Player("Player2", d_gameEngine);
		l_playerPool.add(l_player1);
		l_playerPool.add(l_player2);

		// Set up player orders
		Country l_country1 = new Country(1, 1);
		Country l_country2 = new Country(2, 1);
		l_player1.addOrder(new DeployOrder(l_player1, l_country1, 1));
		l_player2.addOrder(new DeployOrder(l_player2, l_country2, 1));

		// Call the executeOrders method
		String l_result = d_executeOrders.executeAllCommittedOrders();

		// Assert that the result is as expected
		assertEquals("nextRound", l_result);
	}
}
