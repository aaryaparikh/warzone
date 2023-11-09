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
import Utils.MapEditor;

/**
 * JUnit 5 test class for calculating reinforcement integrity.
 *
 * @author Dev
 */
public class ReinforcementArmiesTest {

	public GameMap d_map;
	public MapEditor d_lmap;
	public Player d_p1;
	public Player d_p2;
	public Player d_p3;
	public GameEngine d_gameEngine;
	public DeployOrder d_deployOrder;
	public AirliftOrder d_airliftOrder;
	public AdvanceOrder d_advanceOrder;
	public BlockadeOrder d_blockadeOrder;
	public DiplomacyOrder d_diplomacyOrder;

	/**
	 * created default Map
	 *
	 * @param p_gameMap
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

	public Country getSource(Player p_player, int index) {
		return p_player.getD_countries().get(index);
	}

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

//	public Country getNeighbor(int p_countryID) {
//
//	}

	public void showInfo(Player p_player) {
		p_player.getD_countries().stream().forEach(
				ele -> System.out.println(p_player.getName() + " have country => " + ele.getCountryId() + "\t "));
	}

	public void deploy(Player p_player, Country p_country, int p_armies) {
		d_deployOrder = new DeployOrder(p_player, p_player.getD_countries().get(0), p_player.getD_reinforcementPool());
		d_deployOrder.execute(d_gameEngine);
	}

	public void deploy() {
		deploy(d_p1, d_p1.getD_countries().get(0), d_p1.getD_reinforcementPool());
		deploy(d_p2, d_p2.getD_countries().get(0), d_p2.getD_reinforcementPool());
		deploy(d_p3, d_p3.getD_countries().get(0), d_p3.getD_reinforcementPool());
	}

	public void advance(Player p_player, Country p_sourceCountry, Country p_targetCountry, int p_armies) {
		d_advanceOrder = new AdvanceOrder(p_player, p_sourceCountry, p_targetCountry, p_armies);
		System.out.println(d_advanceOrder.execute(d_gameEngine));
	}

	public void airlift(Player p_player, Country p_sourceCountry, Country p_targetCountry, int p_armies) {

	}

	public void blockade(Player p_player, Country p_targetCountry) {

	}

	public void bomb(Player p_player, Country p_targetCountry) {

	}

	public void negotiate(Player p_player, Player p_targetPlayer) {

	}

	@Test
	public void CheckReinforcementOfPlayers() {
		assertAll(() -> assertEquals(getExpectedReinforcementValue(d_p1), d_p1.getD_reinforcementPool()),
				() -> assertEquals(getExpectedReinforcementValue(d_p2), d_p2.getD_reinforcementPool()),
				() -> assertEquals(getExpectedReinforcementValue(d_p3), d_p3.getD_reinforcementPool()));
	}

	@Test
	public void checkIntegretyAfterDeploy() {

		int l_totalArmies = getExpectedReinforcementValue(d_p1) + getExpectedReinforcementValue(d_p2)
				+ getExpectedReinforcementValue(d_p3);

		deploy();

		int l_returnedArmies = d_p1.getD_countries().get(0).getArmies() + d_p2.getD_countries().get(0).getArmies()
				+ d_p3.getD_countries().get(0).getArmies();

		assertEquals(l_totalArmies, l_returnedArmies);
	}

	
	// To be completed
	
	
	/*
	@Test
	public void checkIntegrityAfterAdvance() {
//		deploy();
//
//		System.out.println(d_p1.getD_reinforcementPool() + "" + getSource(d_p1, 0).getArmies());
//		System.out.println(d_p2.getD_reinforcementPool() + "" + getSource(d_p2, 0).getArmies());
//
//		System.out.println(d_p1.getD_countries().get(0).getNeighborCountries());
//		System.out.println(d_p2.getD_countries().get(0).getNeighborCountries());
//		System.out.println(d_p3.getD_countries().get(0).getNeighborCountries());
//		if (d_p1.getD_countries().get(0).getNeighborCountries().size() > 1) {
//			advance(d_p1, getSource(d_p1, 0), getNeighbor(d_p1.getD_countries().get(0).getNeighborCountries().get(0)),
//					d_p1.getD_reinforcementPool());
//		}

//		System.out.println(d_p1.getD_reinforcementPool() + "" + getSource(d_p1, 0).getArmies());
//		System.out.println(d_p2.getD_reinforcementPool() + "" + getSource(d_p2, 0).getArmies());
	}*/

}
