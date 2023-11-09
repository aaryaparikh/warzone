package StartupPhase;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Controller.GameEngine;
import Controller.Phases.SubPhases.PlaySetupPhase;
import Models.Country;
import Models.GameMap;


/**
 * Junit 5 class for testing the smooth functionality of startup phase
 * 
 * @author Virag
 */
public class StartupPhaseTest {
	
	
	public static GameMap d_map;
	public static GameEngine d_gameEngine;

	public void defaultGameMap(GameMap p_gameMap) {
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
	
	
	@BeforeEach
	/**
	 * Setup method to set the default map
	 */
	public void setup()
	{
		d_map = new GameMap();
		defaultGameMap(d_map);
		d_gameEngine = new GameEngine(d_map);
	}
	
	@Test
	/**
	 * Testing the game player add command functionality
	 */
	public void testGameplayerAdd()
	{
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = {"gameplayer", "-add", "Virag"};
		l_playsetupPhase.gamePlayer(l_commands);
		assertEquals(1, d_gameEngine.getPlayers().size());
	}
	
	@Test
	/**
	 * Testing the assign countries command functionality
	 */
	public void testAssignCountries()
	{
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = {"gameplayer", "-add", "Virag"};
		l_playsetupPhase.gamePlayer(l_commands);
		String l_commands_assign[] = {"assigncountries"};
		l_playsetupPhase.assignCountries(l_commands_assign);
		for(Country c : d_gameEngine.getGameMap().getCountries())
		{
			assertTrue(c.getOwner().getName().equals("Virag"));
		}
		
	}
	
	@Test
	/**
	 * Testing the deploy command functionality
	 */
	public void testDeploy()
	{
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = {"deploy"};
		assertNull(l_playsetupPhase.deploy(l_commands, null));
		
	}
	
	@Test
	/**
	 * Testing the advance command functionality
	 */
	public void testAdvance()
	{
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = {"advance"};
		assertNull(l_playsetupPhase.advance(l_commands, null));
		
	}
	
	@Test
	/**
	 * Testing the bomb command functionality
	 */
	public void testBomb()
	{
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = {"bomb"};
		assertNull(l_playsetupPhase.bomb(l_commands, null));
		
	}
	
	@Test
	/**
	 * Testing the blockade command functionality
	 */
	public void testBlockade()
	{
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = {"blockade"};
		assertNull(l_playsetupPhase.blockade(l_commands, null));
		
	}
	
	@Test
	/**
	 * Testing the airlift command functionality
	 */
	public void testAirlift()
	{
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = {"airlift"};
		assertNull(l_playsetupPhase.airlift(l_commands, null));
		
	}
	
	@Test
	/**
	 * Testing the Negotiate command functionality
	 */
	public void testNegotiate()
	{
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = {"negotiate"};
		assertNull(l_playsetupPhase.negotiate(l_commands, null));
		
	}
	
	@Test
	/**
	 * Testing the game commit command functionality
	 */
	public void tesCommit()
	{
		PlaySetupPhase l_playsetupPhase = new PlaySetupPhase(d_gameEngine);
		d_gameEngine.setPhase(l_playsetupPhase);
		String l_commands[] = {"commit"};
		assertNull(l_playsetupPhase.commit(l_commands, null));
		
	}
	
	

}
