package Models.Orders;

import java.util.List;
import Controller.GameEngine;
import Models.Country;
import Models.Player;

/**
 * Players can advance reinforcements to another country.
 * 
 * @author YURUI
 */
public class AdvanceOrder extends Order {
	private Player d_player;
	private Country d_resourceCountry;
	private Country d_targetCountry;
	private int d_armies;

	/**
	 * Constructor to assign initial values
	 * 
	 * @param p_player            player who is committing advance command
	 * @param p_sourceCountry country from which the reinforcements are to be
	 *                            taken
	 * @param p_targetCountry country to which reinforcements are to be advanced
	 * @param p_armies            number of reinforcements
	 */
	public AdvanceOrder(Player p_player, Country p_sourceCountry, Country p_targetCountry, int p_armies) {
		d_player = p_player;
		d_resourceCountry = p_sourceCountry;
		d_targetCountry = p_targetCountry;
		d_armies = p_armies;
	}

	/**
	 * Method to execute advance command
	 * 
	 * @param p_game gets the object of GameEngine class
	 * @return string according to the executed order
	 */
	@Override
	public String execute(GameEngine p_game) {
		Country l_resourceCountry = null, l_targetCountry = null;
		for (Country l_country : p_game.getGameMap().getCountries()) {
			if (l_country == d_resourceCountry)
				l_resourceCountry = l_country;
			if (l_country == d_targetCountry)
				l_targetCountry = l_country;
		}

		// Step 1: Check if the resource country exists
		if (l_resourceCountry == null)
			return String.format("Country \"%d\" does not exist", d_resourceCountry.getCountryId());

		// Step 2: Check if the target country exists
		if (l_targetCountry == null)
			return String.format("Country \"%d\" does not exist", d_targetCountry.getCountryId());

		// Step 3: Check if the player controls the resource country
		if (!d_player.getD_countries().contains(l_resourceCountry)) {
			return String.format("Player \"%s\" does not control country \"%d\", hence armies cannot be moved.",
					d_player.getName(), d_resourceCountry.getCountryId());
		}

		// Step 4: Check if there are enough armies in the resource country
		if (l_resourceCountry.getArmies() < d_armies) {
			return String.format("Country \"%d\" does not have enough armies", d_resourceCountry.getCountryId());
		}

		// Step 5: Check if the target country is a neighbor of the resource country
		List<Integer> l_neighboringCountries = l_resourceCountry.getNeighborCountries();
		if (!l_neighboringCountries.contains(d_targetCountry.getCountryId())) {
			return String.format(
					"Armies cannot be moved from country \"%d\" to country \"%d\" because they are not neighbors",
					d_resourceCountry.getCountryId(), d_targetCountry.getCountryId());
		}

		// Step 6: Advance armies from resource to target country
		if (d_player.getD_countries().contains(l_resourceCountry)
				&& d_player.getD_countries().contains(l_targetCountry)) {
			l_resourceCountry.subtractArmies(d_armies);
			l_targetCountry.addArmies(d_armies);
			return String.format("Armies successfully moved from country \"%d\" to country \"%d\"",
					d_resourceCountry.getCountryId(), d_targetCountry.getCountryId());
		}

		// Step 7: Determine the capabilities of source and destination countries
		int l_sourceCountryArmies = l_resourceCountry.getArmies();
		int l_destinationCountryArmies = l_targetCountry.getArmies();

		int l_capabilitySourceCountryArmies = (int) Math.ceil(d_armies * 0.6);
		int l_capabilityDestinationCountryArmies = (int) Math.ceil(l_destinationCountryArmies * 0.7);

		// Step 8: Handle the scenario where the source country wins
		if (l_capabilitySourceCountryArmies > l_destinationCountryArmies) {
			Player l_playerBeingAttacked = l_targetCountry.getOwner();

			d_targetCountry.setOwner(d_player);
			d_player.addCountry(l_targetCountry);

			l_playerBeingAttacked.getD_countries().remove(l_targetCountry);
			l_resourceCountry.setArmies(l_sourceCountryArmies - d_armies);
			l_targetCountry.setArmies(d_armies - l_capabilityDestinationCountryArmies);
			return String.format(
					"Armies successfully moved from country \"%d\" to country \"%d\" and the ownership changed to \"%s\" player.",
					d_resourceCountry.getCountryId(), d_targetCountry.getCountryId(), d_player.getName());

		// Step 9: Handle the scenario where the source country and destination country are evenly matched
		} else if (l_capabilitySourceCountryArmies == l_destinationCountryArmies) {
			l_resourceCountry.setArmies(l_sourceCountryArmies - l_capabilityDestinationCountryArmies);
			l_targetCountry.setArmies(0);
			return String.format(
					"Armies from country \"%d\" were not able to advance to country \"%d\" as the attacking armies were only able to defeat the exact number of armies present in the defending country",
					d_resourceCountry.getCountryId(), d_targetCountry.getCountryId(), d_player.getName());

		// Step 10: Handle the scenario where the destination country wins
		} else {
			l_resourceCountry.setArmies(l_sourceCountryArmies - d_armies);
			l_targetCountry.subtractArmies(l_capabilitySourceCountryArmies);
			return String.format(
					"Armies from country \"%d\" were not able to advance to country \"%d\" \n as the attacking armies could not defeat all the armies present in the defending country",
					d_resourceCountry.getCountryId(), d_targetCountry.getCountryId(), d_player.getName());
		}
	}
}
