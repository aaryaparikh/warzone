package Models.Orders;

import Controller.GameEngine;
import Models.Country;
import Models.Player;

/**
 * Players can place reinforcements to the countries they control.
 * 
 * @author YURUI
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
	 * @param p_player object
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

		boolean ifDeployCountryInControl = false;
		for (Country l_country : d_player.getD_countries()) {
			if (l_country.equals(d_country)) {
				ifDeployCountryInControl = true;
				l_country.addArmies(d_armies);
				break;
			}
		}

		if (ifDeployCountryInControl == true)
			return String.format("Player \"%s\" deployed \"%d\" armies to country \"%d\"", d_player.getName(), d_armies,
					d_country.getCountryId());
		else
			return String.format("Player \"%s\" can't deploy to country \"%d\", since it is not his territory.",
					d_player.getName(), d_armies, d_country.getCountryId());
	}

	@Override
	public String getOrderType() {
		return "Deploy";
	}
}