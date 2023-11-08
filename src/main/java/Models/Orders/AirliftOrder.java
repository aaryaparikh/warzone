package Models.Orders;

import Controller.GameEngine;
import Models.Country;
import Models.Player;

/**
 * Players can airlift reinforcements to another country.
 *
 * @author YURUI
 */
public class AirliftOrder extends Order {
	private Player d_player;
	private Country d_resourceCountry;
	private Country d_targetCountry;
	private int d_armies;

	/**
	 * Constructor to airlift order
	 *
	 * @param p_player        player who is committing airlift command
	 * @param p_sourceCountry country from which the reinforcements are to be taken
	 * @param p_targetCountry country to which reinforcements are to be taken
	 * @param p_armies        number of reinforcements
	 */
	public AirliftOrder(Player p_player, Country p_sourceCountry, Country p_targetCountry, int p_armies) {
		d_player = p_player;
		d_resourceCountry = p_sourceCountry;
		d_targetCountry = p_targetCountry;
		d_armies = p_armies;
	}

	/**
	 * Method to execute airlift command
	 *
	 * @param p_game gets the object of GameEngine class
	 * @return string according to the executed order
	 */
	@Override
	public String execute(GameEngine p_game) {
		// Check if the player controls the source country
		if (!d_player.getD_countries().contains(d_resourceCountry)) {
			return String.format(
					"Player \"%s\" does not control resource country \"%d\", hence armies cannot be airlifted.",
					d_player.getName(), d_resourceCountry.getCountryId());
		}

		// Check if there are enough armies in the source country
		if (d_resourceCountry.getArmies() < d_armies) {
			d_armies = d_resourceCountry.getArmies();
			// return String.format("Country \"%d\" does not have enough armies",
			// d_resourceCountry.getCountryId());
		}

		// Check if the target country is controlled by player
		if (!d_player.getD_countries().contains(d_targetCountry)) {
			return String.format(
					"Player \"%s\" does not control target country \"%d\", hence armies cannot be airlifted.",
					d_player.getName(), d_targetCountry.getCountryId());
		}

		// Airlift armies from source to target country
		d_resourceCountry.subtractArmies(d_armies);
		d_targetCountry.addArmies(d_armies);
		return String.format("Armies successfully airlifted from country \"%d\" to country \"%d\"",
				d_resourceCountry.getCountryId(), d_targetCountry.getCountryId());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getOrderType() {
		return "Airlift";
	}

	/**
	 * Return d_armies for testing purpose
	 *
	 * @return d_armies
	 */
	public int getD_armies() {
		return d_armies;
	}
}
