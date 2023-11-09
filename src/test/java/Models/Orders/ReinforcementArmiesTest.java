package Models.Orders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Constants.GameConstants;
import Controller.GameEngine;
import Models.Continent;
import Models.Country;
import Models.GameMap;
import Models.Player;

/**
 * JUnit 5 test class for calculating reinforcement integrity.
 *
 * @author Dev
 */
public class ReinforcementArmiesTest {

	private GameMap d_map;
	private Player d_p1;
	private Player d_p2;
	private Player d_p3;
	private GameEngine d_gameEngine;
	private DeployOrder d_deployOrder;
	private AdvanceOrder d_advanceOrder;
	private AirliftOrder d_airLiftOrder;
	private BlockadeOrder d_blockadeOrder;
	private BombOrder d_bombOrder;
	private DiplomacyOrder d_diplomacyOrder;

	/**
	 * created default Map
	 *
	 * @param p_gameMap game map
	 */
	public void defaultGameMap(GameMap p_gameMap) {
		p_gameMap.addContinent(1, 4);
		p_gameMap.addContinent(2, 5);
		p_gameMap.addContinent(3, 3);

		p_gameMap.addCountry(1, 1);
		p_gameMap.addCountry(2, 1);
		p_gameMap.addCountry(3, 2);
		p_gameMap.addCountry(4, 2);
		p_gameMap.addCountry(5, 3);

		p_gameMap.addNeighbor(1, 2);
		p_gameMap.addNeighbor(1, 3);
		p_gameMap.addNeighbor(1, 4);
		p_gameMap.addNeighbor(2, 1);
		p_gameMap.addNeighbor(2, 5);
		p_gameMap.addNeighbor(3, 1);
		p_gameMap.addNeighbor(3, 5);
		p_gameMap.addNeighbor(4, 1);
		p_gameMap.addNeighbor(4, 5);
		p_gameMap.addNeighbor(5, 2);
		p_gameMap.addNeighbor(5, 3);
		p_gameMap.addNeighbor(5, 4);
	}

	/**
	 * sets up some objects and initialize it with some predefined values
	 */
	@BeforeEach
	public void setup() {
		d_map = new GameMap();
		defaultGameMap(d_map);
		d_gameEngine = new GameEngine(d_map);
		d_p1 = new Player("Dev", d_gameEngine);
		d_p2 = new Player("Aarya", d_gameEngine);
		d_p3 = new Player("Yurui", d_gameEngine);
		d_gameEngine.addPlayer(d_p1);
		d_gameEngine.addPlayer(d_p3);
		d_gameEngine.addPlayer(d_p2);
		d_gameEngine.assignCountriesRandomly();
		d_gameEngine.assignReinforcements();
	}

	/**
	 * Retrieves a country from the player's list of countries based on its index.
	 *
	 * @param p_player The player from whom to retrieve the country.
	 * @param p_index    The index of the desired country in the player's list.
	 * @return The country at the specified index.
	 */
	public Country getSource(Player p_player, int p_index) {
		return p_player.getD_countries().get(p_index);
	}

	/**
	 * Calculates the expected reinforcement value for a player based on game rules.
	 *
	 * @param p_player The player for whom to calculate the reinforcement value.
	 * @return The expected reinforcement value based on the game rules.
	 */
	public int getExpectedReinforcementValue(Player p_player) {
		// Implement reinforcement calculation logic based on game rules
		int l_assignedArmies = p_player.getD_countries().size() / 3;

		for (Continent l_continent : d_map.getContinents()) {
			List<Country> l_countryList = new ArrayList<>();
			for (Country l_country : d_map.getCountries()) {
				if (l_country.getContinentId() == l_continent.getContinentId())
					l_countryList.add(l_country);
			}
			if (p_player.getD_countries().containsAll(l_countryList))
				l_assignedArmies += l_continent.getContinentValue();
		}

		if (l_assignedArmies > GameConstants.MINIMUN_PLAYER_REINFORCEMENT)
			return l_assignedArmies;
		else
			return GameConstants.MINIMUN_PLAYER_REINFORCEMENT;
	}

