package Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Models.*;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * JUnit 5 test class for GameEngine class.
 * 
 * @author YURUI
 */
public class GameEngineTest {

	private final InputStream d_originalSystemIn = System.in;
	private final PrintStream d_originalSystemOut = System.out;
	private final ByteArrayOutputStream d_systemOutContent = new ByteArrayOutputStream();
	private GameEngine d_gameEngine;
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
		String simulatedInput = "next" + System.lineSeparator() + "gameplayer -add Yurui" + System.lineSeparator()
				+ "assigncountries" + System.lineSeparator() + "next" + System.lineSeparator() + "end"
				+ System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

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
		String simulatedInput = "next" + System.lineSeparator() + "showmap" + System.lineSeparator()
				+ "gameplayer -add Yurui" + System.lineSeparator() + "assigncountries" + System.lineSeparator() + "next"
				+ System.lineSeparator() + "deploy 1 3" + System.lineSeparator() + "commit" + System.lineSeparator()
				+ "end" + System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

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
		String simulatedInput = "editmap temp" + System.lineSeparator() + "showmap" + System.lineSeparator() + "next"
				+ System.lineSeparator() + "gameplayer -add Yurui" + System.lineSeparator() + "assigncountries"
				+ System.lineSeparator() + "next" + System.lineSeparator() + "end" + System.lineSeparator() + "end"
				+ System.lineSeparator();
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		this.d_gameEngine = new GameEngine(d_map);
		d_gameEngine.start();

		assertTrue(d_systemOutContent.toString()
				.contains("	[Continents]\r\n" + "	Id	Value\r\n" + "	1	3\r\n" + "	2	5"));
	}
}
