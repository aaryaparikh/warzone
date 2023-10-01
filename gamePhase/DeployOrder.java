package gamePhase;

import controller.GameEngine;
import mapEditor.Country;
import startPhase.Player;

/**
 * Players can place reinforcements to the countries they control.
 */
public class DeployOrder extends Order {
	private Player d_player;
	private Country d_country;
	private int d_armies;

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
	 * method to execute deploy order
	 * 
	 * @param p_game object calling this function
	 * @return Positive response string if this order was executed successfully, otherwise negative response
	 */
	@Override
	public String execute(GameEngine p_game) {
		if (!d_player.getCountries().contains(d_country)) {
			return String.format("Player \"%s\" does not control country \"%d\"", d_player.getName(), d_country);
		}
		
		if (d_player.getReinforcementPool() < d_armies) {
			return String.format("Player \"%s\" does not enough armies", d_player.getName());
		}
		
		for (int i = 0; i < p_game.getGameMap().getCountries().size(); i++) {
			if (p_game.getGameMap().getCountries().get(i).equals(d_country)) {
				p_game.getGameMap().getCountries().get(i).addArmies(d_armies);
			}
		}
		d_player.decreaseReinforcementPool(d_armies);
		return String.format("Player \"%s\" deployed \"%d\" armies to country \"%d\"", d_player.getName(), d_armies,
				d_country);
	}
}