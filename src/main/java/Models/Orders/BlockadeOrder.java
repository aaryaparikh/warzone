package Models.Orders;

import Controller.GameEngine;
import Models.Country;
import Models.Player;

/**
 * Players blockade a country owned.
 *
 */
public class BlockadeOrder extends Order {
	/**
	 * Order giving player
	 */
	private Player d_player;

	/**
	 * Country where the blockade order is to be executed
	 */
	private Country d_targetCountry;

	/**
	 * Constructor to blockade order
	 *
	 * @param p_player        player who is committing deploy order
	 * @param p_targetCountry which country is blockaded
	 */
	public BlockadeOrder(Player p_player, Country p_targetCountry) {
		this.d_player = p_player;
		this.d_targetCountry = p_targetCountry;
	}

	/**
	 * Method to execute diplomacy order.
	 *
	 * @param p_game the object representing the game.
	 * @return a response string indicating the result of the order execution.
	 */
	@Override
	public String execute(GameEngine p_game) {

		// Check if the player controls the specified country.
		if (!d_player.getD_countries().contains(d_targetCountry))
			return (String.format("Player \"%s\" doesn't control target country \"%d\", can't blockade.",
					d_player.getName(), d_targetCountry.getCountryId()));

		// Execute blockade card order
		if (d_targetCountry.getArmies() > 0) {
			d_targetCountry.setArmies(d_targetCountry.getArmies() * 3);
			d_targetCountry.setOwner(null);

			d_player.getD_countries().remove(d_targetCountry);
			return String.format("Player \"%s\" blockade country \"%d\", it has \"%s\" armies and become neutral.",
					d_player.getName(), d_targetCountry.getCountryId(), d_targetCountry.getArmies());
		} else {
			d_targetCountry.setOwner(null);
			d_player.getD_countries().remove(d_targetCountry);
			return String.format("Player \"%s\" blockade a country \"%d\", but no army.", d_player.getName(),
					d_targetCountry.getCountryId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getOrderType() {
		return "Blockade";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getOrderInfo() {
		return String.format("%s blockade %d", d_player.getName(), d_targetCountry.getCountryId());
	}
}
