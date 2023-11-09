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

	private final InputStream originalSystemIn = System.in;
	private final PrintStream originalSystemOut = System.out;
	private final ByteArrayOutputStream systemOutContent = new ByteArrayOutputStream();
	private GameEngine d_gameEngine;
	private GameMap d_map;

	/**
	 * Set up output stream
	 */
	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(systemOutContent));
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
		System.setIn(originalSystemIn);
		System.setOut(originalSystemOut);
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

		assertTrue(systemOutContent.toString().contains("Yurui's turn"));
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

		assertTrue(systemOutContent.toString()
				.contains("Player \"Yurui\" deployed \"3\" armies to country \"1\"\r\n" + ""));
	}
}
