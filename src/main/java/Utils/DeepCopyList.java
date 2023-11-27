package Utils;

import java.util.ArrayList;
import java.util.List;

import Controller.GameEngine;
import Models.Country;
import Models.Player;

/**
 * Utility class for performing deep copy of lists containing Country and Player objects.
 */
public class DeepCopyList {

    /**
     * Performs a deep copy of a list of Country objects.
     *
     * @param p_countryList The original list of Country objects to be copied.
     * @return A new list containing deep copies of the Country objects from the original list.
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
     * Performs a deep copy of a list of Player objects.
     *
     * @param p_playerList The original list of Player objects to be copied.
     * @param p_gameEngine The GameEngine instance associated with the players.
     * @return A new list containing deep copies of the Player objects from the original list.
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
