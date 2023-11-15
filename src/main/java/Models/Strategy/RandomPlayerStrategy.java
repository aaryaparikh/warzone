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
public class RandomPlayerStrategy extends PlayerStrategy {

	public RandomPlayerStrategy(Player p_player, List<Country> p_countryList, LogEntryBuffer p_logEntryBuffer) {
		super(p_player, p_countryList, p_logEntryBuffer);
	}

	/**
	 * Determines the country to attack to. The Defensive player does not attack, so
	 * this returns null.
	 * 
	 * @return null
	 */
	protected Country toAttack() {
		return null;
	}

	/**
	 * Determines which territory should be defended. The Defensive player decides
	 * to defend its country with the most armies.
	 * 
	 * @return the player's country with the most armies
	 */
	protected Country toDefend() {
		Collections.sort(d_countryList, Comparator.comparingInt(Country::getArmies).reversed());

		// Judge whether country is controled by himself
		for (Country l_countryInGame : d_countryList) {
			for (Country l_countryOwned : d_player.getD_countries())
				if (l_countryOwned.getCountryId() == l_countryInGame.getCountryId())
					return l_countryOwned;
		}
		return null;
	}

	/**
	 * Determines where to launch an attack from. The Defensive player does not
	 * attack, so it returns null @ return null
	 */
	protected Country toAttackFrom() {
		return null;
	}

	/**
	 * Determine where armies are moved to. The Defensive player does not move, so
	 * it reutrns null
	 * 
	 * @return null
	 */
	protected Country toMoveFrom() {
		Collections.sort(d_countryList, Comparator.comparingInt(Country::getArmies).reversed());

		// Judge whether country is controled by himself
		for (Country l_countryInGame : d_countryList) {
			for (Country l_countryOwned : d_player.getD_countries())
				if (l_countryOwned.getCountryId() == l_countryInGame.getCountryId())

					// Judge whether neighbor is controlled by player
					for (int l_neighbor : l_countryOwned.getNeighborCountries()) {
						for (Country l_neighborOwned : d_player.getD_countries())
							if (l_neighbor == l_neighborOwned.getCountryId())

								// Judge whether has armies in neighbor controlled
								for (Country l_countryInList : d_countryList)
									if ((l_countryInList.getCountryId() == l_neighbor)
											&& (l_countryInList.getArmies() > 0)
											&& (l_countryInGame.getArmies() > l_countryInList.getArmies()))
										return l_neighborOwned;
					}
		}
		return null;
	}

	/**
	 * Create an order. the Defensive player can only use Deploy orders
	 * 
	 * @return created order
	 */
	public Order createOrder() {
		Random l_rand = new Random();

		if (d_player.getD_reinforcementPool() > 0) {
			int l_armies = l_rand.nextInt(d_player.getD_reinforcementPool()) + 1;
			Country l_deployCountry = toDefend();

			// Log order
			String l_response = String.format("Benevolent Strategy deploy \"%s\" armies on \"%s\" for Player \"%s\".",
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
			Country l_defendSourceCountry = toMoveFrom();
			Country l_defendTargetCountry = toDefend();

			// Depend on country in player's view
			Country l_defendSourceCountryInList = null;

			// If no source country
			if (l_defendSourceCountry == null)
				return null;
			else
				for (Country l_country : d_countryList)
					if (l_country.getCountryId() == l_defendSourceCountry.getCountryId())
						l_defendSourceCountryInList = l_country;

			// If source country no armies
			if (l_defendSourceCountryInList.getArmies() == 0)
				return null;

			int l_defendArmies = l_defendSourceCountryInList.getArmies();

			// Log order
			String l_response = String.format(
					"Benevolent Strategy advance \"%s\" armies from \"%s\" to \"%s\" for Player \"%s\".",
					l_defendArmies, l_defendSourceCountry.getCountryId(), l_defendTargetCountry.getCountryId(),
					d_player.getName());
			d_logEntryBuffer.setString(l_response);
			System.out.println(l_response);

			// Update game information
			for (Country l_country : d_countryList)
				if (l_country.getCountryId() == l_defendSourceCountry.getCountryId())
					l_country.setArmies(0);

			return new AdvanceOrder(d_player, l_defendSourceCountry, l_defendTargetCountry, l_defendArmies);
		}
	}
}