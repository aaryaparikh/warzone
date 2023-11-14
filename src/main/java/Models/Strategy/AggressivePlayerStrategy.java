package Models.Strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import Controller.LogEntryBuffer;
import Models.Country;
import Models.Player;
import Models.Orders.AdvanceOrder;
import Models.Orders.DeployOrder;
import Models.Orders.Order;

/**
 * ConcreteStrategy of the Strategy pattern.
 */
public class AggressivePlayerStrategy extends PlayerStrategy {

	public AggressivePlayerStrategy(Player p_player, List<Country> p_countryList, LogEntryBuffer p_logEntryBuffer) {
		super(p_player, p_countryList, p_logEntryBuffer);
	}

	/**
	 * Determines the country to attack to. The Defensive player does not attack, so
	 * this returns null.
	 * 
	 * @return null
	 */
	protected Country toAttack() {
		Collections.sort(d_countryList, Comparator.comparingInt(Country::getArmies).reversed());

		int l_attackTarget = 0;
		for (Country l_countryInGame : d_countryList) {
			for (Country l_countryOwned : d_player.getD_countries())
				if (l_countryOwned.getCountryId() == l_countryInGame.getCountryId())
					
					// Judge whether neighbor is control by himself
					for (int l_neighbor : l_countryOwned.getNeighborCountries()) {
						boolean l_ifNeighborOwned = false;
						for (Country l_neighborOwned : d_player.getD_countries())
							if (l_neighbor == l_neighborOwned.getCountryId())
								l_ifNeighborOwned = true;
						
						// Could attack country not controled
						if (l_ifNeighborOwned == false)
							l_attackTarget = l_neighbor;
					}
		}

		if (l_attackTarget != 0)
			for (Country l_country : d_player.getD_gameEngine().getGameMap().getCountries())
				if (l_country.getCountryId() == l_attackTarget)
					return l_country;
		return null;
	}

	/**
	 * Determines where to launch an attack from. The Defensive player does not
	 * attack, so it returns null
	 * 
	 * @return the player's country with the most armies
	 */
	protected Country toDefend() {
		return null;
	}

	/**
	 * Determines where to launch an attack from. The Defensive player does not
	 * attack, so it returns null
	 * 
	 * @return attack source country
	 */
	protected Country toAttackFrom() {
		Collections.sort(d_countryList, Comparator.comparingInt(Country::getArmies).reversed());
		for (Country l_countryInGame : d_countryList) {
			for (Country l_countryOwned : d_player.getD_countries())
				if (l_countryOwned.getCountryId() == l_countryInGame.getCountryId())
					
					// Judge whether neighbor is control by himself
					for (int l_neighbor : l_countryOwned.getNeighborCountries()) {
						boolean l_ifNeighborOwned = false;
						for (Country l_neighborOwned : d_player.getD_countries())
							if (l_neighbor == l_neighborOwned.getCountryId())
								l_ifNeighborOwned = true;
						
						// Could attack country not controled
						if (l_ifNeighborOwned == false)
							return l_countryOwned;
					}
		}
		return null;
	}

	/**
	 * Determine where armies are moved to. The Defensive player does not move, so
	 * it reutrns null
	 * 
	 * @return null
	 */
	protected Country toMoveFrom() {
		return null;
	}

	/**
	 * Create an order. the aggresive player Deploy armies first, and advance to
	 * attack if possible.
	 * 
	 * @return created order
	 */
	public Order createOrder() {
		Random l_rand = new Random();
		if (d_player.getD_reinforcementPool() > 0) {
			int l_armies = l_rand.nextInt(d_player.getD_reinforcementPool()) + 1;
			Country l_deployCountry = toAttackFrom();

			// Log order
			String l_response = String.format(
					"Aggressive Strategy adds an order of deploying \"%s\" armies on \"%s\" to Player \"%s\".",
					l_armies, l_deployCountry.getCountryId(), d_player.getName());
			d_logEntryBuffer.setString(l_response);
			System.out.println(l_response);
			
			// Update game information
			d_player.decreaseReinforcementPool(l_armies);
			for (Country l_country : d_countryList)
				if (l_country.getCountryId() == l_deployCountry.getCountryId())
					l_country.addArmies(l_armies);
			
			return new DeployOrder(d_player, l_deployCountry, l_armies);
		} else {
			Country l_attackSourceCountry = toAttackFrom();
			Country l_attackTargetCountry = toAttack();
			
			// Depend on country in player's view
			Country l_attackSourceCountryInList = null;
			for (Country l_country : d_countryList)
				if (l_country.getCountryId() == l_attackSourceCountry.getCountryId())
					l_attackSourceCountryInList = l_country;
					
			if (l_attackSourceCountryInList == null || l_attackSourceCountryInList.getArmies() == 0)
				return null;
				
			int l_attackArmies = l_attackSourceCountryInList.getArmies();

			// Log order
			String l_response = String.format(
					"Aggressive Strategy adds an order of advancing \"%s\" armies from \"%s\" to \"%s\" for Player \"%s\".",
					l_attackSourceCountry.getArmies(), l_attackSourceCountry.getCountryId(),
					l_attackTargetCountry.getCountryId(), d_player.getName());
			d_logEntryBuffer.setString(l_response);
			System.out.println(l_response);
			
			// Update game information
			for (Country l_country : d_countryList)
				if (l_country.getCountryId() == l_attackSourceCountry.getCountryId())
					l_country.setArmies(0);
			
			return new AdvanceOrder(d_player, l_attackSourceCountry, l_attackTargetCountry,
					l_attackArmies);
		}
	}
}
