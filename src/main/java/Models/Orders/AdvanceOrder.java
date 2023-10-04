package Models.Orders;

import java.util.List;
import Controller.GameEngine;
import Models.Player;

/**
 * Players can advance reinforcements to another country.
 */
public class AdvanceOrder extends Order {
	private Player d_player;
	private int d_resourceCountryName;
	private int d_targetCountryName;
	private int d_armies;

	/**
	 * Constructor to assign initial values
	 * 
	 * @param p_player          player who is committing advance command
	 * @param p_countryNameFrom country from which the reinforcements are to be
	 *                          taken
	 * @param p_countryNameTo   country to which reinforcements are to be advanced
	 * @param p_armies          number of reinforcements
	 */
	public AdvanceOrder(Player p_player, int p_sourceCountryName, int p_targetCountryName, int p_armies) {
		d_player = p_player;
		d_resourceCountryName = p_sourceCountryName;
		d_targetCountryName = p_targetCountryName;
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
		// Step 1: Check if the resource country exists
		if (!p_game.getGameMap().getCountries().contains(d_resourceCountryName))
			return String.format("Country \"%d\" does not exist", d_resourceCountryName);

		// Step 2: Check if the target country exists
		if (p_game.getGameMap().getCountries().get(d_targetCountryName) == null)
			return String.format("Country \"%d\" does not exist", d_targetCountryName);

		// Step 3: Check if the player controls the resource country
		if (!d_player.getD_countries().contains(d_resourceCountryName)) {
			return String.format("Player \"%s\" does not control country \"%d\", hence armies cannot be moved.",
					d_player.getName(), d_resourceCountryName);
		}

		// Step 4: Check if there are enough armies in the resource country
		if (d_player.getD_countries().get(d_resourceCountryName).getArmies() < d_armies) {
			return String.format("Country \"%d\" does not have enough armies", d_resourceCountryName);
		}

		// Step 5: Ensure that at least one army remains in the resource country
		if ((d_player.getD_countries().get(d_resourceCountryName).getArmies() - d_armies) < 1) {
			return String.format("Country \"%d\" should remain with at least 1 army after advancing command",
					d_resourceCountryName);
		}

		// Step 6: Check if the target country is a neighbor of the resource country
		List<Integer> l_neighboringCountries = d_player.getD_countries().get(d_resourceCountryName)
				.getNeighborCountries();
		if (!l_neighboringCountries.contains(d_targetCountryName)) {
			return String.format(
					"Armies cannot be moved from country \"%d\" to country \"%d\" because they are not neighbors",
					d_resourceCountryName, d_targetCountryName);
		}

		// Step 7: Advance armies from resource to target country
		if (d_player.getD_countries().contains(d_resourceCountryName)
				&& d_player.getD_countries().contains(d_targetCountryName)) {
			d_player.getD_countries().get(d_resourceCountryName).subtractArmies(d_armies);
			d_player.getD_countries().get(d_targetCountryName).addArmies(d_armies);
			return String.format("Armies successfully moved from country \"%d\" to country \"%d\"",
					d_resourceCountryName, d_targetCountryName);
		}

		// Step 8: Determine the capabilities of source and destination countries
		int l_sourceCountryArmies = p_game.getGameMap().getCountries().get(d_resourceCountryName).getArmies();
		int l_destinationCountryArmies = p_game.getGameMap().getCountries().get(d_targetCountryName).getArmies();

		int l_capabilitySourceCountryArmies = (int) Math.ceil(d_armies * 0.6);
		int l_capabilityDestinationCountryArmies = (int) Math.ceil(l_destinationCountryArmies * 0.7);

		// Step 9: Handle the scenario where the source country wins
		if (l_capabilitySourceCountryArmies > l_destinationCountryArmies) {
			Player l_playerBeingAttacked = p_game.getGameMap().getCountries().get(d_targetCountryName).getOwner();

			p_game.getGameMap().getCountries().get(d_targetCountryName).setOwner(d_player);
			d_player.addCountry(p_game.getGameMap().getCountries().get(d_targetCountryName));

			l_playerBeingAttacked.getD_countries().remove(d_targetCountryName);
			d_player.getD_countries().get(d_resourceCountryName).setArmies(l_sourceCountryArmies - d_armies);
			d_player.getD_countries().get(d_targetCountryName)
					.setArmies(d_armies - l_capabilityDestinationCountryArmies);
			return String.format(
					"Armies successfully moved from country \"%d\" to country \"%d\" and the ownership changed to \"%s\" player.",
					d_resourceCountryName, d_targetCountryName, d_player.getName());

			// Step 10: Handle the scenario where the source country and destination country
			// are evenly matched
		} else if (l_capabilitySourceCountryArmies == l_destinationCountryArmies) {
			d_player.getD_countries().get(d_resourceCountryName)
					.setArmies(l_sourceCountryArmies - l_capabilityDestinationCountryArmies);
			p_game.getGameMap().getCountries().get(d_targetCountryName).setArmies(0);
			return String.format(
					"Armies from country \"%d\" were not able to advance to country \"%d\" as the attacking armies were only able to defeat the exact number of armies present in the defending country",
					d_resourceCountryName, d_targetCountryName, d_player.getName());

			// Step 11: Handle the scenario where the destination country wins
		} else {
			d_player.getD_countries().get(d_resourceCountryName).setArmies(l_sourceCountryArmies - d_armies);
			p_game.getGameMap().getCountries().get(d_targetCountryName).subtractArmies(l_capabilitySourceCountryArmies);
			return String.format(
					"Armies from country \"%d\" were not able to advance to country \"%d\" as the attacking armies could not defeat all the armies present in the defending country",
					d_resourceCountryName, d_targetCountryName, d_player.getName());
		}
	}
}
