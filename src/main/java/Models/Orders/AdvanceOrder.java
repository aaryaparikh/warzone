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
	 * @param p_player        player who is committing advance command
	 * @param p_sourceCountry country from which the reinforcements are to be taken
	 * @param p_targetCountry country to which reinforcements are to be advanced
	 * @param p_armies        number of reinforcements
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
		// Check if the player controls the resource country
		if (!d_player.getD_countries().contains(d_resourceCountry)) {
			return String.format("Player \"%s\" does not control country \"%d\", hence armies cannot be moved.",
					d_player.getName(), d_resourceCountry.getCountryId());
		}

		// Check if there are enough armies in the resource country
		if (d_resourceCountry.getArmies() < d_armies) {
			return String.format("Country \"%d\" does not have enough armies", d_resourceCountry.getCountryId());
		}

		// Check if the target country is a neighbor of the resource country
		List<Integer> l_neighborCountries = d_resourceCountry.getNeighborCountries();
		if (!l_neighborCountries.contains(d_targetCountry.getCountryId())) {
			return String.format(
					"Armies cannot be moved from country \"%d\" to country \"%d\" because they are not neighbors",
					d_resourceCountry.getCountryId(), d_targetCountry.getCountryId());
		}

		// Advance armies from resource to target country
		if (d_player.getD_countries().contains(d_resourceCountry)
				&& d_player.getD_countries().contains(d_targetCountry)) {
			d_resourceCountry.subtractArmies(d_armies);
			d_targetCountry.addArmies(d_armies);
			return String.format("Armies successfully moved from country \"%d\" to country \"%d\"",
					d_resourceCountry.getCountryId(), d_targetCountry.getCountryId());
		}

		// Check if the player has negotiated with player who owns target country.
		if (d_player.getNegotiatedPlayers().contains(d_targetCountry.getOwner())) {
			return String.format("Can't attack, there is a negotiation between \"%s\" and \"%s\".", d_player.getName(),
					d_targetCountry.getOwner());
		}

		// Determine the capabilities of source and destination countries
		int l_sourceCountryArmies = d_resourceCountry.getArmies();
		int l_targetCountryArmies = d_targetCountry.getArmies();

		int l_capabilitySourceCountryArmies = (int) Math.ceil(d_armies * 0.6);
		int l_capabilityTargetCountryArmies = (int) Math.ceil(l_targetCountryArmies * 0.7);

		// Handle the attack where the source country wins
		if (l_capabilitySourceCountryArmies > l_targetCountryArmies) {
			Player l_playerBeingAttacked = d_targetCountry.getOwner();

			d_targetCountry.setOwner(d_player);
			d_player.addCountry(d_targetCountry);

			// Check if target country is neutral
			if (l_playerBeingAttacked != null)
				l_playerBeingAttacked.getD_countries().remove(d_targetCountry);

			d_resourceCountry.setArmies(l_sourceCountryArmies - d_armies);
			d_targetCountry.setArmies(d_armies - l_capabilityTargetCountryArmies);

			d_player.addCardsOwned("random");
			return String.format(
					"Armies successfully moved from country \"%d\" to country \"%d\" and the ownership changed to \"%s\" player.",
					d_resourceCountry.getCountryId(), d_targetCountry.getCountryId(), d_player.getName());

			// Handle the attack where the source country and destination country are evenly
			// matched
		} else if (l_capabilitySourceCountryArmies == l_targetCountryArmies) {
			d_resourceCountry.setArmies(l_sourceCountryArmies - l_capabilityTargetCountryArmies);
			d_targetCountry.setArmies(0);
			return String.format(
					"Armies from country \"%d\" were not able to advance to country \"%d\" as the attacking armies were only able to defeat the exact number of armies in target country",
					d_resourceCountry.getCountryId(), d_targetCountry.getCountryId(), d_player.getName());

			// Handle the attack where the destination country wins
		} else {
			d_resourceCountry.setArmies(l_sourceCountryArmies - d_armies);
			d_targetCountry.subtractArmies(l_capabilitySourceCountryArmies);
			return String.format(
					"Armies from country \"%d\" were not able to advance to country \"%d\" \n as the attacking armies could not defeat all the armies in target country",
					d_resourceCountry.getCountryId(), d_targetCountry.getCountryId(), d_player.getName());
		}
	}
}
