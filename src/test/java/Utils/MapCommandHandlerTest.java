package Utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.GameEngine;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit 5 test class for MapCommandHandler class.
 * 
 * @author YURUI
 */
public class MapCommandHandlerTest {

	private MapCommandHandler d_mapCommandHandler;
	private GameEngine d_gameEngine;

	/**
	 * Set up test
	 */
	@BeforeEach
	public void setUp() {
		this.d_gameEngine = new GameEngine(null);
		this.d_mapCommandHandler = new MapCommandHandler(d_gameEngine);
	}

	/**
	 * Test refactoring about Pull Up Map Field.
	 */
	@Test
	public void testPullUpMapField() {
		assertEquals(d_gameEngine, d_mapCommandHandler.d_gameEngine);
	}
	
}