	/**
	 * Displays information about the countries owned by a player.
	 *
	 * @param p_player The player for whom to show country information.
	 */
	public void showInfo(Player p_player) {
		p_player.getD_countries().stream().forEach(
				ele -> System.out.println(p_player.getName() + " have country => " + ele.getCountryId() + "\t "));
	}

	/**
	 * Initiates a deployment of armies by a player to a specific country.
	 *
	 * @param p_player   The player who is deploying armies.
	 * @param p_country  The country to which the armies are being deployed.
	 * @param p_armies   The number of armies being deployed.
	 */
	public void deploy(Player p_player, Country p_country, int p_armies) {
		d_deployOrder = new DeployOrder(p_player, p_player.getD_countries().get(0), p_player.getD_reinforcementPool());
		d_deployOrder.execute(d_gameEngine);
	}

	/**
	 * Runs Deploy Order
	 */
	public void deploy() {
		deploy(d_p1, d_p1.getD_countries().get(0), d_p1.getD_reinforcementPool());
		deploy(d_p2, d_p2.getD_countries().get(0), d_p2.getD_reinforcementPool());
		deploy(d_p3, d_p3.getD_countries().get(0), d_p3.getD_reinforcementPool());
	}

	/**
	 * Runs Advance Order
	 *
	 * @param p_player player for test
	 * @param p_sourceCountry source country in test
	 * @param p_targetCountry target country in test
	 * @param p_armies army number
	 */
	public void advance(Player p_player, Country p_sourceCountry, Country p_targetCountry, int p_armies) {
		d_advanceOrder = new AdvanceOrder(p_player, p_sourceCountry, p_targetCountry, p_armies);
		System.out.println(d_advanceOrder.execute(d_gameEngine));
	}

	/**
	 * Runs Airlift Order
	 *
	 * @param p_player player for test 
	 * @param p_sourceCountry source country in test
	 * @param p_targetCountry target country in test
	 * @param p_armies army number
	 */
	public void airlift(Player p_player, Country p_sourceCountry, Country p_targetCountry, int p_armies) {
		d_airLiftOrder = new AirliftOrder(p_player, p_sourceCountry, p_targetCountry, p_armies);
		d_airLiftOrder.execute(d_gameEngine);
	}

	/**
	 * Runs Blockade Order
	 *
	 * @param p_player player for test 
	 * @param p_targetCountry target country in test
	 */
	public void blockade(Player p_player, Country p_targetCountry) {
		d_blockadeOrder = new BlockadeOrder(p_player, p_targetCountry);
		d_blockadeOrder.execute(d_gameEngine);
	}

	/**
	 * Runs Bomb Order
	 *
	 * @param p_player player for test 
	 * @param p_targetCountry target country in test
	 */
	public void bomb(Player p_player, Country p_targetCountry) {
		d_bombOrder = new BombOrder(p_player, p_targetCountry);
		d_bombOrder.execute(d_gameEngine);
	}

	/**
	 * Runs Diplomacy Order
	 *
	 * @param p_player player for test 
	 * @param p_targetPlayer target country in test
	 */
	public void negotiate(Player p_player, Player p_targetPlayer) {
		d_diplomacyOrder = new DiplomacyOrder(p_player, p_targetPlayer);
		d_diplomacyOrder.execute(d_gameEngine);
	}

	/**
	 * Checks reinforcement of PLayers
	 */
	@Test
	public void CheckReinforcementOfPlayers() {
		assertAll(() -> assertEquals(getExpectedReinforcementValue(d_p1), d_p1.getD_reinforcementPool()),
				() -> assertEquals(getExpectedReinforcementValue(d_p2), d_p2.getD_reinforcementPool()),
				() -> assertEquals(getExpectedReinforcementValue(d_p3), d_p3.getD_reinforcementPool()));
	}

	/**
	 * Checks the integrity after Deploy
	 */
	@Test
	public void checkIntegretyAfterDeploy() {

		int l_totalArmies = getExpectedReinforcementValue(d_p1) + getExpectedReinforcementValue(d_p2)
				+ getExpectedReinforcementValue(d_p3);

		deploy();

		int l_returnedArmies = d_p1.getD_countries().get(0).getArmies() + d_p2.getD_countries().get(0).getArmies()
				+ d_p3.getD_countries().get(0).getArmies();

		assertEquals(l_totalArmies, l_returnedArmies);
	}
}
