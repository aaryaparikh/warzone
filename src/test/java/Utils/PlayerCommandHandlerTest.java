package Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.GameEngine;

/**
 * JUnit 5 test class for PlayerCommandHandler class.
 *
 * @author YURUI
 */
public class PlayerCommandHandlerTest {

	/**
	 * PlayerCommandHandler instance
	 */
	private PlayerCommandHandler d_playerCommandHandler;

	/**
	 * GameEngine instance
	 */
	private GameEngine d_gameEngine;

	/**
	 * Set up test
	 */
	@BeforeEach
	public void setUp() {
		this.d_gameEngine = new GameEngine(null);
		this.d_playerCommandHandler = new PlayerCommandHandler(d_gameEngine);
	}

	/**
	 * Test refactoring about Pull Up Player Field.
	 */
	@Test
	public void testPullUpMapField() {
		assertEquals(d_gameEngine, d_playerCommandHandler.d_gameEngine);
	}

}
