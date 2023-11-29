package Controller;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Models.GameMap;

/**
 * JUnit 5 test class for GameEngine class.
 *
 * @author YURUI
 */
public class GameEngineTest {

	/**
	 * InputStream instance
	 */
	private final InputStream d_originalSystemIn = System.in;

	/**
	 * PrintStream instance
	 */
	private final PrintStream d_originalSystemOut = System.out;

	/**
	 * ByteArrayOutputStream instance
	 */
	private final ByteArrayOutputStream d_systemOutContent = new ByteArrayOutputStream();

	/**
	 * GameEngine instance
	 */
	private GameEngine d_gameEngine;

	/**
	 * GameMap instance
	 */
	private GameMap d_map;

	/**
	 * Set up output stream
	 */
	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(d_systemOutContent));
		this.d_map = new GameMap();
		d_map.addContinent(1, 3);
		d_map.addCountry(1, 1);
		d_map.addCountry(2, 1);
		d_map.addNeighbor(1, 2);
		d_map.addNeighbor(2, 1);
	}

	/**
	 * Set down input and output stream
	 */
	@AfterEach
	public void tearDown() {
		System.setIn(d_originalSystemIn);
		System.setOut(d_originalSystemOut);
	}

	/**
	 * Test GameEngine issue orders when refactoring.
	 */
	@Test
	public void testGameEngineIssueOrders() {
		// Simulate user input
		String l_simulatedInput = "next" + System.lineSeparator() + "gameplayer -add Yurui" + System.lineSeparator()
				+ "\n" + System.lineSeparator() + "assigncountries" + System.lineSeparator() + "next"
				+ System.lineSeparator() + "end" + System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(l_simulatedInput.getBytes()));

		this.d_gameEngine = new GameEngine(d_map);
		d_gameEngine.start();

		assertTrue(d_systemOutContent.toString().contains("Yurui's turn"));
	}

	/**
	 * Test GameEngine execute orders when refactoring.
	 */
	@Test
	public void testGameEngineExecuteOrders() {
		// Simulate user input
		String l_simulatedInput = "next" + System.lineSeparator() + "showmap" + System.lineSeparator()
				+ "gameplayer -add Yurui" + System.lineSeparator() + "\n" + System.lineSeparator() + "assigncountries"
				+ System.lineSeparator() + "next" + System.lineSeparator() + "deploy 1 3" + System.lineSeparator()
				+ "commit" + System.lineSeparator() + "end" + System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(l_simulatedInput.getBytes()));

		this.d_gameEngine = new GameEngine(d_map);
		d_gameEngine.start();

		assertTrue(d_systemOutContent.toString()
				.contains("Player \"Yurui\" deployed \"3\" armies to country \"1\"\r\n" + ""));
	}

	/**
	 * Test Map Service perform the same when refactoring.
	 */
	@Test
	public void testMapService() {
		// Simulate user input
		String simulatedInput = "editmap test.map" + System.lineSeparator() + "showmap" + System.lineSeparator()
				+ "next" + System.lineSeparator() + "gameplayer -add Yurui" + System.lineSeparator() + "\n"
				+ System.lineSeparator() + "assigncountries" + System.lineSeparator() + "next" + System.lineSeparator()
				+ "end" + System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		this.d_gameEngine = new GameEngine(d_map);
		d_gameEngine.start();

		assertTrue(d_systemOutContent.toString()
				.contains("	[Continents]\r\n" + "	Id	Value\r\n" + "	1	5\r\n" + "	2	5"));
	}
}
