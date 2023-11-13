package Models.Strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import Models.Country;
import Models.Player;
import Models.Orders.AdvanceOrder;
import Models.Orders.DeployOrder;
import Models.Orders.Order;

/**
 *	ConcreteStrategy of the Strategy pattern. 
 */
public class AggressivePlayerStrategy extends PlayerStrategy {

	public AggressivePlayerStrategy(Player p_player, List<Country> p_countryList) {
		super(p_player, p_countryList);
	}

	/**
	 *	Determines the country to attack to. 
	 *	The Defensive player does not attack, so this returns null.
	 *	@return null
	 */
	protected Country toAttack() {
        Collections.sort(d_countryList, Comparator.comparingInt(Country::getArmies));
        
        int l_attackTarget = 0;
        for(Country l_country : d_countryList) {
        	if (d_player.getD_countries().contains(l_country))
				for(int l_neighbor : l_country.getNeighborCountries()) {
					boolean l_ifNeighborOwned = false;
					for (Country l_countryOwned : d_player.getD_countries())
						if(l_neighbor == l_countryOwned.getCountryId())
							l_ifNeighborOwned = true;
					if (l_ifNeighborOwned == false)
						l_attackTarget = l_neighbor;
				}			
		}
        
        if (l_attackTarget != 0)
        	for(Country l_country : d_countryList)
        		if (l_country.getCountryId() == l_attackTarget)
        			return l_country;
        return null;
	}

	/**
	 *	Determines where to launch an attack from. The Defensive player does not attack, so it returns null
	 *	@return the player's country with the most armies
	 */
	protected Country toDefend() {
		return null;
	}

	/**
	 *	Determines where to launch an attack from. The Defensive player does not attack, so it returns null
	 *	@return attack source country
	 */
	protected Country toAttackFrom() {
        Collections.sort(d_countryList, Comparator.comparingInt(Country::getArmies));
        for(Country l_country : d_countryList) {
        	if (d_player.getD_countries().contains(l_country))
				for(int l_neighbor : l_country.getNeighborCountries()) {
					boolean l_ifNeighborOwned = false;
					for (Country l_countryOwned : d_player.getD_countries())
						if(l_neighbor == l_countryOwned.getCountryId())
							l_ifNeighborOwned = true;
					if (l_ifNeighborOwned == false)
						return l_country;
				}			
		}
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
	 *  Create an order. the aggresive player Deploy armies first, and advance to attack if possible.
	 *	@return created order
	 */
	public Order createOrder() {
		Random l_rand = new Random();
		if (d_player.getD_reinforcementPool() > 0) {
			int l_armies = l_rand.nextInt(d_player.getD_reinforcementPool());
			d_player.decreaseReinforcementPool(l_armies);
			return new DeployOrder(d_player, toAttackFrom(), l_armies);
		}
		else {
			Country l_attackSourceCountry = toAttackFrom();
			return new AdvanceOrder(d_player, l_attackSourceCountry, toAttack(), l_attackSourceCountry.getArmies());
		}
	}
}
