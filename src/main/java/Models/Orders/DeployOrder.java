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
	    // Check if the player controls the specified country.
	    if (!d_player.getCountries().contains(d_country)) {
	        return String.format("Player \"%s\" does not control country \"%d\"", d_player.getName(), d_country);
	    }
	    
	    // Check if the player has enough armies.
	    if (d_player.getReinforcementPool() < d_armies) {
	        return String.format("Player \"%s\" does not have enough armies", d_player.getName());
	    }
	    
	    // Find the country in the game map and add armies.
	    for (Country country : p_game.getGameMap().getCountries()) {
	        if (country.equals(d_country)) {
	            country.addArmies(d_armies);
	            break;
	        }
	    }
	    
	    // Decrease the player's reinforcement pool.
	    d_player.decreaseReinforcementPool(d_armies);
	    
	    return String.format("Player \"%s\" deployed \"%d\" armies to country \"%d\"",
	            d_player.getName(), d_armies, d_country);
	}

}