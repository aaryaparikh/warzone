package StartupPhase;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.GameEngine;
import Controller.Phases.SubPhases.PlaySetupPhase;
import Models.GameMap;

/**
 * Junit 5 class for testing the smooth functionality of startup phase
 *
 * @author Virag
 */
public class StartupPhaseTest {

	/**
	 * GameMap instance
	 */
	private static GameMap d_map;

	/**
	 * GameEngine instance
	 */
	private static GameEngine d_gameEngine;

	/**
	 * Set Default map
	 * 
	 * @param p_gameMap game map
	 */
	private void defaultGameMap(GameMap p_gameMap) {
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

	/**
	 * Setup method to set the default map
	 */
	@BeforeEach
	public void setup() {
		d_map = new GameMap();
		defaultGameMap(d_map);
		d_gameEngine = new GameEngine(d_map);
	}

	/**
	 * Testing the deploy command functionality
	 */
	@Test
	public void shouldDeploy() {
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = { "deploy" };
		assertNull(l_playsetupPhase.deploy(l_commands, null));

	}

	/**
	 * Testing the advance command functionality
	 */
	@Test
	public void shouldAdvance() {
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = { "advance" };
		assertNull(l_playsetupPhase.advance(l_commands, null));

	}

	/**
	 * Testing the bomb command functionality
	 */
	@Test
	public void shouldBomb() {
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = { "bomb" };
		assertNull(l_playsetupPhase.bomb(l_commands, null));

	}

	/**
	 * Testing the blockade command functionality
	 */
	@Test
	public void shouldBlockade() {
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = { "blockade" };
		assertNull(l_playsetupPhase.blockade(l_commands, null));

	}

	/**
	 * Testing the airlift command functionality
	 */
	@Test
	public void shouldAirlift() {
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = { "airlift" };
		assertNull(l_playsetupPhase.airlift(l_commands, null));

	}

	/**
	 * Testing the Negotiate command functionality
	 */
	@Test
	public void shouldNegotiate() {
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = { "negotiate" };
		assertNull(l_playsetupPhase.negotiate(l_commands, null));

	}

	/**
	 * Testing the game commit command functionality
	 */
	@Test
	public void shouldCommit() {
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = { "commit" };
		assertNull(l_playsetupPhase.commit(l_commands, null));

	}
}
