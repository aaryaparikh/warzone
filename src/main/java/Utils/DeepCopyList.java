package Utils;

import java.util.ArrayList;
import java.util.List;

import Controller.GameEngine;
import Models.Country;
import Models.Player;

/**
 * Class the creates the Deep Copy of Country List and Player List
 *
 * @author Dev
 */
public class DeepCopyList {

	/**
	 * Create the deep copy of the Country List.
	 *
	 * @param p_countryList Accepts Country List as an argument.
	 * @return Returns the deep copy of list.
	 */
	public static List<Country> deepCopy(List<Country> p_countryList) {
		List<Country> l_newCountryList = new ArrayList<>();
		for (Country l_country : p_countryList) {
			Country l_newCountry = new Country(l_country.getCountryId(), l_country.getContinentId());
			l_newCountry.setArmies(l_country.getArmies());
			l_newCountry.setOwner(l_country.getOwner());
			l_newCountry.setNeighborCountries(l_country.getNeighborCountries());
			l_newCountryList.add(l_newCountry);
		}
		return l_newCountryList;
	}

	/**
	 * Create the deep copy of the Player List.
	 *
	 * @param p_playerList Accepts a list of Players as an argument.
	 * @param p_gameEngine Accepts an instance of gameengine.
	 * @return Returns a deep copy of players list.
	 */
	public static List<Player> deepCopy(List<Player> p_playerList, GameEngine p_gameEngine) {
		List<Player> l_newPlayerList = new ArrayList<>();
		for (Player l_player : p_playerList) {
			Player l_newPlayer = new Player(l_player.getName(), p_gameEngine);
			l_newPlayer.setD_strategy(l_player.getD_strategy());
			l_newPlayer.getD_strategy().d_player = l_newPlayer;
			l_newPlayer.setIfSignified(false);
			l_newPlayerList.add(l_newPlayer);
		}
		return l_newPlayerList;
	}
}
