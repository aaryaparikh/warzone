package Models.Orders;

import Controller.GameEngine;
import Models.Country;
import Models.Player;

/**
 * Players can place reinforcements to the countries they control.
 */
public class DeployOrder extends Order {
	private Player d_player;
	private Country d_country;
	private int d_armies;

	/**
	 * Gets player object
	 *
	 * @return player object
	 */
	public Player getPlayer() {
		return d_player;
	}

	/**
	 * Sets player object
	 *
	 * @param player object
	 */
	public void setPlayer(Player p_player) {
		this.d_player = p_player;
	}

	/**
	 * Constructor to assign initial values
	 * 
	 * @param p_player  player who is committing deploy order
	 * @param p_country which country that reinforcements are to be deployed
	 * @param p_armies  number of reinforcements to be deployed
	 */
	public DeployOrder(Player p_player, Country p_country, int p_armies) {
		d_player = p_player;
		d_country = p_country;
		d_armies = p_armies;
	}

	/**
	 * Method to execute deploy order.
	 * 
	 * @param p_game the object representing the game.
	 * @return a response string indicating the result of the order execution.
	 */
	@Override
	public String execute(GameEngine p_game) {
		for (Country l_country : p_game.getGameMap().getCountries()) {
			if (l_country.equals(d_country)) {
				l_country.addArmies(d_armies);
				break;
			}
		}

		return String.format("Player \"%s\" deployed \"%d\" armies to country \"%d\"", d_player.getName(), d_armies,
				d_country.getCountryId());
	}

}