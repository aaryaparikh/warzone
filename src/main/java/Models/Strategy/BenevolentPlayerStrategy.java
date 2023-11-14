package Models.Strategy;

import java.util.List;
import java.util.Random;

import Controller.LogEntryBuffer;
import Models.Country;
import Models.Player;
import Models.Orders.DeployOrder;
import Models.Orders.Order;

/**
 *	ConcreteStrategy of the Strategy pattern. 
 */
public class BenevolentPlayerStrategy extends PlayerStrategy {

	public BenevolentPlayerStrategy(Player p_player, List<Country> p_countryList, LogEntryBuffer p_logEntryBuffer) {
		super(p_player, p_countryList, p_logEntryBuffer);
	}

	/**
	 *	Determines the country to attack to. 
	 *	The Defensive player does not attack, so this returns null.
	 *	@return null
	 */
	protected Country toAttack() {
		return null;
	}

	/**
	 *	Determines which territory should be defended. 
	 *	The Defensive player decides to defend its country with the most armies. 
	 *	@return the player's country with the most armies
	 */
	protected Country toDefend() {
		Country l_maxArmyCountry = d_player.getD_countries().get(0);
		for(Country l_country : d_player.getD_countries()) {
			if (l_maxArmyCountry.getArmies() < l_country.getArmies())
				l_maxArmyCountry = l_country; 
		}
		return null;
	}

	/**
	 *	Determines where to launch an attack from. The Defensive player does not attack, so it returns null
	 *@	return null
	 */
	protected Country toAttackFrom() {
		return null;
	}

	/**
	 * 	Determine where armies are moved to. The Defensive player does not move, so it reutrns null
	 *  @return null
	 */
	protected Country toMoveFrom() {
		return null;
	}

	/**
	 *  Create an order. the Defensive player can only use Deploy orders
	 *	@return created order
	 */
	public Order createOrder() {
		Random l_rand = new Random();
		if (l_rand.nextInt(5) != 0) {
			return new DeployOrder(d_player, toDefend(), l_rand.nextInt(20));
		}
		return null;
	}
}
