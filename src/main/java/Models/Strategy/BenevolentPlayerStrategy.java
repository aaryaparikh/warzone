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
 * BenevolentPlayerStrategy represents the Benevolent strategy of the Strategy
 * pattern. This strategy focuses on defending its countries and reinforcing the
 * weakest territories.
 * 
 * @author Yurui
 */
public class BenevolentPlayerStrategy extends PlayerStrategy {

	/**
	 * Creates a BenevolentPlayerStrategy instance with the specified player,
	 * country list, and log entry buffer.
	 * 
	 * @param p_player         The player associated with this strategy.
	 * @param p_countryList    The list of countries owned by the player.
	 * @param p_logEntryBuffer The buffer for logging strategy-related entries.
	 */
	public BenevolentPlayerStrategy(Player p_player, List<Country> p_countryList, LogEntryBuffer p_logEntryBuffer) {
		super(p_player, p_countryList, p_logEntryBuffer);
	}

	/**
	 * Determines the country to attack. The Benevolent player does not attack, so
	 * it returns null.
	 * 
	 * @param p_sourceCountry The country from which the attack is initiated.
	 * @return null
	 */
	protected Country toAttack(Country p_sourceCountry) {
		return null;
	}

	/**
	 * Determines which territory should be defended. The Benevolent player decides
	 * to defend its country with the most armies.
	 * 
	 * @return The player's country with the most armies.
	 */
	protected Country toDefend() {
		Collections.sort(d_countryList, Comparator.comparingInt(Country::getArmies));

		// Judge whether country is controled by himself
		for (Country l_countryInGame : d_countryList) {
			for (Country l_countryOwned : d_player.getD_countries())
				if (l_countryOwned.getCountryId() == l_countryInGame.getCountryId())
					return l_countryOwned;
		}
		return null;
	}

	/**
	 * Determines where to launch an attack from. The Benevolent player does not
	 * attack, so it returns null.
	 * 
	 * @return null
	 */
	protected Country toAttackFrom() {
		return null;
	}

	/**
	 * Determine where armies are moved to. The Benevolent player reinforces the
	 * weakest neighboring territory.
	 * 
	 * @return The target country to reinforce.
	 */
	protected Country toMoveFrom(Country p_sourceCountry) {
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
	 * Create an order. The Benevolent player deploys armies first and advances to
	 * move if possible.
	 * 
	 * @return The created order.
	 */
	public Order createOrder() {

		// Deploy weakest country each time
		if (d_player.getD_reinforcementPool() > 0) {
			Random l_rand = new Random();
			int l_armies = l_rand.nextInt(d_player.getD_reinforcementPool()) + 1;
			Country l_deployCountry = toDefend();

			if (l_deployCountry == null)
				return null;

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
			Country l_defendSourceCountry = toMoveFrom(null);
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
